package com.example.mrsayurveda;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
//    ProductViewHolder adapter;
//    List<ProductList> orderedProductsList;
    OrderViewHolder adapter;
    List<OrderedProduct> orderedProductsList;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    private String deliveryDate;
    private LottieAnimationView notFoundAnimation,loadingAnimation;





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
        getSupportActionBar().setTitle("Orders");




        // Initialize RecyclerView and product list
        recyclerView = findViewById(R.id.orderrecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        orderedProductsList = new ArrayList<>();
        notFoundAnimation = findViewById(R.id.NotFoundAnimation2);
        loadingAnimation = findViewById(R.id.LoadingAnimatio2);


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

            loadingAnimation.setVisibility(View.VISIBLE);
            loadingAnimation.playAnimation();

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

                        loadingAnimation.cancelAnimation();
                        loadingAnimation.setVisibility(View.GONE);
                    }

                    // Initialize the adapter if it's null
                 //   Log.d("values1", "onDataChange: "+orderedProductsList);
                    if (adapter == null) {
                        adapter = new OrderViewHolder(orderedProductsList);
                        recyclerView.setAdapter(adapter);

                    } else {
                        // Notify the adapter that the data set has changed
                        adapter.notifyDataSetChanged();
                    }
                    // Check if the orderedProductsList is empty
                    if (orderedProductsList.isEmpty()) {
                        // Show a toast indicating that the activity is empty

                        recyclerView.setVisibility(View.GONE);
                        notFoundAnimation.setVisibility(View.VISIBLE);
                        notFoundAnimation.playAnimation();
//                        notFoundAnimation.addAnimatorListener(new Animator.AnimatorListener() {
//                            @Override
//                            public void onAnimationStart(Animator animation) {
//                                // nothing
//                            }
//
//                            @Override
//                            public void onAnimationEnd(Animator animation) {
//
//                                recyclerView.setVisibility(View.VISIBLE);
//                                notFoundAnimation.setVisibility(View.GONE);
//                            }
//
//                            @Override
//                            public void onAnimationCancel(Animator animation) {}
//
//                            @Override
//                            public void onAnimationRepeat(Animator animation) {}
//                        });
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






}
