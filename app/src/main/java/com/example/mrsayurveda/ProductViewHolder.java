package com.example.mrsayurveda;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;

public class ProductViewHolder extends RecyclerView.ViewHolder {

    private ImageView productImage;
    private TextView productName, productPrice, delivery;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);

        productImage = itemView.findViewById(R.id.image);
        productName = itemView.findViewById(R.id.product_name);
        productPrice = itemView.findViewById(R.id.product_price);
        delivery = itemView.findViewById(R.id.delivery);
    }

    public void setDetails(String productName, String imageUrl, String price) {
        // Set the data to the views
        Picasso.get().load(imageUrl).into(productImage);
        this.productName.setText(productName);
        this.productPrice.setText(price);
        delivery.setText("Free delivery");
    }


}
