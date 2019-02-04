package com.michaelzhang.apartmentguide.utils;

import com.michaelzhang.apartmentguide.models.ApartmentBuilding;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetApartmentsInterface {
    @GET("/michaelzyc9/ApartmentGuide/master/apartments.json")
    Call<List<ApartmentBuilding>> getApartments();
}
