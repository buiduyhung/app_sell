package com.example.appsell.retrofit;


import com.example.appsell.model.CategoryProductModel;
import com.example.appsell.model.ProductModel;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface ApiSell {
    @GET("get_category_product.php")
    Observable<CategoryProductModel> getCategoryProduct();

    @GET("get_products.php")
    Observable<ProductModel> getProduct();
}
