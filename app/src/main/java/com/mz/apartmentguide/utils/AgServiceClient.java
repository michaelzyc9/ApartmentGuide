package com.mz.apartmentguide.utils;

import android.content.Context;

import com.mz.apartmentguide.R;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AgServiceClient {

    private static Retrofit mRetrofit;

    public static Retrofit getServiceClient(Context context){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(context.getString(R.string.ag_service_base_url))
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return mRetrofit;
    }
}
