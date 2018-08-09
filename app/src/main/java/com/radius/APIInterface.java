package com.radius;

import com.radius.Entities.Facilities;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Pavan on 09-08-2018.
 */

public interface APIInterface {
    @GET("ad-assignment/db")
    Call<Facilities> getFacilities();
}
