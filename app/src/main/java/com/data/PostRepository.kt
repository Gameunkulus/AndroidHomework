package com.data

import androidx.annotation.DrawableRes
import androidx.lifecycle.LiveData

interface PostRepository {

    val data: LiveData<Post>

    fun like()

    fun share()

    fun setNumberOrder(num: Int): String

    @DrawableRes
    fun getPostButtonLikeResId(liked: Boolean): Int
}