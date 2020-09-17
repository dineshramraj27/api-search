package com.example.searchkeyword.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.searchkeyword.model.Response
import com.example.searchkeyword.repository.LastFmRepository

class ViewModel(application: Application) : AndroidViewModel(application) {
    var repository: LastFmRepository? = LastFmRepository(application)

    // fetching the album data from the api service
    fun getAlbumData(searchKey: String, apiKey: String, format: String): MutableLiveData<Response>? {
        return repository?.getAlbumData(searchKey, apiKey, format)
    }
    // fetching the album data from the api service
    fun getArtistData(searchKey: String, apiKey: String, format: String): MutableLiveData<Response>? {
        return repository?.getArtistData(searchKey, apiKey, format)
    }
    // fetching the album data from the api service
    fun getSongData(searchKey: String, apiKey: String, format: String): MutableLiveData<Response>? {
        return repository?.getSongData(searchKey, apiKey, format)
    }

}