package com.tiktokyouthcamp.aircare

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.util.Log
import android.view.View
import com.tiktokyouthcamp.aircare.network.AirCondApi
import com.tiktokyouthcamp.aircare.network.Infos
import com.tiktokyouthcamp.aircare.network.NetworkModule
import com.tiktokyouthcamp.aircare.network.Wrapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val airCondApi: AirCondApi = NetworkModule.airCondApi

    // View references. lazily initialized
    private val tv_status: TextView by lazy { findViewById(R.id.tvResult_status)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }

    public fun getAirCondition(view: View) {
        airCondApi.getAirQualityStatus()
            .enqueue(object : Callback<Infos> {
                override fun onResponse(call: Call<Infos>, response: Response<Infos>) {
                    val infos = response.body() ?: return
                    val airQuality = infos.status.description
                    tv_status.text = getString(R.string.air_quality_status, airQuality)

                }

                override fun onFailure(call: Call<Infos>, t: Throwable) {
                    Log.e("MainActivity", "getAirQualityStatus on Failure()", t)
                }
            })

        airCondApi.getAirQualityIndex()
            .enqueue(object : Callback<Wrapper> {
                override fun onResponse(call: Call<Wrapper>, response: Response<Wrapper>) {
                    val wrapper = response.body() ?: return
                    val pM25 = wrapper.items.get(0).readings.pm25
                    val index = (pM25.central + pM25.east + pM25.west
                        + pM25.central + pM25.north + pM25.south) / 5
                    val description = tv_status.text.toString()
                    tv_status.text = description + "\n" +
                            getString(R.string.air_quality_index, index.toString())

                }

                override fun onFailure(call: Call<Wrapper>, t: Throwable) {
                    Log.e("MainActivity", "getAirQualityIndex on Failure()", t)
                }
            })
    }
}