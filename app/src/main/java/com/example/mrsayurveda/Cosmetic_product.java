package com.example.mrsayurveda;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.mrsayurveda.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.util.ArrayList;
import java.util.List;

public class Cosmetic_product extends AppCompatActivity {

    private RecyclerView recyclerView;
    DatabaseReference database;
    cosmeticProductAdapter CosmeticProductAdapter;
    ArrayList<ProductList> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cosmetic_product);

    // database for accessing images and its url
        recyclerView=findViewById(R.id.recyclerView);
        database= FirebaseDatabase.getInstance().getReference("CosmeticProductlist");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        list=new ArrayList<>();
        CosmeticProductAdapter=new cosmeticProductAdapter(Cosmetic_product.this,list);
        recyclerView.setAdapter(CosmeticProductAdapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list.clear();

                for (DataSnapshot categorySnapshot : snapshot.getChildren()) {
                    for (DataSnapshot dataSnapshot : categorySnapshot.getChildren()) {
                        //Images and its name are displayed


                        ProductList ProductList = dataSnapshot.getValue(ProductList.class);
                        list.add(ProductList);


                    }
                }
                if (CosmeticProductAdapter != null) {
                    CosmeticProductAdapter.notifyDataSetChanged();
                }
              //  CosmeticProductAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            //handle error
                Toast.makeText(Cosmetic_product.this, "Database Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });




   }
}






