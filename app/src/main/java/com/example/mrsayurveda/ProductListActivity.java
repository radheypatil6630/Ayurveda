package com.example.mrsayurveda;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ProductListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private FirebaseRecyclerAdapter<ProductList, ProductViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get the category from the intent
        String category = getIntent().getStringExtra("CATEGORY");

        // Set the reference to the specific category in the database
      //  databaseReference = FirebaseDatabase.getInstance().getReference().child("ProductList").child(category);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("ProductList").child(category);
        FirebaseRecyclerOptions<ProductList> options =
                new FirebaseRecyclerOptions.Builder<ProductList>()
                        .setQuery(databaseReference, ProductList.class)
                        .build();

        adapter = new FirebaseRecyclerAdapter<ProductList, ProductViewHolder>(options) {

@Override
protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull ProductList model) {
    // Bind data to the ViewHolder
    holder.setDetails(model.getProductName(), model.getimageUrl(), model.getprice());
}

            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                // Create a new ViewHolder for each item
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_recycleview, parent, false);
                return new ProductViewHolder(view);
            }
        };

        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
