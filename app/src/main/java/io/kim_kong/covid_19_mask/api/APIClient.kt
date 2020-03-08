package io.kim_kong.covid_19_mask.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object APIClient {

    private val URL = "https://8oi9s0nnth.apigw.ntruss.com/corona19-masks/v1/storesByGeo/"
    private var mRetrofit: Retrofit? = null

    val instance: Retrofit
        get() {

            if (mRetrofit == null) {
                mRetrofit = Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return mRetrofit!!
        }
}