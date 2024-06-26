package com.example.mrsayurveda;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class homeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
//    private HomePageBinding homePageBinding;
private EditText email, password;

    // Declare paidProductsList globally here
//    private List<ProductList> paidProductsList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);



//        Toolbar toolbar=findViewById(R.id.toolbar);
//     setSupportActionBar(toolbar);


//        getSupportActionBar().setTitle("Ayurveda");
//
//        // Enable the home button for navigation
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
      //  bottomNavigationView.setOnNavigationItemSelectedListener(this::handleBottomNavigation);
    bottomNavigationView.setOnNavigationItemSelectedListener(homeActivity.this);
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                return handleBottomNavigation(item.getItemId());
//            }
//        });

        ImageView medicalImageView = findViewById(R.id.imageView1);
        ImageView foodImageView = findViewById(R.id.imageView2);
        ImageView cosmeticImageView = findViewById(R.id.imageView3);
        ImageView imageView4 = findViewById(R.id.imageView4);
        ImageView imageView5 = findViewById(R.id.imageView5);
        ImageView imageView6 = findViewById(R.id.imageView6);
        ImageView imageView7 = findViewById(R.id.imageView7);

        medicalImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToProductList("Medical");
            }
        });

        foodImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToProductList("Food");
            }
        });

        cosmeticImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToProductList("Cosmetic");
            }
        });

        // Set click listeners for the new ImageViews (imageView4, imageView5, imageView6)
        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://m.facebook.com/arogyamayurskin"));
                startActivity(intent);
            }
        });

        imageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.webmd.com/balance/what-is-homeopathy"));
                startActivity(intent);
            }
        });

        imageView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://vedayurved.org/"));
                startActivity(intent);
            }
        });
        imageView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://vikaspedia.in/health/ayush/ayurveda-1/ayurvedic-home-remedies"));
                startActivity(intent);
            }
        });


    }

    private void navigateToProductList(String category) {
        Intent intent = new Intent(homeActivity.this, ProductListActivity.class);
        intent.putExtra("CATEGORY", category);
        startActivity(intent);
    }



    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.profile) {
            // Handle profile click
            startActivity(new Intent(homeActivity.this,MyAccount.class));

            return true;
        } else if (id == R.id.product_History) {
            // Handle product history click
            startActivity(new Intent(homeActivity.this,OrderHistoryActivity.class));

            return true;
        } else if (id == R.id.home) {
            // Handle home
            onBackPressed();
            startActivity(new Intent(homeActivity.this, homeActivity.class));

            return true;

        }
       else if (id == R.id.logput) {
            // Handle share click
//            if (email != null && password != null) {
//                email.setText("");
//                password.setText("");
//            }
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(homeActivity.this, MainActivity.class));
            Toast.makeText(homeActivity.this, "user logged out", Toast.LENGTH_SHORT).show();

            finish();
            return false;
        }

        return false;
    }

}

