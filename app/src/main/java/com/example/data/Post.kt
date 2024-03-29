package com.example.data

import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val id: Int,
    val author: String,
    val content: String,
    val published: String,
    var likes: Int = 0,
    var share: Int = 0,
    var video: String?,
    var likeByMe: Boolean = false,
)