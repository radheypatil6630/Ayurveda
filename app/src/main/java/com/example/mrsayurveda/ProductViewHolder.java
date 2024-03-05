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

public class ProductViewHolder extends RecyclerView.Adapter<ProductViewHolder.ViewHolder>{
    private List<ProductList> productList;

    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onItemClick(ProductList product, int position);
    }

    public ProductViewHolder(List<ProductList> productList, OnItemClickListener onItemClickListener) {

        this.productList = productList;
        //this.onItemClickListener = this.onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_recycleview, parent, false);
      return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductList product = productList.get(position);
        if (product != null) {
            holder.setDetails(product.getProductName(), product.getImageUrl(), product.getPrice());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int adapterPosition = holder.getAdapterPosition();
                    if (onItemClickListener != null && adapterPosition != RecyclerView.NO_POSITION) {
                        onItemClickListener.onItemClick(productList.get(adapterPosition), adapterPosition);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private TextView productName, productPrice, delivery;

        public ViewHolder(@NonNull View itemView) {
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


}
