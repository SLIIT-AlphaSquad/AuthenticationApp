package com.example.authenticationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ViewHistory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_history);
    }

    public void viewposts(View view) {
        startActivity(new Intent(ViewHistory.this, UserPosts.class));
    }

    public void viewFoodposts(View view) {
        startActivity(new Intent(ViewHistory.this, UserFoodPosts.class));
    }

    public void addMedicinePosts(View view) {
        startActivity(new Intent(ViewHistory.this, UserMedicinePosts.class));
    }
}