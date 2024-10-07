package com.example.appsell.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appsell.R;
import com.example.appsell.adapter.PhoneAdapter;
import com.example.appsell.model.Product;
import com.example.appsell.retrofit.ApiSell;
import com.example.appsell.retrofit.RetrofitClient;
import com.example.appsell.ultis.Ultis;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;

public class PhoneActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    ApiSell apiSell;
    CompositeDisposable compositeDisposable =new CompositeDisposable();
    int page = 1;
    int category;
    PhoneAdapter phoneAdapter;
    List<Product> listProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        apiSell = RetrofitClient.getInstance(Ultis.BASE_URL).create(ApiSell.class);
        category = getIntent().getIntExtra("category", 1);

        Anhxa();
        ActionToolbar();
        getData();
    }

    private void getData() {
        compositeDisposable.add(apiSell.getProductDetail(page,category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    productModel -> {
                        if (productModel.isSuccess()){
                            listProduct = productModel.getResult();
                            phoneAdapter = new PhoneAdapter(getApplicationContext(), listProduct);
                            recyclerView.setAdapter(phoneAdapter);
                        }
                    },
                    throwable -> {
                        Toast.makeText(getApplicationContext(), "Lỗi kết nối server", Toast.LENGTH_SHORT).show();
                    }
                )
        );
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

    private void Anhxa() {
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recycleview_dt);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        listProduct = new ArrayList<>();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}