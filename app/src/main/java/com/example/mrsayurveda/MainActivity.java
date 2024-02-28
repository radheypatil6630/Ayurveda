package com.example.mrsayurveda;// MainActivity.java
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    EditText Textemail, Textpassword;
    Button login;
    TextView registerbtn;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

       Textemail = findViewById(R.id.Email);
        Textpassword = findViewById(R.id.Password);
        login = findViewById(R.id.Login);
        registerbtn=findViewById(R.id.registerbtnlogin);

        //username=jaydeep@gmail.com
        //password=123456

//        homeFragment homeFragment = new homeFragment();
        
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
                                // If valid, start the next activity
                                Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
//                                Intent stm = new Intent(MainActivity.this, homeFragment.class);
//                                startActivity(stm);
                                Intent intent = new Intent(MainActivity.this, homeActivity.class);
                                startActivity(intent);
                                finish();
//                                getSupportFragmentManager().beginTransaction()
//                                        .replace(R.id.fragment_container, new homeFragment())
//                                        .commit();
                            } else {
                                Toast.makeText(MainActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });
        registerbtn.setOnClickListener(view -> {
            Intent stm = new Intent(MainActivity.this, RegisterPage.class);
            startActivity(stm);
        });

    }

        // ... other code


        public void onClearEditText(String email, String password) {
            // Clear your EditText in MainActivity here
            // For example:
           Textemail.setText("");
           Textpassword.setText("");

            Textemail.setVisibility(View.INVISIBLE);
            Textpassword.setVisibility(View.INVISIBLE);
        }


}
