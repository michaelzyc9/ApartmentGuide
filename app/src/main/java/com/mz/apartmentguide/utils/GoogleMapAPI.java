package com.mz.apartmentguide.utils;

import com.mz.apartmentguide.responses.GoogleMapApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GoogleMapAPI {

    @GET("/maps/api/geocode/json?")
    Call<GoogleMapApiResponse> getGeocodingResponse(@Query("address") String address,
                                                    @Query("key") String key);

}
