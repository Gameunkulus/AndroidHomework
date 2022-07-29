package com.data.impl

import androidx.annotation.DrawableRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.data.Post
import com.data.PostRepository
import com.example.androidhomework.R
import kotlin.math.floor

class InMemoryPostRepository : PostRepository {

    override val data = MutableLiveData<Post>(
        Post(
            id = 1,
            author = "Maxim",
            content = "Content",
            published = "Time",
            likes = 999,
            share = 98,
            likeByMe = false
        )
    )

    override fun like() {
        val currentPost = checkNotNull(data.value) {
            "Post should be not null"
        }
        val likedPost = currentPost.copy(
            likes = checkLikeByMe(!currentPost.likeByMe,currentPost.likes),
            likeByMe = !currentPost.likeByMe
        )
        data.value = likedPost

    }

    override fun share() {
        val currentPost = checkNotNull(data.value) {
            "Post should be not null"
        }
        val sharedPost = currentPost.copy(
            share = currentPost.share + 1
        )
        data.value = sharedPost
    }

    //Настройка отображения количества like и share
    override fun setNumberOrder(num: Int): String {
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

    fun checkLikeByMe(like: Boolean, num: Int): Int {
        if (like) {
            return num + 1
        } else {
            return num - 1
        }
    }

    @DrawableRes
    override fun getPostButtonLikeResId(liked: Boolean) =
        if (liked) R.drawable.liked else R.drawable.like


}