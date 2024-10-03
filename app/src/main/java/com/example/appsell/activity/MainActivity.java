package com.example.appsell.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.appsell.R;
import com.example.appsell.adapter.CategoryProductAdapter;
import com.example.appsell.model.CategoryProduct;
import com.example.appsell.retrofit.ApiSell;
import com.example.appsell.retrofit.RetrofitClient;
import com.example.appsell.ultis.Ultis;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewManHinhChinh;
    NavigationView navigationView;
    ListView listViewManHinhChinh;
    DrawerLayout drawerLayout;
    CategoryProductAdapter categoryProductAdapter;
    List<CategoryProduct> listCategoryProduct;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiSell apiSell;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiSell = RetrofitClient.getInstance(Ultis.BASE_URL).create(ApiSell.class);

        Anhxa();
        ActionBar();
        ActionViewFlipper();
        if (isConnected(this)){
            ActionViewFlipper();
            getCategoryProduct();
        }else {
            Toast.makeText(getApplicationContext(), "Không có internet, vui lòng kết nối !", Toast.LENGTH_SHORT).show();
        }
    }

    private void getCategoryProduct() {
        compositeDisposable.add(apiSell.getCategoryProduct()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        categoryProductModel -> {
                            if (categoryProductModel.isSuccess()) {
                                // Kiểm tra xem danh sách result có dữ liệu không
                                if (categoryProductModel.getResult() != null && !categoryProductModel.getResult().isEmpty()) {
                                    // Hiển thị tên sản phẩm đầu tiên
                                    Toast.makeText(getApplicationContext(), categoryProductModel.getResult().get(0).getNameProduct(), Toast.LENGTH_SHORT).show();
                                } else {
                                    // Xử lý khi không có sản phẩm
                                    Toast.makeText(getApplicationContext(), "Không có sản phẩm", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                // Xử lý khi yêu cầu thất bại
                                Toast.makeText(getApplicationContext(), "Yêu cầu thất bại", Toast.LENGTH_SHORT).show();
                            }
                        },
                        throwable -> {
                            // Xử lý lỗi từ API
                            Toast.makeText(getApplicationContext(), "Lỗi kết nối: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                )
        );
    }

    private void ActionViewFlipper() {
        List<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-Le-hoi-phu-kien-800-300.png");
        mangquangcao.add("https://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-HC-Tra-Gop-800-300.png");
        mangquangcao.add("https://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-big-ky-nguyen-800-300.jpg");
        for (int i = 0; i<mangquangcao.size(); i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Glide.with(getApplicationContext()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(slide_in);
        viewFlipper.setOutAnimation(slide_out);
    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void Anhxa() {
        toolbar = findViewById(R.id.toolbarmanhinhchinh);
        viewFlipper = findViewById(R.id.viewflipper);
        recyclerViewManHinhChinh = findViewById(R.id.recycleview);
        listViewManHinhChinh = findViewById(R.id.listviewmahinhchinh);
        navigationView = findViewById(R.id.navigationview);
        drawerLayout = findViewById(R.id.drawerlayout);

        // create list product
        listCategoryProduct = new ArrayList<>();



    }

    private boolean isConnected(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((wifi != null && wifi.isConnected()) || (mobile != null && mobile.isConnected())){
            return true;
        }else {
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}