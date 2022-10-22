package com.dicoding.android.storyapp.ui.story.list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.android.storyapp.databinding.ActivityListStoryBinding
import com.dicoding.android.storyapp.ui.adapter.ListAdapter
import com.dicoding.android.storyapp.data.model.UserModel
import com.dicoding.android.storyapp.data.remote.ListStoryItem
import com.dicoding.android.storyapp.ui.story.add.AddStoryActivity
import com.dicoding.android.storyapp.ui.factory.ListViewModelFactory

class ListStoryActivity : AppCompatActivity(){

    private var listBinding: ActivityListStoryBinding? = null
    private val binding get() = listBinding

    private lateinit var user: UserModel
    private lateinit var listAdapter: ListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listBinding = ActivityListStoryBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        addStoryAction()
        user = intent.getParcelableExtra(EXTRA_USER)!!

        getList()
        setAdapter()
    }

    private fun connectViewModel(): ListStoryViewModel {
        return obtainViewModel(this@ListStoryActivity)
    }

    private fun obtainViewModel(activity: AppCompatActivity): ListStoryViewModel {
        val factory = ListViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[ListStoryViewModel::class.java]
    }

    private fun getList(){
        connectViewModel()
        connectViewModel().showListStory(user.token)
        connectViewModel().getList().observe(this){ storyList ->
            if (storyList != null){
                listAdapter.setData(storyList as MutableList<ListStoryItem>)
                showLoading(false)
            }
        }
    }

    private fun setAdapter(){
        listAdapter = ListAdapter()
        binding?.rvStory?.apply {
            layoutManager = LinearLayoutManager(this@ListStoryActivity)
            setHasFixedSize(true)
            adapter = listAdapter
        }
        binding?.tvInfo?.visibility = View.VISIBLE
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun showLoading(state: Boolean) {
        if (state){
            binding?.rvStory?.visibility = View.GONE
            binding?.progressBar?.visibility = View.VISIBLE
            binding?.tvInfo?.visibility = View.GONE
        } else {
            binding?.progressBar?.visibility = View.GONE
            binding?.rvStory?.visibility = View.VISIBLE
            binding?.tvInfo?.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        listBinding = null
    }

    override fun onResume() {
        super.onResume()
        getList()
    }

    private fun addStoryAction(){
        binding?.ivAddStory?.setOnClickListener {
            val moveToAddStoryActivity = Intent(this, AddStoryActivity::class.java)
            moveToAddStoryActivity.putExtra(AddStoryActivity.EXTRA_USER, user)
            startActivity(moveToAddStoryActivity)
        }
    }

    companion object {
        const val EXTRA_USER = "user"
    }
}