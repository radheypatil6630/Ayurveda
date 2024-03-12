package com.example.mrsayurveda;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

public class ProfileActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView profileImageView;
    Uri imageuri;
    private EditText profileNameText;
    private EditText email, password;
    private Button save, logout;
    ProgressDialog pd;
    StorageReference storageReference;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    // Declare imageUri at the class level
   // private Uri imageUri;

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

//        currentUser = FirebaseAuth.getInstance().getCurrentUser();
//        if (currentUser != null) {
//            // Retrieve user's first and last name from Firebase
//            String firstName = currentUser.getDisplayName();
//            profileNameText.setText(firstName);
//        }

        // Set a click listener on the ImageView to open the image gallery
        mAuth = FirebaseAuth.getInstance();

        //profileImageView.setOnClickListener(v -> openGallery());

       // save.setOnClickListener(v -> uploadImage());



        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert=new AlertDialog.Builder(ProfileActivity.this);
                alert.setMessage("Do you want to change profile image?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent in=new Intent(ProfileActivity.this,ChangeImage.class);
                        startActivity(in);
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                AlertDialog alertDialog=alert.create();
                alertDialog.show();
            }
        });
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

//    private void openGallery() {
//        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(intent, PICK_IMAGE_REQUEST);
//    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
//            imageuri = data.getData();
//            profileImageView.setImageURI(imageuri);
//            //Glide.with(this).load(imageuri).into(profileImageView);
//        }
//    }


//    private void saveToFirebase() {
//        if (imageUri != null) {
//            uploadImageToFirebaseStorage(imageUri);
//        } else {
//            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
//        }
//    }

//    private void uploadImage() {
//        if (imageuri == null) {
//            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
//            return;
//        }
////        pd=new ProgressDialog(this);
////        pd.setTitle("Uploading File....");
////        pd.show();
//            FirebaseUser user = mAuth.getCurrentUser();
//            String userId = user != null ? user.getUid() : "";
//
//            StorageReference storageReference = FirebaseStorage.getInstance().getReference("UserProfileImages/" + userId);
//
//            // Download the image into an ImageView
//            try {
//                File localFile = File.createTempFile("tempfile", ".jpg");
//                storageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                        // Successfully downloaded the image, set it to the ImageView
//                        DisplayMetrics dm = new DisplayMetrics();
//                        Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
//                        profileImageView.setImageBitmap(bitmap);
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        // Handle failure to load profile image
//                    }
//                });
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }





}
//