package com.example.mrsayurveda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b=(Button) findViewById(R.id.Login);

        b.setOnClickListener(view -> {
                Intent stm = new Intent(MainActivity.this, Home_page.class);
                startActivity(stm);
                Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();

        });

    }
}