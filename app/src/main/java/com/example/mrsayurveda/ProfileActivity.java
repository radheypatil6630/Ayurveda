package com.example.mrsayurveda;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView profileImageView;
    private EditText profileNameEditText;
    private EditText email, password;
    private Button save, logout;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileImageView = findViewById(R.id.profile_img);
        profileNameEditText = findViewById(R.id.profile_name);

        // Set a click listener on the ImageView to open the image gallery
        profileImageView.setOnClickListener(v -> openGallery());

        save = findViewById(R.id.saveDetails);
        logout = findViewById(R.id.logout);

     email=findViewById(R.id.Email);
     password=findViewById(R.id.Password);

        logout.setOnClickListener(v -> {
            showToastMessage();

            // Clearing email and password or perform any other actions
            if (email != null && password != null) {
                email.setText("");
                password.setText("");
            }
            // Sign out from Firebase
             FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(ProfileActivity.this,MainActivity.class));

            finish();
        });
    }

    private void showToastMessage() {
        Toast.makeText(this, "Logout successfully", Toast.LENGTH_SHORT).show();
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Get the selected image URI
            Uri imageUri = data.getData();

            // Set the selected image to your ImageView or perform any other actions
            profileImageView.setImageURI(imageUri);

            // Use Glide to load and display the selected image
            Glide.with(this)
                    .load(imageUri)
                    .into(profileImageView);
        }
    }
}

