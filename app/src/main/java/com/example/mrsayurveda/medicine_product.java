package com.example.mrsayurveda;

import android.annotation.SuppressLint;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



public class medicine_product extends AppCompatActivity {

    private RecyclerView recycleview;
    RecyclerView.LayoutManager layoutManager;

    MedicineProductAdapter medicineProductAdapter;

    // ArrayList for person names
    String []product_name = {"Tulasi","Hadjod","Renogrit","Maharshi Ayurveda","Pilex","Thyrogrit","Ayu Sleep","B-Capsules","Sugar Knocker","Yakrit Plihantak","Dabar","Zero Block","Pachan Fatafat","Diabdex","Pureprash","Asthma","Sfido","Bhringrajasav","Divya","Tulsi D.S.","K-Cuff","Drishti","Saumya","Al-Shams","Isoamrit","Sunetra","Vedistry","Pravek","Haridra","Haridrakhandam","Aller Gi"};



    int []product_image = {R.drawable.medical,R.drawable.cosmetic};

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_product);
        // get the reference of RecyclerView

        RecyclerView recyclerView = findViewById(R.id.recyclerView); // Correct
        layoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);

        medicineProductAdapter =new MedicineProductAdapter(product_image,product_name);
        recyclerView.setAdapter(medicineProductAdapter);

    }
}