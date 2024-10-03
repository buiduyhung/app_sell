package com.example.appsell.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appsell.R;
import com.example.appsell.model.ProductNew;

import java.util.List;

public class ProductNewAdapter extends RecyclerView.Adapter<ProductNewAdapter.MyViewHolder> {
    Context context;
    List<ProductNew> array;

    public ProductNewAdapter(Context context, List<ProductNew> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_new, parent, false);

        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ProductNew productNew = array.get(position);
        holder.txt_name.setText(productNew.getName());
        holder.txt_price.setText(productNew.getPrice());
        Glide.with(context).load(productNew.getImage()).into(holder.img_image);
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_price, txt_name;
        ImageView img_image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_name = itemView.findViewById(R.id.itemsp_name);
            txt_price = itemView.findViewById(R.id.itemsp_price);
            img_image = itemView.findViewById(R.id.itemsp_image);
        }
    }

}
