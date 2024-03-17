package com.example.mrsayurveda;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
//    ProductViewHolder adapter;
//    List<ProductList> orderedProductsList;
    OrderViewHolder adapter;
    List<OrderedProduct> orderedProductsList;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    private String deliveryDate;





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

      //  deliveryDate = getIntent().getStringExtra("deliverydate");


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
        if (user != null) {
            String userId = user.getUid();
//            DatabaseReference userRef = databaseReference.child(userId);
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("orderedproduct").child(userId);

            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    orderedProductsList.clear();
                    for (DataSnapshot orderIdSnapshot : dataSnapshot.getChildren()) {
                      //  for(DataSnapshot productId: orderIdSnapshot.getChildren()) {
                            String productName = orderIdSnapshot.child("productName").getValue(String.class);
                            String imageUrl = orderIdSnapshot.child("imageUrl").getValue(String.class);
                            String price = orderIdSnapshot.child("productPrice").getValue(String.class);
                        String deliveryDate = orderIdSnapshot.child("deliveryDate").getValue(String.class);

                        String orderId = orderIdSnapshot.getKey(); // Retrieve orderId

                        OrderedProduct orderedProduct = new OrderedProduct(productName, imageUrl, price, deliveryDate, orderId);
                        orderedProductsList.add(orderedProduct);


                    }

                    // Initialize the adapter if it's null
                 //   Log.d("values1", "onDataChange: "+orderedProductsList);
                    if (adapter == null) {
                        adapter = new OrderViewHolder(orderedProductsList);
                        recyclerView.setAdapter(adapter);
//                        adapter = new OrderViewHolder(orderedProductsList ,new ProductViewHolder.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(ProductList product, int position) {
//                                // Handle item click if needed
//                            }
//                        });
                      //  recyclerView.setAdapter(adapter);
                    } else {
                        // Notify the adapter that the data set has changed
                        adapter.notifyDataSetChanged();
                    }
                    // Check if the orderedProductsList is empty
                    if (orderedProductsList.isEmpty()) {
                        // Show a toast indicating that the activity is empty
                        Toast.makeText(OrderHistoryActivity.this, "No orders found.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(OrderHistoryActivity.this, "Failed to load ordered products.", Toast.LENGTH_SHORT).show();
                }
            });
        }
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
    public void onCancelClicked(OrderedProduct product, int position) {
        showCancellationConfirmationDialog(product, position);
    }

    private void showCancellationConfirmationDialog(OrderedProduct product, int position) {
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

    private void cancelProduct(OrderedProduct product, int position) {
        Toast.makeText(this, "Product canceled.", Toast.LENGTH_SHORT).show();

        // Remove the product from the orderedProductsList
        orderedProductsList.remove(position);
        // Notify the adapter that an item has been removed
        adapter.notifyItemRemoved(position);

        // Get the user ID
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            // Get the reference to the ordered product for the current user
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("orderedproduct").child(userId);
            String orderId = product.getOrderId();
            if (orderId != null) {
                // Remove the ordered product node from Firebase
                userRef.child(orderId).removeValue()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Product successfully removed from Firebase
                                Log.d("Cancel Product", "Product removed from Firebase");


                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Failed to remove product from Firebase
                                Log.e("Cancel Product", "Failed to cancel product and remove from Firebase: " + e.getMessage());
                            }
                        });
            } else {
                Log.e("Cancel Product", "Order ID is null");
            }
        } else {
            Log.e("Cancel Product", "User is null");
        }
    }


}
