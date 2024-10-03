package com.example.appsell.adapter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.appsell.R;
import com.example.appsell.model.Product;

import java.util.List;

public class ProductAdapter extends BaseAdapter {
    List<Product> array;
    Context context;

    public ProductAdapter(Context context, List<Product> array) {
        this.array = array;
        this.context = context;
    }

    @Override
    public int getCount() {
        return array.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public class ViewHolder{
        TextView nameProduct;
        ImageView imageProduct;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.item_product, null);
            viewHolder.nameProduct = view.findViewById(R.id.item_nameProduct);
            viewHolder.imageProduct = view.findViewById(R.id.item_imageProduct);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.nameProduct.setText(array.get(i).getNameProduct());
        Glide.with(context).load(array.get(i).getImageProduct()).into(viewHolder.imageProduct);

        return view;
    }




}
