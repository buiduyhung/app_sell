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
import com.example.appsell.model.Product;

import java.text.DecimalFormat;
import java.util.List;

public class PhoneAdapter extends RecyclerView.Adapter<PhoneAdapter.MyViewHolder> {
    Context context;
    List<Product> array;

    public PhoneAdapter(Context context, List<Product> array) {
        this.array = array;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_phone, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneAdapter.MyViewHolder holder, int position) {
        Product product = array.get(position);
        holder.name_product.setText(product.getName());

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.price_product.setText("Giá: "+ decimalFormat.format(Double.parseDouble(product.getPrice())) +" VND");

        holder.content_product.setText(product.getContent());
        Glide.with(context).load(product.getImage()).into(holder.img_product);
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name_product, price_product, content_product;
        ImageView img_product;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name_product = itemView.findViewById(R.id.itemdt_name);
            price_product = itemView.findViewById(R.id.itemdt_price);
            content_product = itemView.findViewById(R.id.itemdt_content);
            img_product = itemView.findViewById(R.id.itemdt_image);
        }
    }


}
