package com.dicoding.android.storyapp.ui.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.android.storyapp.data.model.UserPreference
import com.dicoding.android.storyapp.ui.login.LoginViewModel
import com.dicoding.android.storyapp.ui.main.MainViewModel

class ViewModelFactory(private val pref: UserPreference) : ViewModelProvider.NewInstanceFactory() {

  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    return when {
      modelClass.isAssignableFrom(MainViewModel::class.java) -> {
        MainViewModel(pref) as T
      }
      modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
        LoginViewModel(pref) as T
      }
      else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
  }
}