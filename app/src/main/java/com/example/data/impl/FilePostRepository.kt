package com.example.data.impl


import android.app.Application
import android.content.Context
import androidx.core.content.edit
import androidx.lifecycle.MutableLiveData
import com.example.data.Post
import com.example.data.PostRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.properties.Delegates


class FilePostRepository(
    private val application: Application
) : PostRepository {

    private val gson = Gson()
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type

    private val prefs = application.getSharedPreferences(
        "repo", Context.MODE_PRIVATE
    )
    private var nextId: Int by Delegates.observable(
        prefs.getInt(NEXT_ID_PREFS_KEY, 0)
    ) { _, _, newValue ->
        prefs.edit { putInt(NEXT_ID_PREFS_KEY, newValue) }
    }


    private var posts
        get() = checkNotNull(data.value) {
            "Data value should be not null"
        }
        set(value) {
            application.openFileOutput(
                FILE_NAME, Context.MODE_PRIVATE
            ).bufferedWriter().use {
                it.write(gson.toJson(value))
            }
            data.value = value
        }

    override val data: MutableLiveData<List<Post>>

    init {
        val postsFile = application.filesDir.resolve(FILE_NAME)
        val posts: List<Post> = if (postsFile.exists()) {
            val inputStream = application.openFileInput(FILE_NAME)
            val reader = inputStream.bufferedReader()
            reader.use { gson.fromJson(it, type) }
        } else emptyList()
        data = MutableLiveData(posts)
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
        posts = listOf(
            post.copy(id = ++nextId)
        ) + posts
    }

    private fun update(post: Post) {
        posts = posts.map {
            if (it.id == post.id) post else it
        }
    }

    private companion object {
        const val GENERATED_POSTS_AMOUNT = 1000
        const val POSTS_PRESS_KEY = "posts"
        const val NEXT_ID_PREFS_KEY = "nextId"
        const val FILE_NAME = "posts.json"
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