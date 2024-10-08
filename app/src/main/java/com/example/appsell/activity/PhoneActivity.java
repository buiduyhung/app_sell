package com.example.appsell.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
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
    LinearLayoutManager linearLayoutManager;
    Handler handler = new Handler();
    boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        apiSell = RetrofitClient.getInstance(Ultis.BASE_URL).create(ApiSell.class);
        category = getIntent().getIntExtra("category", 1);

        Anhxa();
        ActionToolbar();
        getData(page);
        addEventLoad();
    }

    private void addEventLoad() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isLoading == false){
                    if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == listProduct.size()-1){
                        isLoading = true;
                        loadMore();
                    }
                }
            }
        });
    }

    private void loadMore() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                // add null
                listProduct.add(null);
                phoneAdapter.notifyItemInserted(listProduct.size()-1);
            }
        });
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // remover null
                listProduct.remove(listProduct.size()-1);
                phoneAdapter.notifyItemRemoved(listProduct.size());
                page = page+1;
                getData(page);
                phoneAdapter.notifyDataSetChanged();
                isLoading = false;

            }
        }, 2000);
    }

    private void getData(int page) {
        compositeDisposable.add(apiSell.getProductDetail(page,category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    productModel -> {
                        if (productModel.isSuccess()){
                            if (phoneAdapter == null){
                                listProduct = productModel.getResult();
                                phoneAdapter = new PhoneAdapter(getApplicationContext(), listProduct);
                                recyclerView.setAdapter(phoneAdapter);
                            }else {
                                int vitri = listProduct.size()-1;
                                int soluongadd = productModel.getResult().size();
                                for (int i=0; i<soluongadd; i++){
                                    listProduct.add(productModel.getResult().get(i));
                                }
                                phoneAdapter.notifyItemRangeInserted(vitri,soluongadd);
                            }
                        }else {
                            Toast.makeText(getApplicationContext(), "Loading data finish", Toast.LENGTH_SHORT).show();
                            isLoading = true;
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

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        listProduct = new ArrayList<>();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}