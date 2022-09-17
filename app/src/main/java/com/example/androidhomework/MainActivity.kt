@file:Suppress("DEPRECATION")

package com.example.androidhomework


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.*
import com.example.adapter.PostsAdapter
import com.example.androidhomework.databinding.ActivityMainBinding
import com.example.viewModel.PostViewModel
import com.util.hideKeyBoard
import com.util.showKeyBoard
import kotlinx.android.synthetic.main.activity_main.view.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()

        val adapter = PostsAdapter(viewModel)

        binding.postsRecyclerView.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        binding.panelSaveButton.setOnClickListener {
            with(binding.contentEditText) {
                val content = text.toString()
                viewModel.onSaveButtonClicked(content)
                clearFocus()
                hideKeyBoard()
            }
        }

        binding.panelDeclineButton.setOnClickListener {
            with(binding.contentEditText){
                clearFocus()
                hideKeyBoard()
            }

        }
        viewModel.currentPost.observe(this) { currentPost ->
            with(binding.contentEditText) {
                val content = currentPost?.content
                setText(content)
                if (content != null) {
                    requestFocus()
                    showKeyBoard()
                }
                else{
                    clearFocus()
                }
            }
        }
    }
}


