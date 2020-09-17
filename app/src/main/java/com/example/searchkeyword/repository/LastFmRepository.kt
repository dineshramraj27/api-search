package com.example.searchkeyword.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LastFmRepository(application: Application) {

    var mutableLivData: MutableLiveData<com.example.searchkeyword.model.Response> = MutableLiveData()

    fun getAlbumData(
        search: String,
        apiKey: String,
        format: String
    ): MutableLiveData<com.example.searchkeyword.model.Response> {
        val apiService: LastFmService = RetrofitInstance().buildService(LastFmService::class.java)

        val call:Call<com.example.searchkeyword.model.Response> = apiService.searchAlbum(search, apiKey, format)

        call.enqueue(object : Callback<com.example.searchkeyword.model.Response> {
            override fun onResponse(
                call: retrofit2.Call<com.example.searchkeyword.model.Response>,
                response: Response<com.example.searchkeyword.model.Response>
            ) {
                if (response.isSuccessful) {
                    mutableLivData.value = response.body()
                    Log.d("API_Response", response.body().toString())
                }
            }

            override fun onFailure(call: retrofit2.Call<com.example.searchkeyword.model.Response>, t: Throwable) {
                mutableLivData.value = null
                Log.d("API_Response", t.message.toString())
            }
        })
        return mutableLivData
    }

    fun getArtistData(
        search: String,
        apiKey: String,
        format: String
    ): MutableLiveData<com.example.searchkeyword.model.Response> {
        val apiService: LastFmService = RetrofitInstance().buildService(LastFmService::class.java)

        val call = apiService.searchArtist(search, apiKey, format)
        apiCall(call)

        return mutableLivData
    }

    fun getSongData(
        search: String,
        apiKey: String,
        format: String
    ): MutableLiveData<com.example.searchkeyword.model.Response> {
        val apiService: LastFmService = RetrofitInstance().buildService(LastFmService::class.java)
        val call: Call<com.example.searchkeyword.model.Response> = apiService.searchSongs(search, apiKey, format)

        apiCall(call)
        return mutableLivData
    }

    private fun apiCall(call: Call<com.example.searchkeyword.model.Response>): MutableLiveData<com.example.searchkeyword.model.Response> {

        call.enqueue(object : Callback<com.example.searchkeyword.model.Response> {
            override fun onResponse(
                call: retrofit2.Call<com.example.searchkeyword.model.Response>,
                response: Response<com.example.searchkeyword.model.Response>
            ) {
                if (response.isSuccessful) {
                    mutableLivData.value = response.body()
                    Log.d("API_Response", response.body().toString())
                }
            }

            override fun onFailure(call: retrofit2.Call<com.example.searchkeyword.model.Response>, t: Throwable) {
                mutableLivData.value = null
                Log.d("API_Response", t.message.toString())
            }
        })
        return mutableLivData
    }
}