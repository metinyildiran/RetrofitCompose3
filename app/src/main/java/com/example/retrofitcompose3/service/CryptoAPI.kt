package com.example.retrofitcompose3.service

import com.example.retrofitcompose3.model.CryptoModel
import retrofit2.Call
import retrofit2.http.GET

interface CryptoAPI {
    // https://raw.githubusercontent.com/atilsamancioglu/K21-JSONDataSet/master/crypto.json
    // BASE -> https://raw.githubusercontent.com/

    @GET("atilsamancioglu/K21-JSONDataSet/master/crypto.json")
    fun getData(): Call<List<CryptoModel>>
}