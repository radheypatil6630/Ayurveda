package com.example.mrsayurveda;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrderHistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    ProductViewHolder adapter;
    List<ProductList> orderedProductsList;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;




    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            // Handle the back button
            case android.R.id.home:
                onBackPressed(); // This will call the default back button behavior
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            // Set your desired icon for the navigation drawer toggle
        }
        getSupportActionBar().setTitle("Product Cart");

        // Initialize RecyclerView and product list
        recyclerView = findViewById(R.id.orderrecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        orderedProductsList = new ArrayList<>();


        // Initialize Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("orderedproduct");
        firebaseAuth = FirebaseAuth.getInstance();

        // Load ordered products
        loadOrderedProducts();
    }

    private void loadOrderedProducts() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String userId = user.getUid();

        DatabaseReference userRef = databaseReference.child(userId);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                orderedProductsList.clear();
                for (DataSnapshot orderIdSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot productSnapshot : orderIdSnapshot.getChildren()) {
                        String productName = productSnapshot.child("productName").getValue(String.class);
                        String imageUrl = productSnapshot.child("imageUrl").getValue(String.class);
                        String price = productSnapshot.child("price").getValue(String.class);

                        // Create a ProductList object and add it to the list
                        ProductList orderedProduct = new ProductList(productName, imageUrl, price);
                        orderedProductsList.add(orderedProduct);
                    }
                }
                // Initialize the adapter if it's null
                if (adapter == null) {
                    adapter = new ProductViewHolder(orderedProductsList, new ProductViewHolder.OnItemClickListener() {
                        @Override
                        public void onItemClick(ProductList product, int position) {
                            // Handle item click if needed
                        }
                    });
                    recyclerView.setAdapter(adapter);
                } else {
                    // Notify the adapter that the data set has changed
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(OrderHistoryActivity.this, "Failed to load ordered products.", Toast.LENGTH_SHORT).show();
            }
        });
    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        // Navigate back to the home page
        startActivity(new Intent(OrderHistoryActivity.this, homeActivity.class));
        finish();
        return true;
    }
    // Method to display cancellation confirmation dialog
    private void showCancellationConfirmationDialog(ProductList product, int position) {
        new AlertDialog.Builder(this)
                .setTitle("Confirm Cancellation")
                .setMessage("Are you sure you want to cancel this product?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cancelProduct(product, position);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    // Method to cancel the product
    private void cancelProduct(ProductList product, int position) {
        Toast.makeText(this, "Product canceled.", Toast.LENGTH_SHORT).show();
        // Remove the product from the list
        orderedProductsList.remove(position);
        // Notify the adapter that the data set has changed
        adapter.notifyItemRemoved(position);
    }
}
