package com.example.mrsayurveda;// MainActivity.java

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    EditText Textemail, Textpassword;
    Button login;
    TextView registerbtn;
    FirebaseAuth mAuth;

    LottieAnimationView loginAnimation , loadingAnimation;
    ShapeableImageView profileImage;
    LinearLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        loadingAnimation = findViewById(R.id.LoadingAnimation4);
        loginAnimation = findViewById(R.id.LoginAnimation);
        profileImage = findViewById(R.id.profileImage);
        mainLayout = findViewById(R.id.linear_layout_login);

        if (currentUser != null) {
            if (currentUser.isEmailVerified()) {
                Intent intent = new Intent(MainActivity.this, homeActivity.class);

                if (currentUser.getEmail().equals("patilradhey6630@gmail.com") ) {
                    // Admin user
                    intent.putExtra("isAdmin", true);
                    Log.d("good", "Admin login");
                } else {
                    // Regular user
                    intent.putExtra("isAdmin", false);
                    Log.d("good", "User login");
                }

                startActivity(intent);
                finish();
            } else {
                // User's email is not verified, prompt them to verify their email
                Toast.makeText(MainActivity.this, "Please verify your email before signing in", Toast.LENGTH_SHORT).show();
                mAuth.signOut(); // Sign out the user to prevent auto-login
            }
            // No user is signed in
        }

       Textemail = findViewById(R.id.Email);
        Textpassword = findViewById(R.id.Password);
        login = findViewById(R.id.Login);
        registerbtn=findViewById(R.id.registerbtnlogin);

        mainLayout.setVisibility(View.GONE);
        profileImage.setVisibility(View.GONE);
        loadingAnimation.setVisibility(View.VISIBLE);
        loadingAnimation.playAnimation();
        loadingAnimation.addAnimatorListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                // Hide animation view
                loadingAnimation.setVisibility(View.GONE);

                // Show other components
                mainLayout.setVisibility(View.VISIBLE);
                profileImage.setVisibility(View.VISIBLE);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = Textemail.getText().toString().trim();
                String password = Textpassword.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener(MainActivity.this, task -> {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();

                                if (user != null && user.isEmailVerified()) {
                                    mainLayout.setVisibility(View.GONE);
                                    profileImage.setVisibility(View.GONE);
                                    loginAnimation.setVisibility(View.VISIBLE);
                                    loginAnimation.playAnimation();
                                    loginAnimation.addAnimatorListener(new AnimatorListenerAdapter() {
                                        @Override
                                        public void onAnimationEnd(Animator animation) {
                                            super.onAnimationEnd(animation);

                                            Intent intent = new Intent(MainActivity.this, homeActivity.class);

                                            if (email.equals("patilradhey6630@gmail.com") && password.equals("123456")) {
                                                // Admin user
                                                intent.putExtra("isAdmin", true);
                                                Log.d("good", "Admin login");
                                            } else {
                                                // Regular user
                                                intent.putExtra("isAdmin", false);
                                                Log.d("good", "User login");
                                            }

                                            startActivity(intent);
                                            finish();
                                        }
                                        });

                                    // Email is verified, proceed with login
//                                    Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
//
//                                    Intent intent = new Intent(MainActivity.this, homeActivity.class);
//                                    startActivity(intent);

                                }else {
                                    // Email is not verified, show a message or take appropriate action
                                    Toast.makeText(MainActivity.this, "Please check and verify your email before Signing In", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(MainActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                                // Insert the new digit at the end of the StringBuilder

                                Textpassword.setText("");
                            }
                        });

            }
        });
        registerbtn.setOnClickListener(view -> {
            Intent stm = new Intent(MainActivity.this, RegisterPage.class);
            startActivity(stm);
        });

    }




        public void onClearEditText(String email, String password) {
            // Clear your EditText in MainActivity here
            // For example:
           Textemail.setText("");
           Textpassword.setText("");

            Textemail.setVisibility(View.INVISIBLE);
            Textpassword.setVisibility(View.INVISIBLE);
        }


}
