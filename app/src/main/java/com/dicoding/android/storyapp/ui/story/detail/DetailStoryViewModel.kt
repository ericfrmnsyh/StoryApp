package com.dicoding.android.storyapp.ui.story.detail

import androidx.lifecycle.ViewModel
import com.dicoding.android.storyapp.data.remote.ListStoryItem

class DetailStoryViewModel: ViewModel() {
  lateinit var storyItem: ListStoryItem

  fun setDetailStory(story: ListStoryItem) : ListStoryItem {
    storyItem = story
    return storyItem
  }

}