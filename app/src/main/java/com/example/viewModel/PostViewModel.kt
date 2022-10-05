package com.example.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.adapter.PostInteractionListener
import com.example.data.Post
import com.example.data.PostRepository
import com.example.data.impl.FilePostRepository
import com.example.util.SingleLiveEvent

class PostViewModel(
    application: Application
) : AndroidViewModel(application),
    PostInteractionListener {

    private val repository: PostRepository =
        FilePostRepository(application)

    val data get() = repository.data


    val sharePostContent = SingleLiveEvent<String>()
    val showPostVideo = SingleLiveEvent<String>()

    val currentPost = MutableLiveData<Post?>(null)

    companion object{
        var POST_CONTENT: String = ""
    }

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
        POST_CONTENT = post.content
        currentPost.value = post
    }

}