package com.example.mrsayurveda;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
    private EditText usernameText, addressText,PhoneNumText;
    private Button addToCartButton, buyButton,btnchage,btnsave;
    private ImageView productImageView;
    private DatabaseReference userRef;
    private FirebaseAuth mAuth;
    private boolean isEditMode = false;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchaseproduct);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        // Check if user data is editable (you can add your own logic here)
        isEditMode = true;

        if (currentUser == null) {
            // Handle the case where the user is not logged in
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity
        }

       // userRef = FirebaseDatabase.getInstance().getReference("users").child(currentUser.getEmail().replace(".", ","));

        // Initialize views
        productNameTextView = findViewById(R.id.productNameTextView);
        productPriceTextView = findViewById(R.id.productPriceTextView);
        deliveryDateTextView = findViewById(R.id.deliveryDateTextView);
        usernameText = findViewById(R.id.usernameTextView);
        addressText = findViewById(R.id.addressTextView);
        PhoneNumText = findViewById(R.id.phonenumTextView);
        productImageView = findViewById(R.id.productImageView);
        addToCartButton = findViewById(R.id.addCartButton);
        buyButton = findViewById(R.id.buyButton);
        btnchage = findViewById(R.id.btnchange);
        btnsave = findViewById(R.id.btnsave);


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

        // Fetch user-specific data and populate the TextView fields
        fetchUserData();





        // Cancel button click listener
        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCartActivity(price,productName,imageUrl);
                // Navigate to ProductListActivity on cancel button click
//                Toast.makeText(Purchaseproduct.this, "product is canceled and now you are going on home activity", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(Purchaseproduct.this,ProductCartActivity.class);
//                startActivity(intent);
//                finish();
            }
        });

        // Buy button click listener
        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform data validation before proceeding to payment
                if (isEditMode) {
                    // Check if any field is empty
                    if (usernameText.getText().toString().isEmpty() &&
                            addressText.getText().toString().isEmpty() &&
                            PhoneNumText.getText().toString().isEmpty()) {
                        Toast.makeText(Purchaseproduct.this, "Please fill all the details", Toast.LENGTH_SHORT).show();
                    } else {
                        String phoneNumber = PhoneNumText.getText().toString();
                        if (phoneNumber.length() != 10) {
                            PhoneNumText.setError("Phone number must be 10 digits long");
                            PhoneNumText.requestFocus();
                            return;}
                            // Disable editing of edittext fields
                        disableTextViewFields();
                        // Proceed to payment activity
                        goToPaymentActivity(price,productName,imageUrl);
                        finish();
                    }
                } else {
                    // Proceed to payment activity directly
                    goToPaymentActivity(price,productName,imageUrl);
                    finish();
                }
            }
        });
        // Save button click listener
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save the edited user details
                saveUserData();
            }
        });

        // Change button click listener
        btnchage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle edit mode
                isEditMode = !isEditMode;
                // Enable EditText fields for editing
                enableEditTextFields(isEditMode);
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
        deliveryDateTextView.setText("Expected Arrival :- " + formattedDate);
    }

    private void fetchUserData() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null)
        {
            userRef = FirebaseDatabase.getInstance().getReference("users").child(currentUser.getUid()).child("userInfo");

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // User data exists, populate TextView fields
                    String userName = snapshot.child("firstName").getValue(String.class);
                    String userAddress = snapshot.child("address").getValue(String.class);
                    String userPhoneNumber = snapshot.child("phoneNumber").getValue(String.class);

                    // Populate TextView fields
                    usernameText.setText(userName);
                    addressText.setText(userAddress);
                    PhoneNumText.setText(userPhoneNumber);

                    // Disable TextView fields if data is editable
                    enableEditTextFields(false);
                } else {
                    // User data does not exist (new user), EditText fields remain enabled
                    enableEditTextFields(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
                Toast.makeText(Purchaseproduct.this, "Failed to fetch user data", Toast.LENGTH_SHORT).show();
            }
        });
    } else {
            // Handle the case where the user is not logged in
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity
        }
    }

    private void disableTextViewFields() {
        // Disable TextView fields after fetching user data
        usernameText.setEnabled(false);
        addressText.setEnabled(false);
        PhoneNumText.setEnabled(false);
    }

private void goToPaymentActivity(String productPrice, String productName, String imageUrl) {
    Intent paymentIntent = new Intent(Purchaseproduct.this, DummyUPIPayment.class);
    paymentIntent.putExtra("ProductName", productName);
    paymentIntent.putExtra("imageUrl", imageUrl);
    paymentIntent.putExtra("productPrice", productPrice);
    String deliveryDate = deliveryDateTextView.getText().toString();
    paymentIntent.putExtra("deliverydate", deliveryDate);

    Toast.makeText(this, "Now  UPI-Payment page will be open", Toast.LENGTH_SHORT).show();
    startActivity(paymentIntent);
    finish(); // Finish the current activity
}

//    private void goToCartActivity(String productPrice, String productName, String imageUrl) {
//        userRef = FirebaseDatabase.getInstance().getReference("cartproduct");
//        mAuth = FirebaseAuth.getInstance();
//        FirebaseUser user = mAuth.getCurrentUser();
//        if (user != null) {
//            String userId = user.getUid();
//
//            // Add the paid product to Firebase under the user's ID
//            DatabaseReference userOrderedProductsRef = userRef.child(userId);
//            String orderId = userOrderedProductsRef.push().getKey();
//            if (orderId != null) {
//                cartProduct cartProduct = new cartProduct(productName, imageUrl, productPrice, "Deliver in 7 days", orderId);
//                cartProduct.setQuantityNum(1);
//
//                userOrderedProductsRef.child(orderId).setValue(cartProduct)
//                        .addOnSuccessListener(aVoid -> {
//                            Log.d("Firebase", "Product added to cart");
//                            Intent i = new Intent(this,ProductCartActivity.class);
//                            startActivity(i);
//
//                        })
//                        .addOnFailureListener(e -> Log.e("FirebaseError", "Failed to add product", e));
//            }
//        }
//
//    }
private void goToCartActivity(String productPrice, String productName, String imageUrl) {
    userRef = FirebaseDatabase.getInstance().getReference("cartproduct");
    mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    if (user != null) {
        String userId = user.getUid();
        DatabaseReference userOrderedProductsRef = userRef.child(userId);

        userOrderedProductsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean productExists = false;

                for (DataSnapshot productSnap : snapshot.getChildren()) {
                    cartProduct existingProduct = productSnap.getValue(cartProduct.class);
                    if (existingProduct != null && existingProduct.getProductName().equals(productName)) {
                        // Product already in cart: increase quantity
                        int currentQty = existingProduct.getQuantityNum();
                        productSnap.getRef().child("quantityNum").setValue(currentQty + 1);
                        Toast.makeText(Purchaseproduct.this, "Product quantity updated in cart", Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(Purchaseproduct.this, ProductCartActivity.class);
                        startActivity(i);
                        productExists = true;
                        break;
                    }
                }

                if (!productExists) {
                    // Product not in cart: add new entry
                    String orderId = userOrderedProductsRef.push().getKey();
                    if (orderId != null) {
                        cartProduct newProduct = new cartProduct(productName, imageUrl, productPrice, "Deliver in 7 days", orderId);
                        newProduct.setQuantityNum(1);
                        userOrderedProductsRef.child(orderId).setValue(newProduct)
                                .addOnSuccessListener(aVoid -> {
                                    Log.d("Firebase", "Product added to cart");
                                    Intent i = new Intent(Purchaseproduct.this, ProductCartActivity.class);
                                    startActivity(i);
                                })
                                .addOnFailureListener(e -> Log.e("FirebaseError", "Failed to add product", e));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", "Failed to read cart", error.toException());
            }
        });
    }
}

    private void enableEditTextFields(boolean enable) {
        // Enable or disable EditText fields based on the given parameter
        usernameText.setEnabled(enable);
        addressText.setEnabled(enable);
        PhoneNumText.setEnabled(enable);
        // Change visibility of buttons accordingly
        if (enable) {
            btnchage.setVisibility(View.GONE);
            btnsave.setVisibility(View.VISIBLE);
        } else {
            btnchage.setVisibility(View.VISIBLE);
            btnsave.setVisibility(View.GONE);
        }
    }

    private void saveUserData() {
        // Check if EditText fields are empty
        if (usernameText.getText().toString().isEmpty() &&
                addressText.getText().toString().isEmpty()&&
                PhoneNumText.getText().toString().isEmpty()) {
            Toast.makeText(Purchaseproduct.this, "Please fill all details", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save user details to the database
        userRef.child("firstName").setValue(usernameText.getText().toString());
        userRef.child("address").setValue(addressText.getText().toString());
        userRef.child("phoneNumber").setValue(PhoneNumText.getText().toString());

        // Disable EditText fields after saving
        enableEditTextFields(false);

        // Show a toast message indicating successful save
        Toast.makeText(Purchaseproduct.this, "User details saved successfully", Toast.LENGTH_SHORT).show();
    }
}
