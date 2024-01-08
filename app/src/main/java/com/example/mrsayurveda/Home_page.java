package com.example.mrsayurveda;


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
    //Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        bottomNavigationView
                = findViewById(R.id.bottomNavigationView);

        bottomNavigationView
                .setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.profile);
//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//Intent intent=new Intent(MainActivity.this, MainActivity2.class);
//startActivity(intent);
//            }
//        });
    }
    homeFragment homeFragment=new homeFragment();
    ProfileFragment profileFragment = new ProfileFragment();
    ProductCartFragment productCartFragment=new ProductCartFragment();

    ShareFragment shareFragment=new ShareFragment();

    @Override
    public boolean
    onNavigationItemSelected(@NonNull MenuItem item)
    {

        int id=item.getItemId();
        if(id==R.id.profile) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, profileFragment)
                    .commit();
            return true;
        }
        else if ( id== R.id.product_History) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, productCartFragment)
                    .commit();
            return true;
        }
        else if(id== R.id.home)
        {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, shareFragment)
                    .commit();
            return true;
        }
        else if(id== R.id.share)
        {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, shareFragment)
                    .commit();
            return true;
        }
        else if(id== R.id.logout)
        {
            Toast.makeText(this, "Logout done", Toast.LENGTH_SHORT).show();
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.flFragment, shareFragment)
//                    .commit();
//            return true;
        }
        return false;

    }


}
