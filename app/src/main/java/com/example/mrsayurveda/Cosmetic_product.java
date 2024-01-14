package com.example.mrsayurveda;

import android.annotation.SuppressLint;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



public class Cosmetic_product extends AppCompatActivity {

    private RecyclerView recycleview;
    RecyclerView.LayoutManager layoutManager;

    cosmeticProductAdapter cosmeticProductAdapter;

    // ArrayList for person names
    String []product_name = {"MORGA","ORCHID","MEDIMIX","YASO","APPLE CIDER VINGER","CHANDRIKA","CHARCOAL BEAD","CHARCOAL","DHATHRI","ESSENCIA","BIO NEEM","FOREST ESSENTIALS","MULTANI MITTI","ONION OIL","RICE SCRUB","WALNUT","BIO KELP","SHAMPOO","NEEM OIL"};

    int []product_image = {R.drawable.body_moisturizer,R.drawable.bodylotion1,R.drawable.soap1,R.drawable.bodylotion2,R.drawable.facewash1,R.drawable.soap2,R.drawable.facewash2,R.drawable.facewash3,R.drawable.soap4,R.drawable.facewash4,R.drawable.facewash5,R.drawable.soap3,R.drawable.soap5,R.drawable.hairfall_control,R.drawable.scrub1,R.drawable.scrub2,R.drawable.shampoo1,R.drawable.shampoo2,R.drawable.neem_oil};
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cosmetic_product);
        // get the reference of RecyclerView

        RecyclerView recyclerView = findViewById(R.id.recyclerView); // Correct
        layoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);

        cosmeticProductAdapter =new cosmeticProductAdapter(product_image,product_name);
        recyclerView.setAdapter(cosmeticProductAdapter);

    }
}