package com.example.searchkeyword.repository

import com.example.searchkeyword.model.Albummatches
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

interface LastFmService {
    @GET("/2.0/?method=artist.search")
    fun searchArtist(
        @Query("artist") artist: String,
        @Query("api_key") key: String,
        @Query("format") format: String
    ): Call<com.example.searchkeyword.model.Response/**/>

    @GET("/2.0/?method=album.search")
    fun searchAlbum(
        @Query("album") search: String,
        @Query("api_key") api_key: String,
        @Query("format") format: String
    ): Call<com.example.searchkeyword.model.Response>

    @GET("/2.0/?method=track.search")
    fun searchSongs(
        @Query("track") songs: String,
        @Query("api_key") key: String,
        @Query("format") format: String
    ): Call<com.example.searchkeyword.model.Response>
}