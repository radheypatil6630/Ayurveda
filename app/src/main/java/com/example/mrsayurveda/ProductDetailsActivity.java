package com.example.mrsayurveda;

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
        String imageUrl = getIntent().getStringExtra("IMAGE_URL");
        String productName = getIntent().getStringExtra("PRODUCT_NAME");
        String productDescription = getIntent().getStringExtra("PRODUCT_DESCRIPTION");
        String productPrice = getIntent().getStringExtra("PRODUCT_PRICE");

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
        productPriceTextView.setText(productPrice);

        // Add click listener to purchase button if needed
        purchaseButton.setOnClickListener(v -> {
            // Handle purchase button click
        });
    }
}

