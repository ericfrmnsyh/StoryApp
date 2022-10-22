package com.dicoding.android.storyapp.ui.register

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.android.storyapp.data.remote.CommonResponse
import com.dicoding.android.storyapp.data.remote.api.ApiConfig
import com.dicoding.android.storyapp.helper.ApiCallbackString
import org.json.JSONObject
import org.json.JSONTokener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegisterViewModel : ViewModel() {

  private val _isLoading = MutableLiveData<Boolean>()

  private val request = ApiConfig().getApiService()
  
  fun register(name: String, email: String, pass: String, callback: ApiCallbackString){
    _isLoading.value = true

    val call = request.register(name, email, pass)
    call.enqueue(object : Callback<CommonResponse> {
      override fun onResponse(
          call: Call<CommonResponse>,
          response: Response<CommonResponse>
      ) {
        _isLoading.value = false
        if (response.isSuccessful) {
          val responseBody = response.body()
          if (responseBody != null && !responseBody.error)
            callback.onResponse(response.body() != null, SUCCESS)

        } else {
          Log.e(TAG, "onFailure1: ${response.message()}")

          val jsonObject = JSONTokener(response.errorBody()!!.string()).nextValue() as JSONObject
          val message = jsonObject.getString("message")
          callback.onResponse(false, message)
        }
      }

      override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
        _isLoading.value = false
        Log.e(TAG, "onFailure2: ${t.message}")
        callback.onResponse(false, t.message.toString())
      }
    })
  }

  companion object {
    private const val TAG = "RegisterViewModel"
    private const val SUCCESS = "success"
  }
}