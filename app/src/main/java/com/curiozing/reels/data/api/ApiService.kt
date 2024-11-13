package com.curiozing.reels.data.api

import com.curiozing.reels.AppKeysAndBaseUrl
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

object ApiService {
    private var retrofitClient: Retrofit? = null

    private fun getRetrofitClient() : Retrofit {
        return if(retrofitClient != null){
            retrofitClient as Retrofit
        }else{
            retrofitClient = Retrofit.Builder()
                .client(OkHttpClient()).baseUrl(AppKeysAndBaseUrl.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
            retrofitClient as Retrofit
        }
    }

    val reelsAPI: ReelsAPI = getRetrofitClient().create(ReelsAPI::class.java)
}

interface ReelsAPI{

    @GET("/data/reels.json")
    fun getReels() : Call<ResponseBody>
}