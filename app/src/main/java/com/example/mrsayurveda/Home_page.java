package com.example.mrsayurveda;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home_page extends AppCompatActivity
        implements BottomNavigationView
        .OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView
                .setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.profile);
    }
    homeFragment homeFragment=new homeFragment();
    ProfileFragment profileFragment = new ProfileFragment();
    productCartFragment producthistoryFragment=new productCartFragment();

//    shareFragment ShareFragment=new shareFragment();

    @Override
    public boolean
    onNavigationItemSelected(@NonNull MenuItem item)
    {

        int id=item.getItemId();
        if (id==R.id.home)
        {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, homeFragment)
                    .commit();
        }
        else if(id==R.id.product_History) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, producthistoryFragment)
                    .commit();
            return true;
        } else if ( id== R.id.profile) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, profileFragment)
                    .commit();
            return true;
        }



        else if(id== R.id.logout)
        {
            Toast.makeText(this, "Logout successfully", Toast.LENGTH_SHORT).show();
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.flFragment, Fragment)
//                    .commit();
//            return true;
        }
//        else if(id==R.id.share)
//        {
//            Toast.makeText(this, "Sharing done", Toast.LENGTH_SHORT).show();
//        }
        return false;
    }
}
