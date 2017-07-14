package com.example.geniusplaza.usertouserchat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.geniusplaza.usertouserchat.Model.User;
import com.squareup.picasso.Picasso;

public class ViewUserProfile extends AppCompatActivity {

    TextView userFName,userLName,userGender;
    ImageView userProfilePic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_profile);
        userFName= (TextView) findViewById(R.id.textViewShowUFName);
        userLName= (TextView) findViewById(R.id.textViewShowULName);
        userGender= (TextView) findViewById(R.id.textViewShowUGender);
        userProfilePic= (ImageView) findViewById(R.id.imageViewUserProfilePicture);

        if (getIntent().getExtras().getSerializable("clickedUser")!=null){
            User user= (User) getIntent().getExtras().getSerializable("clickedUser");
            userFName.setText(user.getFirstName());
            userLName.setText(user.getLastName());
            userGender.setText(user.getGender());
            if (user.getUserPicUrl()!=null && !user.getUserPicUrl().isEmpty()){
                Picasso.with(getApplicationContext()).load(user.getUserPicUrl()).into(userProfilePic);
            }

        }else {
            Toast.makeText(getApplicationContext(),"User not Found",Toast.LENGTH_SHORT).show();
        }
    }
}
