package com.dicoding.android.storyapp.data.remote.api

import com.dicoding.android.storyapp.data.remote.CommonResponse
import com.dicoding.android.storyapp.data.remote.GetStoriesResponse
import com.dicoding.android.storyapp.data.remote.LoginResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
  @FormUrlEncoded
  @POST("register")
  fun register(
    @Field("name") name: String,
    @Field("email") email: String,
    @Field("password") pass: String
  ): Call<CommonResponse>

  @FormUrlEncoded
  @POST("login")
  fun login(
    @Field("email") email: String,
    @Field("password") pass: String
  ): Call<LoginResponse>

  @Multipart
  @POST("stories")
  fun addStories(
    @Header("Authorization") token: String,
    @Part ("description") des: String,
    @Part file: MultipartBody.Part
  ): Call<CommonResponse>

  @GET("stories")
  fun getAllStories(
    @Header("Authorization") token: String
  ): Call<GetStoriesResponse>
}
