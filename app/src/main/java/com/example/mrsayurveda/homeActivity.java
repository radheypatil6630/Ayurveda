package com.example.mrsayurveda;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class homeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        ImageView medicalImageView = findViewById(R.id.imageView1);
        ImageView foodImageView = findViewById(R.id.imageView2);
        ImageView cosmeticImageView = findViewById(R.id.imageView3);

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
    }

    private void navigateToProductList(String category) {
        Intent intent = new Intent(homeActivity.this, ProductListActivity.class);
        intent.putExtra("CATEGORY", category);
        startActivity(intent);
    }


    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.profile) {
            // Handle profile click
            return true;
        } else if (id == R.id.product_History) {
            // Handle product history click
            return true;
        } else if (id == R.id.home) {
            // Handle home click
            return true;
        } else if (id == R.id.share) {
            // Handle share click
            return true;
        } else if (id == R.id.logout) {
            // Handle logout click
            Toast.makeText(this, "Logout done", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
}



//
//import android.annotation.SuppressLint;
//import android.content.Intent;
//import android.os.Bundle;
//
//import androidx.annotation.NonNull;
//import androidx.fragment.app.Fragment;
//
//import android.view.LayoutInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.annotations.Nullable;
//
///**
// * A simple {@link Fragment} subclass.
// * Use the {@link homeFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
//public class homeFragment extends Fragment {
//
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    private ImageView imageView1,imageView2,imageView3,imageView4,imageView5,imageView6,imageView7;
//
//    private TextView textView1,textView2,textView3,textView4;
//
//    public homeFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment homeFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static homeFragment newInstance(String param1, String param2) {
//        homeFragment fragment = new homeFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
//
//    @SuppressLint("MissingInflatedId")
//    @Override
//
//        @Nullable
//            public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//            // Inflate the layout for this fragment
//            View view = inflater.inflate(R.layout.fragment_home, container, false);
//
//            ImageView medicalImageView = view.findViewById(R.id.imageView1);
//            ImageView foodImageView = view.findViewById(R.id.imageView2);
//            ImageView cosmeticImageView = view.findViewById(R.id.imageView3);
//
//            medicalImageView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    navigateToProductList("Medical");
//                }
//            });
//
//            foodImageView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    navigateToProductList("Food");
//                }
//            });
//
//            cosmeticImageView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    navigateToProductList("Cosmetic");
//                }
//            });
//
//            // Add other necessary functionalities and UI elements
//
//            return view;
//        }
//
//        private void navigateToProductList(String category) {
//            Intent intent = new Intent(getActivity(), ProductListActivity.class);
//            intent.putExtra("CATEGORY", category);
//            startActivity(intent);
//        }
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.profile) {
//            // Handle profile click
//            return true;
//        } else if (id == R.id.product_History) {
//            // Handle product history click
//            return true;
//        } else if (id == R.id.home) {
//            // Handle home click
//            return true;
//        } else if (id == R.id.share) {
//            // Handle share click
//            return true;
//        } else if (id == R.id.logout) {
//            // Handle logout click
//            Toast.makeText(getActivity(), "Logout done", Toast.LENGTH_SHORT).show();
//            return true;
//        }
//        return false;
//    }
//
//
//}