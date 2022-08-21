package com.example.data.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.data.Post
import com.example.androidhomework.R
import com.example.androidhomework.databinding.PostListItemBinding
import kotlin.math.floor

class PostsAdapter(
    private val onLikeClicked: (Post) -> Unit,
    private val onShareClicked: (Post) -> Unit
) : ListAdapter<Post, PostsAdapter.ViewHolder>(DiffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("PostsAdapter", "OnCreateViewHolder")
        val inflater = LayoutInflater.from(parent.context)
        val binding = PostListItemBinding.inflate(
            inflater, parent, false
        )
        return ViewHolder(binding, onLikeClicked, onShareClicked)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("Posts adapter", "onBindViewHolder: $position")

        val post = getItem(position)
        holder.bind(post)
    }

    class ViewHolder(
        private val binding: PostListItemBinding,
        private val onLikeClicked: (Post) -> Unit,
        private val onShareClicked: (Post) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var post: Post

        init {
            binding.postButtonLike.setOnClickListener {
                onLikeClicked(post)
            }
            binding.postButtonShare.setOnClickListener {
                onShareClicked(post)
            }
        }

        fun bind(post: Post) {
            this.post = post

            with(binding) {
                postText.text = post.author + "\n" + post.content
                postTimePanel.text = post.published
                postShareNumber.text = setNumberOrder(post.share)
                postLikeNumber.text = setNumberOrder(post.likes)
                postButtonLike.setImageResource(getPostButtonLikeResId(post.likeByMe))
            }
        }


        private fun setNumberOrder(num: Int): String {
            if (num >= 1000000) {

                var newNum: Double = num.toDouble() / 1000000
                newNum = floor(newNum * 10.0) / 10.0
                return newNum.toString() + "M"
            } else if (num < 1000000 && num >= 10000) {

                val newNum: Int = num / 1000
                return newNum.toString() + "K"
            } else if (num >= 1000 && num < 10000) {

                var newNum: Double = num.toDouble() / 1000
                newNum = floor(newNum * 10.0) / 10.0
                return newNum.toString() + "K"
            } else {

                return num.toString()
            }
        }

        @DrawableRes
        fun getPostButtonLikeResId(liked: Boolean) =
            if (liked) R.drawable.liked else R.drawable.like
    }

    private object DiffCallBack : DiffUtil.ItemCallback<Post>() {

        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }
    }
}