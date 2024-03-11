package com.example.mrsayurveda;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Purchaseproduct extends AppCompatActivity {

    private TextView productNameTextView, productPriceTextView, deliveryDateTextView;
    private EditText usernameEditText, addressEditText,PhoneNumEditText;
    private Button cancelButton, buyButton;
    private ImageView productImageView;
    private DatabaseReference userRef;
    private FirebaseAuth mAuth;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchaseproduct);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {
            // Handle the case where the user is not logged in
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity
        }

        userRef = FirebaseDatabase.getInstance().getReference("users").child(currentUser.getEmail().replace(".", ","));

        // Initialize views
        productNameTextView = findViewById(R.id.productNameTextView);
        productPriceTextView = findViewById(R.id.productPriceTextView);
        deliveryDateTextView = findViewById(R.id.deliveryDateTextView);
        usernameEditText = findViewById(R.id.usernameEditText);
        addressEditText = findViewById(R.id.addressEditText);
        PhoneNumEditText = findViewById(R.id.phonenumEditText);
        productImageView = findViewById(R.id.productImageView);
        cancelButton = findViewById(R.id.cancelButton);
        buyButton = findViewById(R.id.buyButton);

        // Get product details from the previous activity
        Intent intent = getIntent();
        String imageUrl = getIntent().getStringExtra("imageUrl");
        String productName = getIntent().getStringExtra("ProductName");
        String price = getIntent().getStringExtra("price");

        // Set product details to the views
        productNameTextView.setText(productName);
        productPriceTextView.setText("₹"+price);

        // Load image using Picasso library
        Picasso.get().load(imageUrl).into(productImageView);

        // display delivery date
        displayDeliveryDate();

        // Fetch user-specific data and populate the EditText fields
        fetchUserData();

        // Cancel button click listener
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to HomeActivity on cancel button click
                Intent homeIntent = new Intent(Purchaseproduct.this, homeActivity.class);
                startActivity(homeIntent);
                finish();
            }
        });

        // Buy button click listener
        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform the purchase and navigate to DummyUPIPaymentActivity
                // Get the product price
                String productPrice = getIntent().getStringExtra("price");

                // Create intent for DummyUPIPaymentActivity
                Intent paymentIntent = new Intent(Purchaseproduct.this, DummyUPIPayment.class);

                // Pass the product price to DummyUPIPaymentActivity
                paymentIntent.putExtra("productPrice", price);

                // Start DummyUPIPaymentActivity
                startActivity(paymentIntent);
                finish(); // Finish the current activity
            }
        });

    }

    private void displayDeliveryDate() {
        // Calculate expected delivery date (dummy logic: 10 days from today)
        Date currentDate = Calendar.getInstance().getTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_YEAR, 10);
        Date deliveryDate = calendar.getTime();

        // Format delivery date for display
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String formattedDate = dateFormat.format(deliveryDate);

        // Set the expected delivery date to the TextView
        deliveryDateTextView.setText("Expected Delivery Date :- " + formattedDate);
    }

    private void fetchUserData() {
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // User data exists, populate EditText fields
                    String userName = snapshot.child("name").getValue(String.class);
                    String userAddress = snapshot.child("address").getValue(String.class);
                    String userPhoneNumber = snapshot.child("phoneNumber").getValue(String.class);

                    // Populate EditText fields
                    usernameEditText.setText(userName);
                    addressEditText.setText(userAddress);
                    PhoneNumEditText.setText(userPhoneNumber);

                    // Disable EditText fields
                    disableEditTextFields();
                } else {
                    // User data does not exist (new user), EditText fields remain enabled
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
                Toast.makeText(Purchaseproduct.this, "Failed to fetch user data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void disableEditTextFields() {
        // Disable EditText fields after fetching user data
        usernameEditText.setEnabled(false);
        addressEditText.setEnabled(false);
        PhoneNumEditText.setEnabled(false);
    }
}
