package com.example.geniusplaza.usertouserchat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.geniusplaza.usertouserchat.CustomerServiceBot.MainActivityCSB;
import com.example.geniusplaza.usertouserchat.Model.ChatMessage;
import com.example.geniusplaza.usertouserchat.Model.User;
import com.example.geniusplaza.usertouserchat.Tutor.POJO.Pod;
import com.example.geniusplaza.usertouserchat.Tutor.POJO.Query;
import com.example.geniusplaza.usertouserchat.Tutor.Retrofit.RestClient;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import es.dmoral.toasty.Toasty;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class GroupChatActivity extends AppCompatActivity {

    int SIGN_IN_REQUEST_CODE = 100;
    private FirebaseListAdapter<ChatMessage> adapter;
    public User existingUserProfile;
    public DatabaseReference mDatabase;
    public FirebaseAuth firebaseAuth;
    public String result ="";
    public String resultDisplay;
    ProgressBar mProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
            // Load chat room contents
        mDatabase = FirebaseDatabase.getInstance().getReference();
        firebaseAuth =FirebaseAuth.getInstance();
        displayChatMessages();

        FloatingActionButton fab =
                (FloatingActionButton)findViewById(R.id.fab);


        Log.d("current user uid",firebaseAuth.getCurrentUser().getUid());
        mDatabase.child("users").child(firebaseAuth.getCurrentUser().getUid()).child("profile").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                existingUserProfile = dataSnapshot.getValue(User.class);
                Log.d("existing user",existingUserProfile.getFirstName());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("error",databaseError.toString());
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = (EditText) findViewById(R.id.input);

                // Read the input field and push a new instance
                // of ChatMessage to the Firebase database

                Log.d("Display name",firebaseAuth.getCurrentUser().getDisplayName());

                if(input.getText().toString().toLowerCase().contains("@help")){
                    Intent i = new Intent(GroupChatActivity.this, MainActivityCSB.class);
                    startActivity(i);
                    finish();
                    return;
                }
                mDatabase.child("GroupChat").push()
                        .setValue(new ChatMessage(input.getText().toString()+":",
                                existingUserProfile.getFirstName().toString())
                        );
                if(input.getText().toString().toLowerCase().contains("@tutor")){
                    resultDisplay = tutorSelected(input.getText().toString().toLowerCase().replace("@tutor ",""));
//                    Intent i = new Intent(GroupChatActivity.this, MainActivityTutor.class);
//                    i.putExtra("searchWords", input.getText().toString().toLowerCase().replace("@tutor",""));
//                    startActivity(i);


                }
                // Clear the input
                input.setText("");
            }
        });
    }
    private void displayChatMessages() {
        ListView listOfMessages = (ListView)findViewById(R.id.list_of_messages);

        adapter = new FirebaseListAdapter<ChatMessage>(this, ChatMessage.class,
                R.layout.message, mDatabase.child("GroupChat")) {
            @Override
            protected void populateView(View v, ChatMessage model, int position) {
                // Get references to the views of message.xml
                TextView messageText = (TextView)v.findViewById(R.id.message_text);
                TextView messageUser = (TextView)v.findViewById(R.id.message_user);
                TextView messageTime = (TextView)v.findViewById(R.id.message_time);

                // Set their text
                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());

                // Format the date before showing it
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                        model.getMessageTime()));
            }
        };

        listOfMessages.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu_groupchat,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_option_view_profile:
                Intent intent= new Intent(this,ProfileDetailsActivity.class);
                startActivity(intent);
                return true;
            case R.id.item_option_logout:
                firebaseAuth.signOut();
                Intent intent1=new Intent(this, MainActivity.class);
                startActivity(intent1);
                finish();
            case R.id.item_about:
                new AlertDialog.Builder(GroupChatActivity.this)
                        .setTitle("HOW TO USE")
                        .setMessage("To speak with tutor add \'@tutor\' annotation followed by the question\n\nTo get intouch with a customer service representative type \'@help\'")
                        .setNegativeButton("Done",null)
                        .create()
                        .show();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public String tutorSelected(String Query){
        String searchWords = Query.replace("+", "plus");
        mProgressBar.setVisibility(View.VISIBLE);
        RestClient.getExampleApi().postGetQueryResult(searchWords)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Query>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Query value) {
                if(value.getQueryresult().getPods() != null){
                    for (Pod i:value.getQueryresult().getPods()){
                        Log.d("Pre if Result Value: " , i.toString());
                        Log.d("Titles", i.getTitle());
                        if(i.getTitle().equals("Result")||i.getTitle().equals("Results")){
                            Log.d("Result Value: " , i.toString());
                            result += "Result:\n";
                            result += i.getSubpods().get(0).getPlaintext().toString()+"\n";
                        }
                        if(i.getTitle().equals("Definitions") || i.getTitle().equals("Definition")){
                            Log.d("Result Value: " , i.toString());
//                        results.setText(i.getSubpods().get(0).getPlaintext().toString());

                            result += "Definition:\n";
                            result += i.getSubpods().get(0).getPlaintext().toString()+"\n";
                        }

                    }
                }
                else{
                    Toasty.error(getApplicationContext(),"Try rewording the input",Toast.LENGTH_SHORT,true).show();
                }
                if(result != ""){
                    mDatabase.child("GroupChat").push()
                            .setValue(new ChatMessage(result,
                                    "Tutor: ")
                            );
                    result = "";
                }
                else{
                    Toasty.error(getApplicationContext(),"Try rewording the input",Toast.LENGTH_SHORT,true).show();
                }
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Throwable e) {
                Toasty.error(GroupChatActivity.this, "No results, Please try rewording the input", Toast.LENGTH_LONG, true).show();
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onComplete() {

            }
        });
        return result;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapter.cleanup();
    }
}