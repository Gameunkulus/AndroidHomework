package com.example.androidhomework


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.core.view.get
import com.data.Post
import com.example.androidhomework.databinding.ActivityMainBinding
import com.viewModel.PostViewModel
import kotlin.math.floor


class MainActivity : AppCompatActivity() {

    private val viewModel = PostViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.data.observe(this) {post ->
            binding.render(post)
        }

        binding.netologyMark.setOnClickListener {
            println("I love programming very mutch")
        }

        binding.postButtonLike.setOnClickListener {
            viewModel.onLikeClicked()
            viewModel.getPostButtonLikeResId(viewModel.data.value!!.likeByMe)
            binding.postLikeNumber.text = viewModel.setNumOrder(viewModel.data.value!!.likes)
        }

        binding.postButtonShare.setOnClickListener {
            viewModel.onShareCkicked()
            binding.postShareNumber.text = viewModel.setNumOrder(viewModel.data.value!!.share)
        }

    }

    private fun ActivityMainBinding.render(post: Post) {
        postText.text = post.author
        postText.text = post.content
        postLikeNumber.text = viewModel.setNumOrder(post.likes)
        postTimePanel.text = post.published
        postShareNumber.text = viewModel.setNumOrder(post.share)
        postButtonLike.setImageResource(viewModel.getPostButtonLikeResId(post.likeByMe))
    }






}

