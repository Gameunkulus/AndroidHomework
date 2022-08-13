package com.viewModel

import androidx.annotation.DrawableRes
import androidx.lifecycle.ViewModel
import com.data.PostRepository
import com.data.impl.InMemoryPostRepository

class PostViewModel : ViewModel() {

    private val repository: PostRepository = InMemoryPostRepository()

    val data by repository::data

    fun onLikeClicked() = repository.like()

    fun onShareClicked() = repository.share()

}