package com.example.mrsayurveda;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private List<ProductList> productList;
    private ProductViewHolder adapter;
   // private FirebaseRecyclerAdapter<ProductList, ProductViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        // Get the category from the intent
        String category = getIntent().getStringExtra("CATEGORY");
        productList = new ArrayList<>();
        adapter = new ProductViewHolder(productList, new ProductViewHolder.OnItemClickListener()  {
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

        // Set the reference to the specific category in the database
      //  databaseReference = FirebaseDatabase.getInstance().getReference().child("ProductList").child(category);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("ProductList").child(category);


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot categorySnapshot : dataSnapshot.getChildren()) {
//                    // First loop for categories (e.g., Cosmetic, Food, Medical)
//                    String category = categorySnapshot.getKey();
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

                            productList.add(product);
//

                    }
                }



                adapter.notifyDataSetChanged();
                // After retrieving data, set up the RecyclerView adapter
//                adapter = new ProductViewHolder(productList);
//                recyclerView.setAdapter(adapter);
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
                intent.putExtra("ProductDescription",  product.getDescription()); // Add description logic
                intent.putExtra("price", product.getPrice());
                startActivity(intent);
            }
        });
    }



}
