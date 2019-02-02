package com.example.apartmentguide.utils;

import com.example.apartmentguide.models.GoogleMapApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GoogleMapAPIs {

    @GET("/maps/api/geocode/json?")
    Call<GoogleMapApiResponse> getGeocodingResponse(@Query("address") String address,
                                                    @Query("key") String key);

}
