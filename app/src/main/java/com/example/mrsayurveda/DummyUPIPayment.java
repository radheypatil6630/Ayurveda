package com.example.mrsayurveda;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class DummyUPIPayment extends AppCompatActivity {

    EditText upiPasswordEditText;
    Button payButton;

    private final StringBuilder passwordBuilder = new StringBuilder();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy_upipayment);

        // Initialize views
        upiPasswordEditText = findViewById(R.id.pinpass);
        payButton = findViewById(R.id.pay);

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
                passwordBuilder.insert(0, value);
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
        upiPasswordEditText.setText(passwordBuilder.toString());
    }

    private void performDummyPayment() {
        // Check if the PIN is at least 6 digits long
        if (passwordBuilder.length() >= 6) {
            // Check if the entered PIN matches the correct PIN ("123456")
            if (passwordBuilder.toString().equals("123456")) {
                // Payment successful

                Toast.makeText(this, "Payment Successful!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DummyUPIPayment.this, OrderHistoryActivity.class);
                startActivity(intent);
                // Navigate to the order activity or perform any other action
            } else {
                // Incorrect PIN
                Toast.makeText(this, "PIN is incorrect", Toast.LENGTH_SHORT).show();
            }
        } else {
            // PIN is too short
            Toast.makeText(this, "PIN must be at least 6 digits long", Toast.LENGTH_SHORT).show();
        }
    }
}
