package com.example.androidhomework


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.core.view.get
import androidx.lifecycle.Lifecycle
import com.data.Post
import com.example.androidhomework.databinding.ActivityMainBinding
import com.viewModel.PostViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.floor


class MainActivity : AppCompatActivity() {

    private val viewModel = PostViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.data.observe(this) { post ->
            binding.render(post)
        }

        binding.netologyMark.setOnClickListener {
            println("I love programming very mutch")
        }

        binding.postButtonLike.setOnClickListener {
            viewModel.onLikeClicked()
            getPostButtonLikeResId(viewModel.data.value!!.likeByMe)
        }

        binding.postButtonShare.setOnClickListener {
            viewModel.onShareClicked()
        }

    }

    private fun setNumberOrder(num: Int): String {
        if (num >= 1000000) {

            var newNum: Double = num.toDouble() / 1000000
            newNum = floor(newNum * 10.0) / 10.0
            return newNum.toString() + "M"

        } else if (num < 1000000 && num >= 10000) {

            val newNum: Int = num / 1000
            return newNum.toString() + "K"

        } else if (num >= 1000 && num < 10000) {

            var newNum: Double = num.toDouble() / 1000
            newNum = floor(newNum * 10.0) / 10.0
            return newNum.toString() + "K"

        } else {
            return num.toString()
        }
    }

    @DrawableRes
    private fun getPostButtonLikeResId(liked: Boolean) =
        if (liked) R.drawable.liked else R.drawable.like

    private fun ActivityMainBinding.render(post: Post) {
        postText.text = post.author
        postText.text = post.content
        postLikeNumber.text = setNumberOrder(post.likes)
        postTimePanel.text = post.published
        postShareNumber.text = setNumberOrder(post.share)
        postButtonLike.setImageResource(getPostButtonLikeResId(post.likeByMe))
    }

}

