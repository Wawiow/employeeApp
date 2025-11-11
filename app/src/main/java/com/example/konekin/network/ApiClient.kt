package com.example.konekin.network

import android.util.Log
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    fun getInstance(): ApiService {
        //digunakan utk logging
        //debuging melihat apa yang diterima response dari server
        val mHttpLoggingInterceptor = HttpLoggingInterceptor{msg -> Log.d("HTTP", msg) }

        //buat HTTp client
        val mOkHttpClient = OkHttpClient
            .Builder()
            .addInterceptor(mHttpLoggingInterceptor)
            .build()

        //build berdasarkan konfig
        val builder = Retrofit.Builder()
            .baseUrl("https://dummy.restapiexample.com/api/v1/")
        //GSON digunakan utk mapping dari json ke object kotlin
            .addConverterFactory(GsonConverterFactory.create())
            .client(mOkHttpClient)
            .build()

        return builder.create(ApiService::class.java)

    }
}