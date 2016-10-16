package com.example.abhishek.flickster.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * Class for showing splash screen to the user on initial load
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialized the intent and start the Movies Activity
        Intent intent = new Intent(this, MoviesActivity.class);
        startActivity(intent);
        finish();
    }
}
