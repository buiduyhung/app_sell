package com.example.appsell.retrofit;


import com.example.appsell.model.ProductModel;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface ApiSell {
    @GET("getproduct.php")
    Observable<ProductModel> getProduct();
}
