package com.example.mrsayurveda;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DummyUPIPayment extends AppCompatActivity {

    private TextView textViewAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy_upipayment);

        // Initialize views
        Button buttonPay = findViewById(R.id.pay);




        // Set click listener for the "Pay Now" button
        buttonPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Implement your payment logic here
                performDummyPayment();
            }
        });
    }


    private void performDummyPayment() {
        // Your dummy payment logic goes here
        Toast.makeText(this, "Payment Successful!", Toast.LENGTH_SHORT).show();
        finish(); // Close the activity after payment
    }
}
