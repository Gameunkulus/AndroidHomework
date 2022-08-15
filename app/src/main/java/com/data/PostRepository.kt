package com.data

import androidx.annotation.DrawableRes
import androidx.lifecycle.LiveData

interface PostRepository {

    val data: LiveData<Post>

    fun like()

    fun share()
}