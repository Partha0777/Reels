package com.curiozing.reels.data.api

import com.curiozing.reels.AppKeysAndBaseUrl
import com.curiozing.reels.model.reels.Reels
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

object ApiService {
    private var retrofitClient: Retrofit? = null
    private fun getRetrofitClient(): Retrofit {
        return if (retrofitClient != null) {
            retrofitClient as Retrofit
        } else {
            retrofitClient = Retrofit.Builder()
                .client(OkHttpClient()).baseUrl(AppKeysAndBaseUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
            retrofitClient as Retrofit
        }
    }

    val reelsAPI: ReelsAPI = getRetrofitClient().create(ReelsAPI::class.java)
}

interface ReelsAPI {
    @GET("/data/reels.json")
    fun getReels(): Deferred<List<Reels>>
}