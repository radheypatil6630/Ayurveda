package com.example.mrsayurveda;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.annotations.Nullable;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    //FirebaseAuth mAuth;
    private Button save,logout;
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView profileImageView;
    private EditText profileNameEditText;
    private EditText email,password;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);



//            View view=getLayoutInflater().inflate(R.layout.fragment_profile,container,false);



        }
    }
    private void showToastMessage()
    {
        Toast.makeText(requireContext(), "logout successfully", Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_profile, container, false);

      //  FirebaseApp.initializeApp(requireContext());
      //  mAuth = FirebaseAuth.getInstance();

        profileImageView = view.findViewById(R.id.profile_img);
        profileNameEditText = view.findViewById(R.id.profile_name);

        // Set a click listener on the ImageView to open the image gallery
        profileImageView.setOnClickListener(v -> openGallery());



        save = (Button) view.findViewById(R.id.saveDetails);
        logout = (Button) view.findViewById(R.id.logout);

        email=view.findViewById(R.id.Email);
        password=view.findViewById(R.id.Password);


      logout.setOnClickListener(v -> {
          showToastMessage();

          if (getActivity() instanceof OnClearEditTextListener) {
              OnClearEditTextListener listener = (OnClearEditTextListener) getActivity();
              listener.onClearEditText(email.getText().toString(), password.getText().toString());
          }



//          email.setText("");
//          password.setText("");
          FirebaseAuth.getInstance().signOut();

          requireActivity().finish();

      });
        return view;
    }

    public interface OnClearEditTextListener {
        void onClearEditText(String email, String password);
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Get the selected image URI
            Uri imageUri = data.getData();

            // Set the selected image to your ImageView or perform any other actions
            profileImageView.setImageURI(imageUri);
        }
    }


}