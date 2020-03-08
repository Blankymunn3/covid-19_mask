package io.kim_kong.covid_19_mask.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.kim_kong.covid_19_mask.model.Store

class CovidResponse {
    @SerializedName("count")
    @Expose
    var count: Int? = 0

    @SerializedName("stores")
    @Expose
    var stores: List<Store>? = null
}