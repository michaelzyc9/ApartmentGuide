package com.example.apartmentguide.utils;

import android.content.Context;
import android.util.Log;

import com.example.apartmentguide.R;
import com.example.apartmentguide.models.GoogleMapApiResponse;
import com.example.apartmentguide.models.Result;
import com.google.android.gms.maps.GoogleMap;

import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GoogleMapApiClient {

    private static Retrofit mRetrofit;

    private static Context mContext;

    public static  Retrofit getClient(Context context) {

        mContext = context;

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(mContext.getString(R.string.google_map_base_url))
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return mRetrofit;
    }
}
