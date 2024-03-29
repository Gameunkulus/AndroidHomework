package com.example.adapter

import com.example.data.Post

interface PostInteractionListener {

    fun onLikeClicked(post:Post)
    fun onShareClicked(post: Post)
    fun onRemoveClicked(post: Post)
    fun onEditClicked(post:Post)
    fun onVideoPostClicked(post:Post)
}