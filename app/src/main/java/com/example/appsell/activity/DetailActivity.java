package com.example.appsell.activity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.appsell.R;
import com.example.appsell.model.Cart;
import com.example.appsell.model.Product;
import com.example.appsell.ultis.Ultis;
import com.nex3z.notificationbadge.NotificationBadge;

import java.text.DecimalFormat;

public class DetailActivity extends AppCompatActivity {
    TextView nameP, priceP, contentP;
    Button btnAdd;
    ImageView imgP;
    Spinner spinner;
    Toolbar toolbar;
    Product product;
    NotificationBadge badge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initView();
        ActionToolbar();
        initData();
        initControl();
    }

    private void initControl() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCart();
            }
        });
    }

    private void addCart() {
        if (Ultis.cartList.size() > 0){
            boolean flag = false;
            int qty = Integer.parseInt(spinner.getSelectedItem().toString());
            for (int i = 0; i<Ultis.cartList.size(); i++){
                if (Ultis.cartList.get(i).getIdProduct() == product.getId()){
                    Ultis.cartList.get(i).setQty(qty + Ultis.cartList.get(i).getQty());
                    long price = Long.parseLong(product.getPrice()) * Ultis.cartList.get(i).getQty();
                    Ultis.cartList.get(i).setPriceProduct(price);
                    flag = true;
                }
            }
            if (flag == false){
                long price = Long.parseLong(product.getPrice()) * qty;
                Cart cart = new Cart();
                cart.setPriceProduct(price);
                cart.setQty(qty);
                cart.setIdProduct(product.getId());
                cart.setNameProduct(product.getName());
                cart.setImgProduct(product.getImage());
                Ultis.cartList.add(cart);
            }

        }else {
            int qty = Integer.parseInt(spinner.getSelectedItem().toString());
            long price = Long.parseLong(product.getPrice()) * qty;
            Cart cart = new Cart();
            cart.setPriceProduct(price);
            cart.setQty(qty);
            cart.setIdProduct(product.getId());
            cart.setNameProduct(product.getName());
            cart.setImgProduct(product.getImage());
            Ultis.cartList.add(cart);
        }

        badge.setText(String.valueOf(Ultis.cartList.size()));
    }

    private void initData() {
        product = (Product) getIntent().getSerializableExtra("detail");
        nameP.setText(product.getName());
        contentP.setText(product.getContent());
        Glide.with(getApplicationContext()).load(product.getImage()).into(imgP);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        priceP.setText("Giá: "+ decimalFormat.format(Double.parseDouble(product.getPrice())) +" VND");

        Integer[] so = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> adapterspin = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, so);
        spinner.setAdapter(adapterspin);
    }

    private void initView() {
        nameP = findViewById(R.id.txt_nameDetail);
        priceP = findViewById(R.id.txt_priceDetail);
        contentP = findViewById(R.id.txt_contentDetail);
        btnAdd = findViewById(R.id.btn_addCart);
        spinner = findViewById(R.id.spinner);
        imgP = findViewById(R.id.img_detail);
        toolbar = findViewById(R.id.toolbar);

        badge = findViewById(R.id.menu_sl);
        if (Ultis.cartList != null){
            badge.setText(String.valueOf(Ultis.cartList.size()));
        }
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


}