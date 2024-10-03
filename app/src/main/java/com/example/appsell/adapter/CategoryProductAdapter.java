package com.example.appsell.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.appsell.R;
import com.example.appsell.model.CategoryProduct;

import java.util.List;

public class CategoryProductAdapter extends BaseAdapter {
    List<CategoryProduct> array;
    Context context;

    public CategoryProductAdapter(Context context, List<CategoryProduct> array) {
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
        TextView nameCategoryProduct;
        ImageView imgCategoryProduct;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null){
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.item_category_product, null);
            viewHolder.nameCategoryProduct = view.findViewById(R.id.item_nameCategoryProduct);
            viewHolder.imgCategoryProduct = view.findViewById(R.id.item_imgCategoryProduct);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
            viewHolder.nameCategoryProduct.setText(array.get(i).getNameProduct());
            Glide.with(context).load(array.get(i).getImageProduct()).into(viewHolder.imgCategoryProduct);
        }

//        viewHolder.nameCategoryProduct.setText(array.get(i).getNameProduct());
//        Glide.with(context).load(array.get(i).getImageProduct()).into(viewHolder.imgCategoryProduct);

        return view;
    }
}
