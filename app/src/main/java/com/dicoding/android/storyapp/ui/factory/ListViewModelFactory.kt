package com.dicoding.android.storyapp.ui.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.android.storyapp.ui.story.list.ListStoryViewModel

class ListViewModelFactory private constructor(private val mApplication: Application) :
    ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: ListViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): ListViewModelFactory {
            if (INSTANCE == null){
                synchronized(ListViewModelFactory::class.java){
                    INSTANCE = ListViewModelFactory(application)
                }
            }
            return INSTANCE as ListViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListStoryViewModel::class.java)){
            return ListStoryViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}