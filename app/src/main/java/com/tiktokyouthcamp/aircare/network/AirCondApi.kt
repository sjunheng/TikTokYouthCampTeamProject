package com.tiktokyouthcamp.aircare.network

import com.squareup.moshi.Json
import retrofit2.Call
import retrofit2.http.GET

data class Wrapper(
    @field:Json(name = "items") val items: List<Items>
)

data class Items(
    @field:Json(name = "readings") val readings: Readings
)

data class Readings(
    @field:Json(name = "pm25_one_hourly") val pm25: PM25
)

data class PM25(
    @field:Json(name = "central") val central: Double,
    @field:Json(name = "west") val west: Double,
    @field:Json(name = "east") val east: Double,
    @field:Json(name = "north") val north: Double,
    @field:Json(name = "south") val south: Double,
)

data class Infos(
    @field:Json(name = "api_info") val status: Status
)

data class Status(
    @field:Json(name = "status") val description: String
)

interface AirCondApi {
    @GET("v1/environment/pm25/")
    fun getAirQualityStatus() : Call<Infos>

    @GET("v1/environment/pm25/")
    fun getAirQualityIndex() : Call<Wrapper>

}