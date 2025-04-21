//package com.example.mrsayurveda;
//
//import android.app.AlertDialog;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.os.Bundle;
//import android.util.DisplayMetrics;
//import android.view.View;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.ListView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.storage.FileDownloadTask;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//
//public class addProductActivity extends AppCompatActivity {
//    private ImageView productImage;
//    private EditText productName , productPrice , productDescription;
//    private Button addProductBtn;
//    private ListView categoryListView, subcategoryListView;
//
//    private ArrayList<String> categoryData = new ArrayList<>();
//    private ArrayList<String> subCategoryData = new ArrayList<>();
//
//    private String selectedCategory = null;
//    private String selectedSubCategory = null;
//    private DatabaseReference databaseReference;
//    private FirebaseAuth firebaseAuth;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_product);
//
//        firebaseAuth = FirebaseAuth.getInstance();
//        databaseReference = FirebaseDatabase.getInstance().getReference();
//
//        productImage = findViewById(R.id.image);
//        productName = findViewById(R.id.productName);
//        productPrice = findViewById(R.id.productPrice);
//        productDescription = findViewById(R.id.productDescription);
//        categoryListView = findViewById(R.id.categoryList);
//        subcategoryListView = findViewById(R.id.subcategoryList);
//
//        addProductBtn = findViewById(R.id.addProductBtn);
//
//        loadCategoryData();
//        loadSubCategoryData();
//
//        categoryListView.setOnItemClickListener((adapterView, view, i, l) -> {
//            selectedCategory = categoryData.get(i);
//            Toast.makeText(addProductActivity.this, "Category: " + selectedCategory, Toast.LENGTH_SHORT).show();
//            loadSubCategoryData(selectedCategory); // Add this!
//        });
//
//
//        // Handle subcategory selection
//        subcategoryListView.setOnItemClickListener((adapterView, view, i, l) -> {
//            selectedSubCategory = subCategoryData.get(i);
//            Toast.makeText(addProductActivity.this, "Subcategory: " + selectedSubCategory, Toast.LENGTH_SHORT).show();
//        });
//
//        productImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AlertDialog.Builder alert=new AlertDialog.Builder(addProductActivity.this);
//                alert.setMessage("Do you want to change profile image?");
//                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Intent in=new Intent(addProductActivity.this,ChangeProductImage.class);
//                        startActivity(in);
//                    }
//                });
//                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                    }
//                });
//                AlertDialog alertDialog=alert.create();
//                alertDialog.show();
//            }
//        });
//
//        loadProductImage();
//
//
//
//
//
//        addProductBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String p_name = productName.getText().toString();
//                String p_price = productPrice.getText().toString();
//                String p_description = productDescription.getText().toString();
//
//                if(p_name.equals("") && p_name.isEmpty()){
//                    productName.setError("product name required");
//                    productName.requestFocus();
//                }
//                else if(p_price.equals("") && p_price.isEmpty()){
//                    productPrice.setError("product price required");
//                    productPrice.requestFocus();
//                }
//                else if(p_description.equals("") && p_description.isEmpty()){
//                    productDescription.setError("product Description required");
//                    productDescription.requestFocus();
//                }else {
//                    DatabaseReference productRef = FirebaseDatabase.getInstance()
//                            .getReference("ProductList")
//                            .child(selectedCategory)
//                            .child(selectedSubCategory)
//                            .push(); // generates unique ID
//
//                    addProduct addProduct = new addProduct(p_name, p_price, p_description, selectedCategory, selectedSubCategory);
//
//                    productRef.setValue(addProduct).addOnSuccessListener(unused -> {
//                        Toast.makeText(addProductActivity.this, "Product Added", Toast.LENGTH_SHORT).show();
//                        finish(); // Optional: finish activity after adding
//                    }).addOnFailureListener(e -> {
//                        Toast.makeText(addProductActivity.this, "Failed to add product: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                    });
//                }
//            }
//        });
//    }
//
//    private void loadCategoryData() {
//        databaseReference.child("Categories").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                categoryData.clear();
//                for (DataSnapshot data : snapshot.getChildren()) {
//                    categoryData.add(data.getKey());
//                }
//                ArrayAdapter<String> adapter = new ArrayAdapter<>(addProductActivity.this, android.R.layout.simple_list_item_1, categoryData);
//                categoryListView.setAdapter(adapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(addProductActivity.this, "Failed to load categories", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//
//    private void loadSubCategoryData(String category) {
//        databaseReference.child("ProductList").child(category)
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        subCategoryData.clear();
//                        for (DataSnapshot data : snapshot.getChildren()) {
//                            subCategoryData.add(data.getKey()); // subcategory name
//                        }
//                        ArrayAdapter<String> adapter = new ArrayAdapter<>(addProductActivity.this, android.R.layout.simple_list_item_1, subCategoryData);
//                        subcategoryListView.setAdapter(adapter);
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        Toast.makeText(addProductActivity.this, "Failed to load subcategories", Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
//
//
//    private void loadProductImage() {
//
//            StorageReference storageReference;
//            FirebaseAuth mAuth = FirebaseAuth.getInstance();
////        FirebaseUser user = mAuth.getCurrentUser();
////        String Uid=user.getUid();
//
//            storageReference= FirebaseStorage.getInstance().getReference("ProductImages/");
//            try {
//                File localfile= File.createTempFile("tempfile",".jpg");
//                storageReference.getFile(localfile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                        DisplayMetrics dm=new DisplayMetrics();
//                        Bitmap bitmap= BitmapFactory.decodeFile(localfile.getAbsolutePath());
//                        productImage.setImageBitmap(bitmap);
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(addProductActivity.this, "failed to load profile image", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//    }
//}


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
    private ImageView productImage;
    private EditText productName, productPrice, productDescription;
    private Button addProductBtn;
    private ListView categoryListView, subcategoryListView;

    private ArrayList<String> categoryData = new ArrayList<>();
    private ArrayList<String> subCategoryData = new ArrayList<>();

    private String selectedCategory = null;
    private String selectedSubCategory = null;
    private String imageUrl = "https://via.placeholder.com/150"; // Placeholder or fetch this dynamically

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
                DatabaseReference productRef = FirebaseDatabase.getInstance()
                        .getReference("ProductList")
                        .child(selectedCategory)
                        .child(selectedSubCategory)
                        .push();

                addProduct product = new addProduct(
                        imageUrl, p_name, p_price, p_description, selectedCategory, selectedSubCategory
                );

                productRef.setValue(product)
                        .addOnSuccessListener(unused -> {
                            Toast.makeText(this, "Product Added", Toast.LENGTH_SHORT).show();
                            finish();
                        })
                        .addOnFailureListener(e ->
                                Toast.makeText(this, "Failed to add product: " + e.getMessage(), Toast.LENGTH_SHORT).show());
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
//        FirebaseUser user = mAuth.getCurrentUser();
//        String Uid=user.getUid();

            storageReference= FirebaseStorage.getInstance().getReference("ProductImages/");
            try {
                File localfile= File.createTempFile("tempfile",".jpg");
                storageReference.getFile(localfile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        DisplayMetrics dm=new DisplayMetrics();
                        Bitmap bitmap= BitmapFactory.decodeFile(localfile.getAbsolutePath());
                        productImage.setImageBitmap(bitmap);
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

