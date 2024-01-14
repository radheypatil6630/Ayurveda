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



    int []product_image = {R.drawable.patanjali_resporatary,R.drawable.patanjali_bone,R.drawable.patanjali_renogrit,R.drawable.patanjali_bp,R.drawable.piles,R.drawable.thyrogrit,R.drawable.zamdu_spray,R.drawable.bp,R.drawable.blood_glucoose,R.drawable.diagestion,R.drawable.dabar,R.drawable.diabetics,R.drawable.pachak_medicine,R.drawable.sheopal,R.drawable.chawanprash,R.drawable.asthma,R.drawable.brain_booster,R.drawable.patanjali_khasi,R.drawable.khasi3,R.drawable.patanjali_khasi,R.drawable.khasi1,R.drawable.eyedrop6,R.drawable.eyedrop5,R.drawable.eyedrop4,R.drawable.eyedrop3,R.drawable.eyedrop2,R.drawable.skin1,R.drawable.skin2,R.drawable.skin3,R.drawable.skin4,R.drawable.skin5};

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