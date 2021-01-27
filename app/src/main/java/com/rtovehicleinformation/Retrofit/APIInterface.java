package com.rtovehicleinformation.Retrofit;

import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIInterface {

    @POST("searchVehicleDetails")
    @FormUrlEncoded
    Call<JsonObject> GetVehicleDetail(@Field("registrationNo") String VehicleNo);
}
