package com.mz.apartmentguide.utils;

import com.mz.apartmentguide.responses.AgServiceResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AgServiceAPI {

    @GET("/ag-api/apartments?size=100")
    Call<AgServiceResponse> getAll();

}
