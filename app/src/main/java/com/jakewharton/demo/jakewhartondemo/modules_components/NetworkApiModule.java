package com.jakewharton.demo.jakewhartondemo.modules_components;


import com.jakewharton.demo.jakewhartondemo.interfaces.NetworkApi;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkApiModule {

    final String BASE_URL="https://api.github.com/";
    @Provides
    public OkHttpClient provideOkHttpClient()
    {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        return new OkHttpClient
                .Builder()
                .addInterceptor(interceptor)
                .build();
    }

    @Provides
    public Retrofit provideRetrofit(OkHttpClient okHttpClient)
    {
        return new Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    public NetworkApi provideNetworkApi(Retrofit retrofit)
    {
        return retrofit.create(NetworkApi.class);
    }
}
