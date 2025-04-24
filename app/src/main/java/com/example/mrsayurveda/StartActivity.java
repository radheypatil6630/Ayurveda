package com.example.mrsayurveda;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ImageView imageView = findViewById(R.id.imageView);
        TextView textView = findViewById(R.id.textView5);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button button = findViewById(R.id.get_start_btn);
        Animation zoomOut = AnimationUtils.loadAnimation(this, R.anim.zoom_out);

// Set listener to show button after animation ends
//        zoomOut.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//                // Optional: Do something at the start
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                // Show the button after the animation ends
//                button.setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//                // Optional: Handle repeat
//            }
//        });

// Start animation on both views
//        imageView.startAnimation(zoomOut);
//        textView.startAnimation(zoomOut);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StartActivity.this,MainActivity.class);
                startActivity(i);
            }
        });

    }
}