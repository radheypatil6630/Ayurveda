package com.example.mrsayurveda;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ShareActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        // Your additional code for the activity goes here

        // Example: Setting up the ActionBar
//        getSupportActionBar().setTitle("Share Activity");
//
//        // Example: Adding a back button to the ActionBar
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    // You can include additional methods or override other lifecycle methods as needed

    // Example: Handling the back button press
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
