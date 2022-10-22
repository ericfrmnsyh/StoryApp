package com.dicoding.android.storyapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
  val name: String,
  val email: String,
  val password: String,
  val userId: String,
  val token: String,
  val isLogin: Boolean
): Parcelable