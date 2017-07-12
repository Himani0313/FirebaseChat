package com.example.geniusplaza.usertouserchat;

import android.accounts.Account;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.Contacts;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private Button emailSignIn;
    private EditText userEmailText,userPasswordText;
    private TextView signUpLink;


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;
    final static private int RC_SIGN_IN=9001;
    FirebaseUser user;
    String first_name,last_name,gender,profilePicUrl,fb_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emailSignIn= (Button) findViewById(R.id.buttonSignIn);
        userEmailText= (EditText) findViewById(R.id.editTextEmail);
        userPasswordText= (EditText) findViewById(R.id.editTextPassword);
        signUpLink= (TextView) findViewById(R.id.textViewSignUpLink);
        mDatabase= FirebaseDatabase.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("demo", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("demo", "onAuthStateChanged:signed_out");
                }
            }
        };

        if (mAuth.getCurrentUser()!=null){
            Intent intent=new Intent(MainActivity.this,UserPageActivity.class);
            startActivity(intent);
            finish();
        }

        emailSignIn.setOnClickListener(this);
        signUpLink.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.buttonSignIn){
            signIn(userEmailText.getText().toString(),userPasswordText.getText().toString());
        }else if (v.getId()==R.id.textViewSignUpLink){
            Intent intent=new Intent(MainActivity.this,SignUpActivity.class);
            startActivity(intent);
            finish();
        }

    }

    public void signIn(String email,String password){
        if (!validateForm()){
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("demo", "signInWithEmail:onComplete:" + task.isSuccessful());

                        if (!task.isSuccessful()) {
                            Log.w("demo", "signInWithEmail:failed", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication Failed",
                                    Toast.LENGTH_SHORT).show();
                        }else {
                            //go to UserPage activity
                            Intent intent=new Intent(MainActivity.this,UserPageActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });

    }

    public boolean validateForm(){
        boolean valid=true;
        if (userEmailText.getText().toString().isEmpty()){
            userEmailText.setError("Required");
            valid=false;
        } else{
            userEmailText.setError(null);
        }

        if (userPasswordText.getText().toString().isEmpty()){
            userPasswordText.setError("Required");
            valid=false;
        } else{
            userPasswordText.setError(null);
        }
        return valid;
    }

}
