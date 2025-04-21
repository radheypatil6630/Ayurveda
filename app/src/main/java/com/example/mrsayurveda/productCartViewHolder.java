package com.example.mrsayurveda;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class productCartViewHolder extends RecyclerView.Adapter<productCartViewHolder.ViewHolder> {
    private List<cartProduct> cartProductList;

    public productCartViewHolder(List<cartProduct> cartProductList) {
        this.cartProductList = cartProductList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_cart_recycleview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        cartProduct cartProduct = cartProductList.get(position);
        holder.setDetails(cartProduct.getProductName(), cartProduct.getImageUrl(), "₹" + cartProduct.getProductPrice(), cartProduct.getDeliveryDate() ,cartProduct.getQuantityNum(),cartProduct.isCheckbox_status());

//        holder.setDetails(cartProduct.getProductName(), product.getImageUrl(), product.getPrice(), product.getDeliveryDate());

        // Set default quantity
        holder.quantityNum.setText(String.valueOf(cartProduct.getQuantityNum()));

//        holder.status.setChecked(cartProduct.isCheckbox_status());
//
//        boolean checked = holder.status.isChecked();

        holder.status.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Get the cartProduct at this position
            cartProduct currentProduct = cartProductList.get(holder.getAdapterPosition());

            // Update the checkbox status in the model
            currentProduct.setCheckbox_status(isChecked);

            // Update the value in Firebase
            String orderId = currentProduct.getOrderId();
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid(); // make sure user is not null

            FirebaseDatabase.getInstance().getReference("cartproduct")
                    .child(userId)
                    .child(orderId)
                    .child("checkbox_status")
                    .setValue(isChecked);
        });


        // Counter logic
        holder.increment.setOnClickListener(v -> {
            int quantity = Integer.parseInt(holder.quantityNum.getText().toString());
            quantity++;
            holder.quantityNum.setText(String.valueOf(quantity));

            updateQuantityInFirebase(cartProduct.getOrderId(), quantity);
        });

        holder.decrement.setOnClickListener(v -> {
            int quantity = Integer.parseInt(holder.quantityNum.getText().toString());
            if (quantity > 1) {
                quantity--;
                holder.quantityNum.setText(String.valueOf(quantity));

                updateQuantityInFirebase(cartProduct.getOrderId(), quantity);
            }
        });
        // Set click listener for cancel button
        holder.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the adapter position from the holder
                int adapterPosition = holder.getAdapterPosition();
                // Call the onCancelClicked method of the activity
                if (v.getContext() instanceof ProductCartActivity) {
                    ((ProductCartActivity) v.getContext()).onCancelClicked(cartProduct, adapterPosition);
                }
            }
        });
    }

    private void updateQuantityInFirebase(String orderId, int quantity) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            DatabaseReference cartRef = FirebaseDatabase.getInstance()
                    .getReference("cartproduct")
                    .child(userId)
                    .child(orderId)
                    .child("quantityNum");
            cartRef.setValue(quantity);
        }
    }

    @Override
    public int getItemCount() {
        return cartProductList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private TextView productName, productPrice, deliveryDateTextView,delivery;
        private TextView increment , decrement , quantityNum;
        private ImageButton cancelButton;
        private CheckBox status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.image);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
            deliveryDateTextView = itemView.findViewById(R.id.deliveryDateTextView);
            cancelButton = itemView.findViewById(R.id.cancelButton);
            delivery = itemView.findViewById(R.id.delivery);
            decrement = itemView.findViewById(R.id.decrement);
            increment = itemView.findViewById(R.id.increment);
            quantityNum = itemView.findViewById(R.id.quantityNum);
            status = itemView.findViewById(R.id.checkbox_status);
        }

        public void setDetails(String productName, String imageUrl, String productPrice, String deliveryDate,int quantityNum,boolean status) {
            Picasso.get().load(imageUrl).into(productImage);
            this.productName.setText(productName);
            this.productPrice.setText(productPrice);
            this.deliveryDateTextView.setText( deliveryDate);
            this.delivery.setText("Free delivery");
            this.quantityNum.setText(Integer.toString(quantityNum));
            this.status.setChecked(status);



        }
    }
}
