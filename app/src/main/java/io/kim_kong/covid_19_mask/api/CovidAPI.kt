package io.kim_kong.covid_19_mask.api

import com.google.gson.annotations.Expose
import retrofit2.Call
import retrofit2.http.*

interface CovidAPI {
    @GET("json?")
    fun getLocation(@Query("lat") lat: Double, @Query("lng") lng: Double, @Query("m") m: Int): Call<CovidResponse>

}