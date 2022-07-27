package com.example.androidhomework


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.annotation.DrawableRes
import com.dto.Post
import com.example.androidhomework.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.floor


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            id = 0L,
            author = "Maxim",
            content = "Content",
            published = "Time",
            likes = 99,
            share = 98,
            likeByMe = false
        )

        binding.render(post)

        binding.postButtonLike.setOnClickListener {
            post.likeByMe = !post.likeByMe
            post.likes = checkLikeByMe(post.likeByMe, post.likes)
            binding.postButtonLike.setImageResource(getPostButtonLikeResId(post.likeByMe))
            binding.postLikeNumber.text = setNumberOrder(post.likes)
        }
        binding.postButtonShare.setOnClickListener {
            post.share++
            binding.postShareNumber.text = setNumberOrder(post.share)
        }

    }

    private fun ActivityMainBinding.render(post: Post) {
        postText.text = post.author
        postText.text = post.content
        postLikeNumber.text = setNumberOrder(post.likes)
        postTimePanel.text = post.published
        postShareNumber.text = setNumberOrder(post.share)
        postButtonLike.setImageResource(getPostButtonLikeResId(post.likeByMe))
    }


    @DrawableRes
    private fun getPostButtonLikeResId(liked: Boolean) =
        if (liked) R.drawable.liked else R.drawable.like

    //Настройка отображения количества like и share
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

    //проверка на наличие самолайка
    private fun checkLikeByMe(like: Boolean, num: Int): Int{
        var newNum: Int = num
        if(like){
            return newNum + 1
        }
        else{ return newNum - 1}
    }

}