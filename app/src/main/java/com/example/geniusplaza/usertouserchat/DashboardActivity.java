package com.example.geniusplaza.usertouserchat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

    }
    public void onClickUserToUser(View v){
        Intent i = new Intent(DashboardActivity.this, UserPageActivity.class);
        startActivity(i);
    }
    public void onClickGroupChat(View v){
        Intent i = new Intent(DashboardActivity.this, GroupChatActivity.class);
        startActivity(i);
    }
}
