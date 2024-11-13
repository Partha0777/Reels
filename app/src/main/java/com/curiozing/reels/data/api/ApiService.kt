package com.curiozing.reels.data.api

import com.curiozing.reels.AppKeysAndBaseUrl
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {
    private var retrofitClient: Retrofit? = null

    fun getRetrofitClient() : Retrofit {
        return if(retrofitClient != null){
            retrofitClient as Retrofit
        }else{
            retrofitClient = Retrofit.Builder()
                .client(OkHttpClient()).baseUrl(AppKeysAndBaseUrl.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
            retrofitClient as Retrofit
        }
    }
}