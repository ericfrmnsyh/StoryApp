package com.dicoding.android.storyapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.android.storyapp.data.model.UserModel
import com.dicoding.android.storyapp.data.model.UserPreference
import kotlinx.coroutines.launch

class MainViewModel(private val pref: UserPreference) : ViewModel()  {

  fun getUser(): LiveData<UserModel> {
    return pref.getUser().asLiveData()
  }

  fun logout() {
    viewModelScope.launch {
      pref.logout()
    }
  }
}