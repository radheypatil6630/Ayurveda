package com.example.mrsayurveda;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProductCartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private TextView subtotal;
    private Button proceedBuyBtn;

    productCartViewHolder adapter;
    List<cartProduct> cartProductsList;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    private String deliveryDate;

    private int totalPrice,totalItems;
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
        setContentView(R.layout.activity_product_cart);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            // Set your desired icon for the navigation drawer toggle
        }
        getSupportActionBar().setTitle("Product Cart");

        firebaseAuth = FirebaseAuth.getInstance();




        // Initialize RecyclerView and product list
        recyclerView = findViewById(R.id.product_cart_recycleview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        subtotal = findViewById(R.id.subtotal);

        notFoundAnimation = findViewById(R.id.NotFoundAnimation1);
        loadingAnimation = findViewById(R.id.LoadingAnimation3);

        proceedBuyBtn = findViewById(R.id.proceed_buyButton);

        cartProductsList = new ArrayList<>();


        proceedBuyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent paymentIntent = new Intent(ProductCartActivity.this, DummyUPIPayment.class);
//                paymentIntent.putExtra("ProductName", productName);
//                paymentIntent.putExtra("imageUrl", imageUrl);
                Log.d("good",Integer.toString(totalPrice));
                paymentIntent.putExtra("addToCartproductPrice", totalPrice);

//                String deliveryDate = deliveryDateTextView.getText().toString();
//                paymentIntent.putExtra("deliverydate", deliveryDate);

                Toast.makeText(ProductCartActivity.this, "Now  UPI-Payment page will be open", Toast.LENGTH_SHORT).show();
                startActivity(paymentIntent);
                finish();
            }
        });


        // Initialize Firebase

        // Load ordered products
        loadCartProducts();

    }

    private void loadCartProducts() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();

            loadingAnimation.setVisibility(View.VISIBLE);
            loadingAnimation.playAnimation();

            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("cartproduct").child(userId);

            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    cartProductsList.clear();
                    for (DataSnapshot orderIdSnapshot : dataSnapshot.getChildren()) {
                        //  for(DataSnapshot productId: orderIdSnapshot.getChildren()) {
                        String productName = orderIdSnapshot.child("productName").getValue(String.class);
                        String imageUrl = orderIdSnapshot.child("imageUrl").getValue(String.class);
                        String price = orderIdSnapshot.child("productPrice").getValue(String.class);
                        String deliveryDate = orderIdSnapshot.child("deliveryDate").getValue(String.class);
                        int quantityValue  = orderIdSnapshot.child("quantityNum").getValue(int.class);
                        boolean checkBox_status = orderIdSnapshot.child("checkbox_status").getValue(boolean.class);
                        String orderId = orderIdSnapshot.getKey(); // Retrieve orderId

                        Log.d("good",productName+" "+quantityValue +" "+checkBox_status);
                        cartProduct cartProduct = new cartProduct(productName, imageUrl, price, deliveryDate, orderId);
                        cartProduct.setQuantityNum(quantityValue);
                        cartProduct.setCheckbox_status(checkBox_status);

                        cartProductsList.add(cartProduct);

                        loadingAnimation.cancelAnimation();
                        loadingAnimation.setVisibility(View.GONE);
                    }


                    if (adapter == null) {
                        adapter = new productCartViewHolder(cartProductsList);
                        recyclerView.setAdapter(adapter);

                    } else {
                        // Notify the adapter that the data set has changed
                        adapter.notifyDataSetChanged();
                    }
                    // Check if the cartProductsList is empty
                    if (cartProductsList.isEmpty()) {
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
                        Toast.makeText(ProductCartActivity.this, "No orders found.", Toast.LENGTH_SHORT).show();
                    }
                    updateSubtotalAndButton();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(ProductCartActivity.this, "Failed to load ordered products.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }



    public void updateSubtotalAndButton() {
        totalPrice = 0;
        totalItems = 0;

        // Iterate through cart items to calculate the total price and item count
        for (cartProduct product : cartProductsList) {
            if (product.isCheckbox_status()) {
                totalPrice += Integer.parseInt(product.getProductPrice()) * product.getQuantityNum();
                totalItems++;
            }
        }

        // Update subtotal and button text
        subtotal.setText("Subtotal: ₹" + totalPrice);
        proceedBuyBtn.setText("Proceed to Buy (" + totalItems + " items)");
    }

    // When checkbox is clicked, update Firebase and recalculate the subtotal
    public void updateCheckboxStatus(int position, boolean isChecked) {
        cartProduct product = cartProductsList.get(position);
        product.setCheckbox_status(isChecked);

        // Update checkbox status in Firebase
        String orderId = product.getOrderId();
        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("users").child("user_id").child(orderId);
        productRef.child("checkbox_status").setValue(isChecked);

        // Recalculate and update subtotal and button count
        updateSubtotalAndButton();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        // Navigate back to the home page
        startActivity(new Intent(ProductCartActivity.this, homeActivity.class));
        finish();
        return true;
    }
    // Method to display cancellation confirmation dialog
    public void onCancelClicked(cartProduct product, int position) {
        showCancellationConfirmationDialog(product, position);
    }

    private void showCancellationConfirmationDialog(cartProduct product, int position) {
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

    private void cancelProduct(cartProduct product, int position) {
        Toast.makeText(this, "Your Product is canceled .", Toast.LENGTH_SHORT).show();

        // Remove the product from the cartProductsList
        cartProductsList.remove(position);
        // Notify the adapter that an item has been removed
        adapter.notifyItemRemoved(position);

        // Get the user ID
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            // Get the reference to the ordered product for the current user
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("cartproduct").child(userId);
            String orderId = product.getOrderId();
            if (orderId != null) {
                // Remove the ordered product node from Firebase
                userRef.child(orderId).removeValue()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Product successfully removed from Firebase
                                Log.d("Cancel Product", "Product removed from Firebase");
                                //  finish();

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