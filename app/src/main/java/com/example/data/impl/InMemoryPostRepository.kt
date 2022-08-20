package com.example.data.impl

import androidx.lifecycle.MutableLiveData
import com.example.data.Post
import com.example.data.PostRepository

class InMemoryPostRepository : PostRepository {
    private var posts
        get() = checkNotNull(data.value)
        set(value) {
            data.value = value
        }

    override val data: MutableLiveData<List<Post>>

    init {
        val initialPosts = List(1000) { index ->
            Post(
                id = index + 1,
                author = "Maxim",
                content = "Контент поста №${index + 1}",
                published = "19 августа 2022",
                likes = 999,
                share = 98,
                likeByMe = false
            )
        }
        data = MutableLiveData(initialPosts)
    }

    override fun like(postId: Int) {
        posts = posts.map { post ->
            if (post.id == postId) {
                post.copy(
                    likes = checkLikeByMe(!post.likeByMe, post.likes),
                    likeByMe = !post.likeByMe
                )
            } else post
        }

    }

    override fun share(postId: Int) {
        posts = posts.map { post ->
            if (post.id == postId) post.copy(
                share = post.share + 1
            )
            else post
        }

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