package com.example.mrsayurveda;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class OrderViewHolder extends RecyclerView.Adapter<OrderViewHolder.ViewHolder> {
    private List<OrderedProduct> orderedProductList;

    public OrderViewHolder(List<OrderedProduct> orderedProductList) {
        this.orderedProductList = orderedProductList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderrecycleview, parent, false);
        return new ViewHolder(view);
    }

@Override
public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    OrderedProduct orderedProduct = orderedProductList.get(position);
    holder.setDetails(orderedProduct.getProductName(), orderedProduct.getImageUrl(), "₹" + orderedProduct.getProductPrice(), orderedProduct.getDeliveryDate());

    // Set click listener for cancel button
//    holder.cancelButton.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            // Get the adapter position from the holder
//            int adapterPosition = holder.getAdapterPosition();
//            // Call the onCancelClicked method of the activity
//            if (v.getContext() instanceof OrderHistoryActivity) {
//                ((OrderHistoryActivity) v.getContext()).onCancelClicked(orderedProduct, adapterPosition);
//            }
//        }
//    });
}

    @Override
    public int getItemCount() {
        return orderedProductList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private TextView productName, productPrice, deliveryDateTextView,delivery;
//        private ImageButton cancelButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.image);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
            deliveryDateTextView = itemView.findViewById(R.id.deliveryDateTextView);
//            cancelButton = itemView.findViewById(R.id.cancelButton);
            delivery = itemView.findViewById(R.id.delivery);
        }

        public void setDetails(String productName, String imageUrl, String productPrice, String deliveryDate) {
            Picasso.get().load(imageUrl).into(productImage);
            this.productName.setText(productName);
            this.productPrice.setText(productPrice);
            this.deliveryDateTextView.setText( deliveryDate);
            this.delivery.setText("Free delivery");
        }
    }
}
