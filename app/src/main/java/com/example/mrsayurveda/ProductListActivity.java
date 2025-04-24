package com.example.mrsayurveda;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private List<ProductList> productList;
    private ProductViewHolder adapter;

    private FirebaseAuth firebaseAuth;

    private EditText searchEditText;
    private LottieAnimationView notFoundAnimation,loadingAnimation;

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
        setContentView(R.layout.activity_product_list);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        searchEditText = findViewById(R.id.editTextText4);
        notFoundAnimation = findViewById(R.id.NotFoundAnimation3);
        loadingAnimation = findViewById(R.id.LoadingAnimation1);

        // Get the category from the intent
        String category = getIntent().getStringExtra("CATEGORY");
        productList = new ArrayList<>();
        adapter = new ProductViewHolder(productList, new ProductViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(ProductList product, int position) {
                // Handle item click
                // Example: Open ProductDetailsActivity and pass product details
                Intent intent = new Intent(ProductListActivity.this, ProductDetailsActivity.class);
                intent.putExtra("imageUrl", product.getImageUrl());
                intent.putExtra("ProductName", product.getProductName());
                intent.putExtra("ProductDescription", product.getDescription()); // Add description logic
                intent.putExtra("price", product.getPrice());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);

        loadingAnimation.setVisibility(View.VISIBLE);
        loadingAnimation.playAnimation();

       databaseReference = FirebaseDatabase.getInstance().getReference().child("ProductList").child(category);


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot productTypeSnapshot : dataSnapshot.getChildren()) {

                    // Second loop for product types within each category (e.g., soap, biscuit, liver)
                    String productType = productTypeSnapshot.getKey();

                    for (DataSnapshot productSnapshot : productTypeSnapshot.getChildren()) {
                        // Third loop for products within each product type (e.g., Product1, Product2, ...)
                        String productName = productSnapshot.child("ProductName").getValue(String.class);
                        String imageUrl = productSnapshot.child("imageUrl").getValue(String.class);
                        String price = productSnapshot.child("price").getValue(String.class);
                        String description = productSnapshot.child("description").getValue(String.class);

                        ProductList product = new ProductList();
                        product.setProductName(productName);
                        product.setImageUrl(imageUrl);
                        product.setPrice(price);
                        product.setDescription(description);
                        product.setProductType(productType);

                        productList.add(product);
//

                    }
                }


                adapter.notifyDataSetChanged();

                loadingAnimation.cancelAnimation();
                loadingAnimation.setVisibility(View.GONE);

                // Check if productList is empty after populating it
                if (productList.isEmpty()) {
                    recyclerView.setVisibility(View.GONE);
                    notFoundAnimation.setVisibility(View.VISIBLE);
                    notFoundAnimation.playAnimation();

//                    notFoundAnimation.addAnimatorListener(new Animator.AnimatorListener() {
//                        @Override
//                        public void onAnimationStart(Animator animation) {
//                            // nothing
//                        }
//
//                        @Override
//                        public void onAnimationEnd(Animator animation) {
//
//                            recyclerView.setVisibility(View.VISIBLE);
//                            notFoundAnimation.setVisibility(View.GONE);
//                        }
//
//                        @Override
//                        public void onAnimationCancel(Animator animation) {}
//
//                        @Override
//                        public void onAnimationRepeat(Animator animation) {}
//                    });
//                    Toast.makeText(ProductListActivity.this, "No products found", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("FirebaseData", "Error loading data", databaseError.toException());
            }
        });
        adapter.setOnItemClickListener(new ProductViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(ProductList product, int position) {
                // Handle item click
                Intent intent = new Intent(ProductListActivity.this, ProductDetailsActivity.class);
                intent.putExtra("imageUrl", product.getImageUrl());
                intent.putExtra("ProductName", product.getProductName());
                intent.putExtra("ProductDescription", product.getDescription()); // Add description logic
                intent.putExtra("price", product.getPrice());
                startActivity(intent);
                finish();
            }
        });


        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed for this implementation
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString().toLowerCase();
                filter(query);
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Not needed for this implementation
            }
        });


    }

    private void filter(String query) {
        List<ProductList> filteredList = new ArrayList<>();

        for (ProductList product : productList) {
            if (product.getProductName().toLowerCase().contains(query) ||
                    product.getProductType().toLowerCase().contains(query)) {
                filteredList.add(product);
            }
        }
        if (filteredList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            notFoundAnimation.setVisibility(View.VISIBLE);
            notFoundAnimation.playAnimation();


                notFoundAnimation.addAnimatorListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    // nothing
                }

                @Override
                public void onAnimationEnd(Animator animation) {

                    recyclerView.setVisibility(View.VISIBLE);
                    notFoundAnimation.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {}

                @Override
                public void onAnimationRepeat(Animator animation) {}
            });

//
//            Toast.makeText(ProductListActivity.this, "No ProductType found", Toast.LENGTH_SHORT).show();
        }
        // Update RecyclerView with filtered list
        adapter.filterList(filteredList);
    }



        private void showProductsByType (String productType){
            List<ProductList> filteredList = new ArrayList<>();

            for (ProductList product : productList) {
                if (product != null && product.getProductType() != null) {
                    if (product.getProductType().toLowerCase().equals(productType.toLowerCase())) {
                        filteredList.add(product);
                    }
                }
            }
            adapter.filterList(filteredList);

            // Show a toast message if no results are found
            if (filteredList.isEmpty()) {
                recyclerView.setVisibility(View.GONE);
                notFoundAnimation.setVisibility(View.VISIBLE);
                notFoundAnimation.playAnimation();

                notFoundAnimation.addAnimatorListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        // nothing
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                        recyclerView.setVisibility(View.VISIBLE);
                        notFoundAnimation.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {}

                    @Override
                    public void onAnimationRepeat(Animator animation) {}
                });
//                Toast.makeText(ProductListActivity.this, "No ProductType found", Toast.LENGTH_SHORT).show();
            }
        }
    @Override
    @SuppressWarnings("Deprecated")
    public void onBackPressed() {
        super.onBackPressed();

        // Refresh data when back button is pressed
      //  fetchDataFromFirebase();
    }

    private void fetchDataFromFirebase() {
        // Your existing code to fetch data from Firebase
        // ...

        // Notify adapter after fetching data
        adapter.notifyDataSetChanged();
    }

}