package com.example.mrsayurveda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerViewAccessibilityDelegate;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MedicineProductAdapter extends RecyclerView.Adapter<MedicineProductAdapter.MyViewHolder> {

    int []product_image;
    String []product_name;
    public MedicineProductAdapter(int[] product_image,String[] product_name) {
        this.product_image = product_image;
        this.product_name = product_name;
    }





    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_recycleview,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        try {
            // int resourceId = R.drawable.homeopathy; // Replace with your actual drawable name
            holder.imageView.setImageResource(product_image[position]);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
        holder.textView.setText(product_name[position]);
    }

    @Override
    public int getItemCount() {
        return product_image.length;
        //  return product_name.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;


        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            imageView=itemView.findViewById(R.id.image);
            textView=itemView.findViewById(R.id.name);
        }
    }
}

