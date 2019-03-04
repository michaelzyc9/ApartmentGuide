package com.michaelzhang.apartmentguide.utils;

import com.michaelzhang.apartmentguide.responses.AgServiceResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AgServiceAPI {

    @GET("/ag-api/apartments")
    Call<AgServiceResponse> getAll();

}
