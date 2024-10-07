package com.example.appsell.retrofit;


import com.example.appsell.model.CategoryProductModel;
import com.example.appsell.model.ProductModel;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiSell {
    @GET("get_category_product.php")
    Observable<CategoryProductModel> getCategoryProduct();

    @GET("get_products.php")
    Observable<ProductModel> getProduct();

    @POST("get_product_detail.php")
    @FormUrlEncoded
    Observable<ProductModel> getProductDetail(
        @Field("page") int page,
        @Field("category") int category
    );
}
