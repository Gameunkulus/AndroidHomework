package com.example.viewModel

import androidx.lifecycle.ViewModel
import com.example.data.Post
import com.example.data.PostRepository
import com.example.data.impl.InMemoryPostRepository

class PostViewModel : ViewModel() {

    private val repository: PostRepository = InMemoryPostRepository()

    val data get() = repository.data

    fun onLikeClicked(post: Post) = repository.like(post.id)

    fun onShareClicked(post: Post) = repository.share(post.id)

}