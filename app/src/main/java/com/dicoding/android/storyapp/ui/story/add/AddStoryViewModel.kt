package com.dicoding.android.storyapp.ui.story.add

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.android.storyapp.data.model.UserModel
import com.dicoding.android.storyapp.data.remote.CommonResponse
import com.dicoding.android.storyapp.data.remote.api.ApiConfig
import com.dicoding.android.storyapp.helper.ApiCallbackString
import okhttp3.MultipartBody
import org.json.JSONObject
import org.json.JSONTokener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddStoryViewModel : ViewModel() {
  private val _isLoading = MutableLiveData<Boolean>()

  private val request = ApiConfig().getApiService()

  fun uploadImage(user: UserModel, description: String, imageMultipart: MultipartBody.Part, callback: ApiCallbackString) {
    _isLoading.value = true
    val call = request.addStories("Bearer ${user.token}", description, imageMultipart)

    call.enqueue(object : Callback<CommonResponse> {
      override fun onResponse(
        call: Call<CommonResponse>,
        response: Response<CommonResponse>
      ) {
        if (response.isSuccessful) {
          val responseBody = response.body()
          if (responseBody != null && !responseBody.error) {
            callback.onResponse(response.body() != null, SUCCESS)
          }
        } else {
          Log.e(TAG, "onFailure: ${response.message()}")
          val jsonObject = JSONTokener(response.errorBody()!!.string()).nextValue() as JSONObject
          val message = jsonObject.getString("message")
          callback.onResponse(false, message)
        }
      }
      override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
        _isLoading.value = false
        Log.e(TAG, "onFailure: ${t.message}")
        callback.onResponse(false, t.message.toString())
      }
    })
  }

  companion object {
    private const val TAG = "AddStoryViewModel"
    private const val SUCCESS = "success"
  }
}