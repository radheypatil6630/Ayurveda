package com.example.mrsayurveda;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Purchaseproduct extends AppCompatActivity {
    Button buyButton ,cancelButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchaseproduct);

        buyButton= findViewById(R.id.buyButton);
        cancelButton= findViewById(R.id.cancelButton);

        String productName = getIntent().getStringExtra("ProductName");
        String productPrice = getIntent().getStringExtra("price");

        buyButton.setOnClickListener(v -> {
            // Handle buy button click
            Toast.makeText(this, "Product purchased: " + productName + " for " + productPrice, Toast.LENGTH_SHORT).show();
            finish();  // Close this activity after purchase
        });

        cancelButton.setOnClickListener(v -> {
            // Handle cancel button click
            finish();  // Close this activity without purchase
        });
    }
}
