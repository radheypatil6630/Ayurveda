package com.example.mrsayurveda;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ProfileActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView profileImageView;
    private EditText profileNameText;
    private EditText email, password;
    private Button save, logout;
    private FirebaseUser currentUser;
    private DatabaseReference databaseReference;
    // Declare imageUri at the class level
    private Uri imageUri;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileImageView = findViewById(R.id.profile_img);
        profileNameText = findViewById(R.id.profile_name);


        save = findViewById(R.id.saveDetails);
        logout = findViewById(R.id.logout);
        email=findViewById(R.id.Email);
        password=findViewById(R.id.Password);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            // Retrieve user's first and last name from Firebase
            String firstName = currentUser.getDisplayName();
            profileNameText.setText(firstName);
        }
//        String[] projection = {ContactsContract.Profile.DISPLAY_NAME};
//        Cursor cursor = getContentResolver().query(
//                ContactsContract.Profile.CONTENT_URI,
//                projection,
//                null,
//                null,
//                null
//        );
//
//        if (cursor != null && cursor.moveToFirst()) {
//            int displayNameIndex = cursor.getColumnIndex(ContactsContract.Profile.DISPLAY_NAME);
//            String displayName = cursor.getString(displayNameIndex);
//
//            // Now, 'displayName' contains the user's profile display name
//            profileNameText.setText(displayName);
//
//            cursor.close();
//        }
        // Set a click listener on the ImageView to open the image gallery
        profileImageView.setOnClickListener(v -> openGallery());

        save.setOnClickListener(v -> saveToFirebase());


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
            imageUri = data.getData();
            profileImageView.setImageURI(imageUri);
            Glide.with(this).load(imageUri).into(profileImageView);
        }
    }

    private void saveToFirebase() {
        if (imageUri != null) {
            uploadImageToFirebaseStorage(imageUri);
        } else {
            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadImageToFirebaseStorage(Uri imageUri) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference("profile_images")
                .child(currentUser.getUid() + ".jpg");

        storageReference.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                        saveImageUrlAndText(uri.toString());
                    });
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Image upload failed", Toast.LENGTH_SHORT).show();
                });
    }

    private void saveImageUrlAndText(String imageUrl) {
        String text = profileNameText.getText().toString();
        databaseReference = FirebaseDatabase.getInstance().getReference("profiles").child(currentUser.getUid());
        Profile profile = new Profile(imageUrl, text);
        databaseReference.setValue(profile);
        Toast.makeText(this, "Profile saved to Firebase", Toast.LENGTH_SHORT).show();
    }


}

