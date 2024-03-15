package com.example.mrsayurveda;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class ProductDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        // Assuming you passed product details through Intent
        Intent intent = getIntent();
        if (intent != null) {
            String imageUrl = getIntent().getStringExtra("imageUrl");
            String productName = getIntent().getStringExtra("ProductName");
            String productDescription = getIntent().getStringExtra("ProductDescription");
            String price = getIntent().getStringExtra("price");

            ImageView productImageView = findViewById(R.id.productImageView);
            TextView productNameTextView = findViewById(R.id.productNameTextView);
            TextView productDescriptionTextView = findViewById(R.id.productDescriptionTextView);
            TextView productPriceTextView = findViewById(R.id.productPriceTextView);
            Button purchaseButton = findViewById(R.id.purchaseButton);

            // Load image using Picasso library
            Picasso.get().load(imageUrl).into(productImageView);

            // Set text for other views
            productNameTextView.setText(productName);
            productDescriptionTextView.setText(productDescription);
            productPriceTextView.setText("₹"+price);

            // Add click listener to purchase button if needed
            purchaseButton.setOnClickListener(v -> {
                // Handle purchase button click
                Intent purchaseIntent = new Intent(ProductDetailsActivity.this,Purchaseproduct.class);
                purchaseIntent.putExtra("ProductName", productName);
                purchaseIntent.putExtra("price", price);
                purchaseIntent.putExtra("imageUrl", imageUrl);

                startActivity(purchaseIntent);
               finish();
            });
        }
    }
}

