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


    fun checkLikeByMe(like: Boolean, num: Int): Int {
        if (like) {
            return num + 1
        } else {
            return num - 1
        }
    }


}