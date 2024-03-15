package com.example.mrsayurveda;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.WindowDecorActionBar;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductViewHolder extends RecyclerView.Adapter<ProductViewHolder.ViewHolder>{
    private List<ProductList> productList;
    private List<ProductList> originalList;
//    private String deliveryDate;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(ProductList product, int position);
    }

    public ProductViewHolder(List<ProductList> productList, OnItemClickListener onItemClickListener) {
        this.productList = productList;
        this.originalList = new ArrayList<>(productList); // Initialize originalProductList
      //  this.deliveryDate = deliveryDate; // Set the delivery date
        this.onItemClickListener = onItemClickListener;
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

//@NonNull
//@Override
//public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//    // Check the context to determine which layout to inflate
//    Context context = parent.getContext();
//    LayoutInflater inflater = LayoutInflater.from(context);
//    View view;
//
//    // Inflate the appropriate layout based on the context
//    if (context instanceof OrderHistoryActivity) {
//        view = inflater.inflate(R.layout.orderrecycleview, parent, false);
//    } else {
//        view = inflater.inflate(R.layout.activity_recycleview, parent, false);
//    }
//    return new ViewHolder(view);
//}


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductList product = productList.get(position);

      if (product != null) { // , product.getDeliveryDate()
            holder.setDetails(product.getProductName(), product.getImageUrl(), "₹" + product.getPrice());
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
        private TextView productName, productPrice, delivery;//, deliveryDateTextView;
       // private WindowDecorActionBar.TabImpl deliveryDateTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.image);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
            delivery = itemView.findViewById(R.id.delivery);
       //     deliveryDateTextView=itemView.findViewById(R.id.deliveryDateTextView);
        }

//        public void setDetails(String productName, String imageUrl, String price) {
//            // Set the data to the views
//            Picasso.get().load(imageUrl).into(productImage);
//            this.productName.setText(productName);
//            this.productPrice.setText(price);
//            delivery.setText("Free delivery");
//        }

        // Inside ProductViewHolder.java
        // Inside ProductViewHolder.java
        public void setDetails(String ProductName, String imageUrl, String price) {  //,String deliveryDate
            // Check if productImageView is null
            if (productImage != null) {
                // Load image into ImageView
                Picasso.get().load(imageUrl).into(productImage);
            }

            // Set product name and price
            if (productName != null && productPrice != null) {
             productName.setText(ProductName);
                productPrice.setText(price);
             //   this.deliveryDateTextView.setText(deliveryDate);
            }
        }



    }

    // Filter method to filter the product list
    public void filter(String query) {
        productList.clear();
        if (query.isEmpty()) {
            // If the query is empty, display original list
            productList.addAll(originalList);
        } else {
            // Iterate through the original list and add matching items to the filtered list
            for (ProductList product : originalList) {
                if (product.getProductName().toLowerCase().contains(query.toLowerCase()) ||
                        product.getProductType().toLowerCase().contains(query.toLowerCase())) {
                    productList.add(product);
                }
            }
        }
        notifyDataSetChanged(); // Notify RecyclerView about the changes
    }
    // Method to set original product list
    public void setOriginalList(List<ProductList> originalList) {
        this.originalList = new ArrayList<>(originalList);
        this.productList = new ArrayList<>(originalList);
    }

    // Method to filter list based on search query
    public void filterList(List<ProductList> filteredList) {
        productList = filteredList;
        notifyDataSetChanged();
    }

    // Method to reset list to original
    public void resetList() {
        productList = new ArrayList<>(originalList);
        notifyDataSetChanged();
    }
}
