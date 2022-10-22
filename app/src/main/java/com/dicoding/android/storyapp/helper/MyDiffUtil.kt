package com.dicoding.android.storyapp.helper

import androidx.recyclerview.widget.DiffUtil
import com.dicoding.android.storyapp.data.remote.ListStoryItem

class MyDiffUtil(
    private val oldList: List<ListStoryItem>,
    private val newList: List<ListStoryItem>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

  override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
     oldList[oldItemPosition].id == newList[newItemPosition].id

  override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
      return when {
          oldList[oldItemPosition].id != newList[newItemPosition].id -> {
              false
          }
          oldList[oldItemPosition].name != newList[newItemPosition].name -> {
              false
          }
          oldList[oldItemPosition].photoUrl != newList[newItemPosition].photoUrl -> {
              false
          }
          else -> true
      }
  }
}