package com.example.mrsayurveda;
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
        adapter = new ProductViewHolder(productList);
        recyclerView.setAdapter(adapter);

        // Set the reference to the specific category in the database
      //  databaseReference = FirebaseDatabase.getInstance().getReference().child("ProductList").child(category);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("ProductList").child(category);


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ProductList product = snapshot.getValue(ProductList.class);
                    if (product != null) {
                        productList.add(product);
                        Log.d("FirebaseData", "Product: " + product.getProductName() + ", " + product.getImageUrl() + ", " + product.getPrice());
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
    }


        //        FirebaseRecyclerOptions<ProductList> options =
//                new FirebaseRecyclerOptions.Builder<ProductList>()
//                        .setQuery(databaseReference, ProductList.class)
//                        .build();

//        adapter = new FirebaseRecyclerAdapter<ProductList, ProductViewHolder>(options) {
//
//@Override
//protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull ProductList model) {
//    // Bind data to the ViewHolder
////    holder.setDetails(model.getProductName(), model.getimageUrl(), model.getprice());
//    Log.d("FirebaseData", "Product Name: " + model.getProductName());
//    Log.d("FirebaseData", "Image URL: " + model.getImageUrl());
//    Log.d("FirebaseData", "Price: " + model.getPrice());
//    String productName = model.getProductName() != null ? model.getProductName() : "Default Name";
//    String imageUrl = model.getImageUrl() != null ? model.getImageUrl() : "Default Image URL";
//    String price = model.getPrice() != null ? model.getPrice() : "Default Price";
//
//    // Bind data to the ViewHolder
//    holder.setDetails(productName, imageUrl, price);
//}
//
//            @NonNull
//            @Override
//            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                // Create a new ViewHolder for each item
//                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_recycleview, parent, false);
//                return new ProductViewHolder(view);
//            }
//        };
//
//        recyclerView.setAdapter(adapter);
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        adapter.startListening();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        adapter.stopListening();
//    }
}
