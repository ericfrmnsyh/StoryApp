package com.dicoding.android.storyapp.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.android.storyapp.R
import com.dicoding.android.storyapp.data.remote.ListStoryItem
import com.dicoding.android.storyapp.databinding.ItemListStoryBinding
import com.dicoding.android.storyapp.ui.story.detail.DetailStoryActivity
import com.dicoding.android.storyapp.helper.MyDiffUtil

class ListAdapter(private var listStory : MutableList<ListStoryItem> = mutableListOf())
  : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

  fun setData(newListStory: MutableList<ListStoryItem>){
    val diffCallback = MyDiffUtil(listStory, newListStory)
    val diffResult = DiffUtil.calculateDiff(diffCallback)
    listStory = newListStory
    diffResult.dispatchUpdatesTo(this)
  }

  inner class ViewHolder(private val binding : ItemListStoryBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(story: ListStoryItem) {
      with(binding){
        Glide.with(imgItemImage)
          .load(story.photoUrl)
          .placeholder(R.drawable.ic_place_holder)
          .error(R.drawable.ic_broken_image)
          .into(binding.imgItemImage)
        tvName.text = story.name
        tvCreatedTime.text = binding.root.resources.getString(R.string.created_add, story.createdAt)

        binding.imgItemImage.setOnClickListener {
          val intent = Intent(it.context, DetailStoryActivity::class.java)
          intent.putExtra(DetailStoryActivity.EXTRA_STORY, story)
          it.context.startActivity(intent)
        }
      }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val binding = ItemListStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return ViewHolder(binding)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(listStory[position])
  }

  override fun getItemCount() = listStory.size
}