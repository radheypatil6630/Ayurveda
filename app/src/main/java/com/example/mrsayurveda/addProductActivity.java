package com.example.mrsayurveda;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class addProductActivity extends AppCompatActivity {
    private Bitmap bitmap;
    private ImageView productImage;
    private EditText productName, productPrice, productDescription;
    private Button addProductBtn;
    private ListView categoryListView, subcategoryListView;

    private ArrayList<String> categoryData = new ArrayList<>();
    private ArrayList<String> subCategoryData = new ArrayList<>();

    private String selectedCategory = null;
    private String selectedSubCategory = null;
    private String imageUrl = "https://www.flaticon.com/free-icon/products_1312091?term=cosmetics&page=1&position=8&origin=tag&related_id=1312091";

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        productImage = findViewById(R.id.image);
        productName = findViewById(R.id.productName);
        productPrice = findViewById(R.id.productPrice);
        productDescription = findViewById(R.id.productDescription);
        categoryListView = findViewById(R.id.categoryList);
        subcategoryListView = findViewById(R.id.subcategoryList);
        addProductBtn = findViewById(R.id.addProductBtn);

        loadCategoryData();

        categoryListView.setOnItemClickListener((adapterView, view, i, l) -> {
            selectedCategory = categoryData.get(i);
            Toast.makeText(this, "Category: " + selectedCategory, Toast.LENGTH_SHORT).show();
            loadSubCategoryData(selectedCategory);
        });

        subcategoryListView.setOnItemClickListener((adapterView, view, i, l) -> {
            selectedSubCategory = subCategoryData.get(i);
            Toast.makeText(this, "Subcategory: " + selectedSubCategory, Toast.LENGTH_SHORT).show();
        });

        productImage.setOnClickListener(view -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("Do you want to change product image?");
            alert.setPositiveButton("Yes", (dialogInterface, i) -> {
                Intent in = new Intent(this, ChangeProductImage.class);
                startActivity(in);
            });
            alert.setNegativeButton("No", null);
            alert.create().show();
        });

        loadProductImage();

        addProductBtn.setOnClickListener(v -> {
            String p_name = productName.getText().toString().trim();
            String p_price = productPrice.getText().toString().trim();
            String p_description = productDescription.getText().toString().trim();

            if (p_name.isEmpty()) {
                productName.setError("Product name required");
                productName.requestFocus();
            } else if (p_price.isEmpty()) {
                productPrice.setError("Product price required");
                productPrice.requestFocus();
            } else if (p_description.isEmpty()) {
                productDescription.setError("Product description required");
                productDescription.requestFocus();
            } else if (selectedCategory == null || selectedSubCategory == null) {
                Toast.makeText(this, "Please select a category and subcategory", Toast.LENGTH_SHORT).show();
            } else {
                DatabaseReference subCategoryRef = FirebaseDatabase.getInstance()
                        .getReference("ProductList")
                        .child(selectedCategory)
                        .child(selectedSubCategory);

                subCategoryRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        long count = snapshot.getChildrenCount();
                        String productKey = "Product" + (count + 1);



                        DatabaseReference productRef = subCategoryRef.child(productKey);
                        addProduct product = new addProduct(
                                imageUrl, p_name, p_price, p_description
                        );

                        productRef.setValue(product)
                                .addOnSuccessListener(unused -> {
                                    Toast.makeText(addProductActivity.this, "Product Added as " + productKey, Toast.LENGTH_SHORT).show();
                                    finish();
                                })
                                .addOnFailureListener(e ->
                                        Toast.makeText(addProductActivity.this, "Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(addProductActivity.this, "Failed to access subcategory", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


    private void loadCategoryData() {
        databaseReference.child("ProductList").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                categoryData.clear();
                for (DataSnapshot categorySnap : snapshot.getChildren()) {
                    categoryData.add(categorySnap.getKey());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(addProductActivity.this,
                        android.R.layout.simple_list_item_1, categoryData);
                categoryListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(addProductActivity.this, "Failed to load categories", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadSubCategoryData(String category) {
        databaseReference.child("ProductList").child(category)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        subCategoryData.clear();
                        for (DataSnapshot subCatSnap : snapshot.getChildren()) {
                            subCategoryData.add(subCatSnap.getKey());
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(addProductActivity.this,
                                android.R.layout.simple_list_item_1, subCategoryData);
                        subcategoryListView.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(addProductActivity.this, "Failed to load subcategories", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loadProductImage() {
        StorageReference storageReference;
            FirebaseAuth mAuth = FirebaseAuth.getInstance();


            storageReference= FirebaseStorage.getInstance().getReference("ProductImages/");
            try {
                File localfile= File.createTempFile("tempfile",".jpg");
                storageReference.getFile(localfile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        DisplayMetrics dm=new DisplayMetrics();
                         bitmap= BitmapFactory.decodeFile(localfile.getAbsolutePath());
                        productImage.setImageBitmap(bitmap);

                        storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                            imageUrl = uri.toString(); // ✅ Save this URL for Firebase DB
                            Toast.makeText(addProductActivity.this, "Image loaded", Toast.LENGTH_SHORT).show();
                        }).addOnFailureListener(e -> {
                            Toast.makeText(addProductActivity.this, "Failed to get image URL", Toast.LENGTH_SHORT).show();
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(addProductActivity.this, "failed to load profile image", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}

