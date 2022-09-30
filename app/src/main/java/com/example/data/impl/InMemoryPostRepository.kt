package com.example.data.impl

import androidx.lifecycle.MutableLiveData
import com.example.data.Post
import com.example.data.PostRepository

class InMemoryPostRepository : PostRepository {

    private var nextId = GENERATED_POSTS_AMOUNT

    private var posts
        get() = checkNotNull(data.value)
        set(value) {
            data.value = value
        }

    override val data: MutableLiveData<List<Post>>

    init {
        val initialPosts = List(GENERATED_POSTS_AMOUNT) { index ->
            Post(
                id = index + 1,
                author = "Maxim",
                content = "Контент поста №${index + 1}",
                published = "19 августа 2022",
                likes = 999,
                share = 98,
                video = "https://www.youtube.com/watch?v=WhWc3b3KhnY",
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

    override fun delete(postId: Int) {
        data.value = posts.filter { it.id != postId }
    }

    override fun save(post: Post) {
        if (post.id == PostRepository.NEW_POST_ID) insert(post) else update(post)
    }

    private fun insert(post: Post) {
        data.value = listOf(
            post.copy(id = ++nextId)
        ) + posts
    }

    private fun update(post: Post) {
        data.value = posts.map {
            if (it.id == post.id) post else it
        }
    }

    private companion object {
        const val GENERATED_POSTS_AMOUNT = 1000

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