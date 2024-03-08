package com.example.mrsayurveda;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

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

    private EditText searchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        searchEditText = findViewById(R.id.editTextText4);

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

        // Set the reference to the specific category in the database

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
            }
        });
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Filter products based on search query as the user types
                filterProducts(editable.toString());
            }
        });
    }

    private void filterProducts(String query) {
        List<ProductList> filteredList = new ArrayList<>();

        if (query.isEmpty()) {
            // If the search query is empty, display the entire product list
            filteredList.addAll(productList);
        } else {
            for (ProductList product : productList) {
                if (product != null && product.getProductType() != null) {
                    // String productName = product.getProductName();
                    if (product.getProductName() != null && product.getProductName().toLowerCase().contains(query.toLowerCase()) || product.getProductType().toLowerCase().contains(query.toLowerCase())) {
                        filteredList.add(product);
                    }

                }
            }
        }
        // Update the adapter with filtered results
        adapter.filterList(filteredList);

        // Show a toast message if no results are found
        if (filteredList.isEmpty() && !query.isEmpty()) {
            Toast.makeText(ProductListActivity.this, "No product found", Toast.LENGTH_SHORT).show();
        }
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
                Toast.makeText(ProductListActivity.this, "No ProductType found", Toast.LENGTH_SHORT).show();
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