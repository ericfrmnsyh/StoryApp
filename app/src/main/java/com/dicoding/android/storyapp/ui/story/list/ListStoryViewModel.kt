package com.dicoding.android.storyapp.ui.story.list

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.android.storyapp.data.remote.GetStoriesResponse
import com.dicoding.android.storyapp.data.remote.ListStoryItem
import com.dicoding.android.storyapp.data.remote.api.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListStoryViewModel(application: Application) : AndroidViewModel(application) {
  private val _itemStory = MutableLiveData<List<ListStoryItem>>()

  fun getList(): LiveData<List<ListStoryItem>>{
    return _itemStory
  }

  private val request = ApiConfig().getApiService()

  fun showListStory(token: String) {
    val call = request.getAllStories("Bearer $token")

    call.enqueue(object : Callback<GetStoriesResponse> {
      override fun onResponse(
        call: Call<GetStoriesResponse>,
        response: Response<GetStoriesResponse>
      ) {
        if (response.isSuccessful) {
          response.body()?.let {
            _itemStory.postValue(it.listStory)
          }
        }
      }

      override fun onFailure(call: Call<GetStoriesResponse>, t: Throwable) {
        Log.e(TAG, "onFailure: ${t.message}")
      }
    })
  }

  companion object {
    private const val TAG = "ListStoryViewModel"
  }
}