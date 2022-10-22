package com.dicoding.android.storyapp.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.android.storyapp.data.model.UserModel
import com.dicoding.android.storyapp.data.model.UserPreference
import com.dicoding.android.storyapp.data.remote.LoginResponse
import com.dicoding.android.storyapp.data.remote.api.ApiConfig
import com.dicoding.android.storyapp.helper.ApiCallbackString
import kotlinx.coroutines.launch
import org.json.JSONObject
import org.json.JSONTokener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val pref: UserPreference) : ViewModel() {

  private val _isLoading = MutableLiveData<Boolean>()

  private val request = ApiConfig().getApiService()

  fun login(email: String, pass: String, callback: ApiCallbackString){
    _isLoading.value = true

    val call = request.login(email, pass)
    call.enqueue(object : Callback<LoginResponse> {
      override fun onResponse(
        call: Call<LoginResponse>,
        response: Response<LoginResponse>
      ) {
        if (response.isSuccessful) {
          val responseBody = response.body()
          if (responseBody != null && !responseBody.error) {

            callback.onResponse(response.body() != null, SUCCESS)

            val model = UserModel(
              responseBody.loginResult.name,
              email,
              pass,
              responseBody.loginResult.userId,
              responseBody.loginResult.token,
              true
            )
            saveUser(model)
          }
        } else {
          Log.e(TAG, "onFailure: ${response.message()}")
          val jsonObject = JSONTokener(response.errorBody()!!.string()).nextValue() as JSONObject
          val message = jsonObject.getString("message")
          callback.onResponse(false, message)
        }
      }

      override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
        _isLoading.value = false
        Log.e(TAG, "onFailure: ${t.message}")
        callback.onResponse(false, t.message.toString())
      }
    })
  }

  fun saveUser(user: UserModel) {
    viewModelScope.launch {
      pref.saveUser(user)
    }
  }

  companion object {
    private const val TAG = "LoginViewModel"
    private const val SUCCESS = "success"
  }
}