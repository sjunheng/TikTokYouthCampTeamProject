package com.tiktokyouthcamp.aircare.network

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


object NetworkModule {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.data.gov.sg/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val airCondApi: AirCondApi = retrofit.create(AirCondApi::class.java)
}