package com.example.mrsayurveda;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class homeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
//    private HomePageBinding homePageBinding;
private EditText email, password;

 private BottomNavigationView bottomNavigationView;

    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private List<ProductList> productList;
    private ProductViewHolder adapter;
    private boolean isAdmin;

    private EditText searchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

         isAdmin = getIntent().getBooleanExtra("isAdmin", false);
         Log.d("good home acitivity",String.valueOf(isAdmin));





        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
      //  bottomNavigationView.setOnNavigationItemSelectedListener(this::handleBottomNavigation);
    bottomNavigationView.setOnNavigationItemSelectedListener(homeActivity.this);

        // Set the selected item to be "Home"
        bottomNavigationView.setSelectedItemId(R.id.home);
        if (isAdmin) {
            menu.findItem(R.id.addproduct).setVisible(true);
        }else {
            menu.findItem(R.id.addproduct).setVisible(false);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true); // Show hamburger icon
            actionBar.setHomeAsUpIndicator(R.drawable.baseline_horizontal_menu);
        }
        ImageView medicalImageView = findViewById(R.id.imageView1);
        ImageView foodImageView = findViewById(R.id.imageView2);
        ImageView cosmeticImageView = findViewById(R.id.imageView3);
        ImageView imageView4 = findViewById(R.id.imageView4);
        ImageView imageView5 = findViewById(R.id.imageView5);
        ImageView imageView6 = findViewById(R.id.imageView6);
        ImageView imageView7 = findViewById(R.id.imageView7);


        medicalImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToProductList("Medical");
            }
        });

        foodImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToProductList("Food");
            }
        });

        cosmeticImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToProductList("Cosmetic");
            }
        });

        // Set click listeners for the new ImageViews (imageView4, imageView5, imageView6)
        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://m.facebook.com/arogyamayurskin"));
                startActivity(intent);
            }
        });

        imageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.webmd.com/balance/what-is-homeopathy"));
                startActivity(intent);
            }
        });

        imageView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://vedayurved.org/"));
                startActivity(intent);
            }
        });
        imageView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://vikaspedia.in/health/ayush/ayurveda-1/ayurvedic-home-remedies"));
                startActivity(intent);
            }
        });


    }

    private void navigateToProductList(String category) {
        Intent intent = new Intent(homeActivity.this, ProductListActivity.class);
        intent.putExtra("CATEGORY", category);
        startActivity(intent);
    }

protected void onResume() {
    super.onResume();

    // Manually set the selected item to be "Home" when returning to the home activity
    bottomNavigationView.setSelectedItemId(R.id.home);
}




//bottom menu bar

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.profile) {
            // Handle profile click
            startActivity(new Intent(homeActivity.this, MyAccount.class));

            return true;
        }  else if (id == R.id.product_History) {
            // Handle product history click
            startActivity(new Intent(homeActivity.this, ProductCartActivity.class));
            return true;
        } else if (id== R.id.addproduct) {
                startActivity(new Intent(homeActivity.this,addProductActivity.class));
                return true;

        }  else if (id == R.id.home) {
            // No need to navigate to homeActivity as we are already in it
            return true;
        }
        else if (id == R.id.logout) {
            // Handle logout click
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(homeActivity.this, MainActivity.class));
            Toast.makeText(homeActivity.this, "User logged out", Toast.LENGTH_SHORT).show();
            finish();
            return false;
        }
        return false;
    }



    //3 line menu bar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            // Handle the 3-line icon click here and show horizontal menu as popup
            showPopupMenu();
            return true;
        }

        if (id == R.id.profile) {
            // Handle profile click
            startActivity(new Intent(homeActivity.this, MyAccount.class));

            return true;
        } else if (id == R.id.order) {
            // Handle product history click
            startActivity(new Intent(homeActivity.this, OrderHistoryActivity.class));
            return true;
        }
        else if (id == R.id.aboutus) {
            // Handle product history click

            return true;
        } else if (id == R.id.like) {
            // Handle product history click

            return true;
        }else if (id == R.id.logout) {
            // Handle logout click
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(homeActivity.this, MainActivity.class));
            Toast.makeText(homeActivity.this, "User logged out", Toast.LENGTH_SHORT).show();
            finish();
            return false;
        }

        return super.onOptionsItemSelected(item);
    }
    private void showPopupMenu() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        PopupMenu popupMenu = new PopupMenu(this, toolbar, Gravity.START);
        popupMenu.getMenuInflater().inflate(R.menu.horizontal_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.settings) {
                    Toast.makeText(homeActivity.this, "Settings clicked", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (id == R.id.order) {
                    startActivity(new Intent(homeActivity.this, OrderHistoryActivity.class));
                    return true;
                } else if (id == R.id.logout) {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(homeActivity.this, MainActivity.class));
                    Toast.makeText(homeActivity.this, "User logged out", Toast.LENGTH_SHORT).show();
                    finish();
                    return true;
                }

                return false;
            }
        });

        popupMenu.show();
    }


}

