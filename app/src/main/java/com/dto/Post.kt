package com.dto

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    var likes: Int,
    var share: Int,
    var likeByMe: Boolean,
)