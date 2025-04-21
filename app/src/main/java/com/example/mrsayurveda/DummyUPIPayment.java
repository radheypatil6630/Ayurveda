package com.example.mrsayurveda;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DummyUPIPayment extends AppCompatActivity {

    EditText upiPasswordEditText;
    Button payButton;
    TextView totalAmount;
    String imageUrl;
    String productName;
    String price, purchasedPrice,addToCartPrice;
    String deliveryDateTextView;

    private final StringBuilder passwordBuilder = new StringBuilder();

    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy_upipayment);

        // Initialize Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("orderedproduct");
        firebaseAuth = FirebaseAuth.getInstance();

        // Initialize views
        upiPasswordEditText = findViewById(R.id.pinpass);
        payButton = findViewById(R.id.pay);
        totalAmount=findViewById(R.id.totalAmount);

//        addToCartPrice= getIntent().getStringExtra("addToCartproductPrice");
//        totalAmount.setText("₹ "+addToCartPrice);

        // Retrieve the product details from the Intent extras
         imageUrl = getIntent().getStringExtra("imageUrl");
         productName = getIntent().getStringExtra("ProductName");
         price = getIntent().getStringExtra("productPrice");
         deliveryDateTextView = getIntent().getStringExtra("deliverydate");
        totalAmount.setText("₹ "+price);

        // Disable soft keyboard for EditText
        upiPasswordEditText.setRawInputType(InputType.TYPE_NULL);

        // Hide soft keyboard when EditText is clicked
        upiPasswordEditText.setOnClickListener(v -> {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        });


        // Set up click listeners for numeric buttons
        findViewById(R.id.one).setOnClickListener(v -> appendValueToPassword("1"));
        findViewById(R.id.two).setOnClickListener(v -> appendValueToPassword("2"));
        findViewById(R.id.three).setOnClickListener(v -> appendValueToPassword("3"));
        findViewById(R.id.four).setOnClickListener(v -> appendValueToPassword("4"));
        findViewById(R.id.five).setOnClickListener(v -> appendValueToPassword("5"));
        findViewById(R.id.six).setOnClickListener(v -> appendValueToPassword("6"));
        findViewById(R.id.seven).setOnClickListener(v -> appendValueToPassword("7"));
        findViewById(R.id.eight).setOnClickListener(v -> appendValueToPassword("8"));
        findViewById(R.id.nine).setOnClickListener(v -> appendValueToPassword("9"));
        findViewById(R.id.zero).setOnClickListener(v -> appendValueToPassword("0"));

        // Set up click listener for clear button
        findViewById(R.id.clrbtn).setOnClickListener(v -> deleteLastCharacter());

        // Set up click listener for pay button
        payButton.setOnClickListener(v -> performDummyPayment());
    }


        private void appendValueToPassword(String value) {
            // Ensure the length doesn't exceed 6 digits
            if (passwordBuilder.length() < 6) {
                // Insert the new digit at the end of the StringBuilder
                passwordBuilder.append(value);
                updatePasswordEditText();
            }
        }


    private void deleteLastCharacter() {
        if (passwordBuilder.length() > 0) {
            passwordBuilder.deleteCharAt(passwordBuilder.length() - 1);
            updatePasswordEditText();
        }
    }

    private void updatePasswordEditText() {
// Reverse the password string before setting it to the EditText
        String password = passwordBuilder.toString();
        upiPasswordEditText.setText(password);

        // After setting the text, restore the original order of the StringBuilder
      //  passwordBuilder.reverse();
    }
    private void performDummyPayment() {
        // Check if the PIN is at least 6 digits long
        if (passwordBuilder.length() >= 6) {
            // Convert entered PIN to a string
            String enteredPIN = passwordBuilder.toString();
            Log.d("Entered PIN", enteredPIN);
            // Check if the entered PIN matches the correct PIN ("123456")
            if (enteredPIN.equals("123456")) {
                // Payment successful
                Toast.makeText(this, "Payment is successfully done", Toast.LENGTH_SHORT).show();
                // Inside performDummyPayment method, after payment is successful
                // Get the current user ID
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    String userId = user.getUid();

                    // Add the paid product to Firebase under the user's ID
                    DatabaseReference userOrderedProductsRef = databaseReference.child(userId);
                    String orderId = userOrderedProductsRef.push().getKey();
                    OrderedProduct orderedProduct = new OrderedProduct(productName, imageUrl, price, deliveryDateTextView,orderId);
                    if (orderId != null) {
                        userOrderedProductsRef.child(orderId).setValue(orderedProduct)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        // Product added successfully
                                        navigateToOrderHistoryActivity();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Failed to add product
                                        showErrorDialog("Failed to add ordered product to database: " + e.getMessage());
                                    }
                                });
                    } else {
                        showErrorDialog("Failed to add ordered product to database");
                    }

                } else {
                    showErrorDialog("User not authenticated");
                }
                // Navigate to the order activity or perform any other action
            } else {
                // Incorrect PIN
                showErrorDialog("PIN is incorrect");

                // Clear the PIN digits and allow the user to enter a new PIN
                clearPIN();
            }
        } else {
            // PIN is too short
            showErrorDialog("PIN must be at least 6 digits long");
        }
    }

//    OrderHistoryActivity
    private void navigateToOrderHistoryActivity() {
        Intent intent = new Intent(DummyUPIPayment.this, OrderHistoryActivity.class);
        startActivity(intent);
        finish(); // Finish the current activity after navigating to OrderHistoryActivity
    }


    private void showErrorDialog(String errorMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Payment Error")
                .setMessage(errorMessage)
                .setPositiveButton("OK", (dialog, which) -> {
                    // Handle OK button click if needed
                    dialog.dismiss();
                })
                .create()
                .show();
    }
    private void clearPIN() {
        // Clear the passwordBuilder and update the EditText
        passwordBuilder.setLength(0);
        updatePasswordEditText();
    }
}
