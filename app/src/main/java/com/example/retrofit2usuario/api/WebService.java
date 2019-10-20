package com.example.retrofit2usuario.api;

import android.content.Context;
import android.content.Intent;

import com.example.retrofit2usuario.sharedPreferences.SharedPrefManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebService {
    private  static  final String BASE_URL="http://181.64.218.36:8040/";
    private  static  WebService instance;
    private Retrofit retrofit;
    private HttpLoggingInterceptor loggingInterceptor;
    private OkHttpClient.Builder okhttpClientBuilder;

    private  WebService(){
        loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        okhttpClientBuilder = new OkHttpClient.Builder().addInterceptor(loggingInterceptor);
        retrofit= new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okhttpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())

                .build();
    }
    public static  synchronized WebService getInstance(){
        if(instance == null){
            instance = new WebService();
        }
        return  instance;
    }

    public <D> D createService(Class<D> serviceClass){
        return  retrofit.create(serviceClass);
    }
}
