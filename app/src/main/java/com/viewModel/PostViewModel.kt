package com.viewModel

import androidx.annotation.DrawableRes
import androidx.lifecycle.ViewModel
import com.data.PostRepository
import com.data.impl.InMemoryPostRepository

class PostViewModel : ViewModel() {

    private val repository: PostRepository = InMemoryPostRepository()

    val data by repository::data

    fun onLikeClicked() = repository.like()

    fun onShareCkicked() = repository.share()

    fun setNumOrder(num: Int): String = repository.setNumberOrder(num)

    @DrawableRes
    fun getPostButtonLikeResId(liked: Boolean) = repository.getPostButtonLikeResId(liked)


}