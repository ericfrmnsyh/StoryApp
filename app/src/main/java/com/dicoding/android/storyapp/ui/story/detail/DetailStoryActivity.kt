package com.dicoding.android.storyapp.ui.story.detail

import android.graphics.text.LineBreaker
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.dicoding.android.storyapp.R
import com.dicoding.android.storyapp.data.remote.ListStoryItem
import com.dicoding.android.storyapp.databinding.ActivityDetailStoryBinding

class DetailStoryActivity : AppCompatActivity() {
    private lateinit var story: ListStoryItem
    private lateinit var binding: ActivityDetailStoryBinding

    private val viewModel: DetailStoryViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Detail Story"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            run {
                binding.tvDescription.justificationMode = LineBreaker.JUSTIFICATION_MODE_INTER_WORD
            }
        }

        story = intent.getParcelableExtra(EXTRA_STORY)!!
        viewModel.setDetailStory(story)
        updateDetailData()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun updateDetailData() {
        with(binding){
            tvName.text = viewModel.storyItem.name
            tvCreatedTime.text = getString(R.string.created_add, viewModel.storyItem.createdAt)
            tvDescription.text = viewModel.storyItem.description

            Glide.with(ivStory)
                .load(viewModel.storyItem.photoUrl)
                .placeholder(R.drawable.ic_place_holder)
                .error(R.drawable.ic_broken_image)
                .into(ivStory)
        }
    }

    companion object {
        const val EXTRA_STORY = "story"
    }
}