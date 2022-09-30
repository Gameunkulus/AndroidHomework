package com.example.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.data.Post
import com.example.data.PostRepository
import com.example.adapter.PostInteractionListener
import com.example.data.impl.InMemoryPostRepository
import com.example.util.SingleLiveEvent

class PostViewModel : ViewModel(), PostInteractionListener {

    private val repository: PostRepository = InMemoryPostRepository()

    val data get() = repository.data

    val sharePostContent = SingleLiveEvent<String>()

    val showPostVideo = SingleLiveEvent<String>()

    val currentPost = MutableLiveData<Post?>(null)

    override fun onLikeClicked(post: Post) { repository.like(post.id)}

    override fun onShareClicked(post: Post) {
        sharePostContent.value = post.content
        repository.share(post.id)
    }

    override fun onVideoPostClicked(post: Post){
        showPostVideo.value = post.video.toString()
    }

    fun onCreateNewPost(newPostContent: String){
        if (newPostContent.isBlank()) {
            return
        }

        val post = currentPost.value?.copy(
            content = newPostContent
        ) ?: Post(
            id = PostRepository.NEW_POST_ID,
            author = "Me",
            content = newPostContent,
            published = "Today",
            video = null
        )
        repository.save(post)
        currentPost.value = null
    }

    override fun onRemoveClicked(post: Post) = repository.delete(post.id)

    override fun onEditClicked(post: Post) {
        currentPost.value = post
    }

}