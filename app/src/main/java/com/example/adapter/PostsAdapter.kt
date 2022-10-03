package com.example.adapter

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.content.ContextCompat.RECEIVER_EXPORTED
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.data.Post
import com.example.androidhomework.R
import com.example.androidhomework.databinding.PostListItemBinding
import kotlin.math.floor

class PostsAdapter(
    private val interactionListener: PostInteractionListener
) : ListAdapter<Post, PostsAdapter.ViewHolder>(DiffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("PostsAdapter", "OnCreateViewHolder")
        val inflater = LayoutInflater.from(parent.context)
        val binding = PostListItemBinding.inflate(
            inflater, parent, false
        )
        return ViewHolder(binding, interactionListener)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("Posts adapter", "onBindViewHolder: $position")
        val post = getItem(position)
        holder.bind(post)
    }

    class ViewHolder(
        private val binding: PostListItemBinding,
        listener: PostInteractionListener
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var post: Post
        private val popupMenu by lazy {
            PopupMenu(itemView.context, binding.postButtonMore).apply {
                inflate(R.menu.options_post)
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.remove -> {
                            listener.onRemoveClicked(post)
                            true
                        }
                        R.id.edit -> {
                            listener.onEditClicked(post)
                            true
                        }
                        else -> false
                    }
                    false
                }
            }
        }

        init {
            binding.postButtonLike.setOnClickListener {
                listener.onLikeClicked(post)
            }
            binding.postButtonShare.setOnClickListener {
                listener.onShareClicked(post)
            }
            binding.postButtonMore.setOnClickListener { popupMenu.show() }
            binding.postVideo.setOnClickListener {
                listener.onVideoPostClicked(post)
            }

        }

        fun bind(post: Post) {
            this.post = post

            with(binding) {
                postText.text = post.author + "\n" + post.content
                postTimePanel.text = post.published
                postButtonShare.text = setNumberOrder(post.share)
                postButtonLike.text = setNumberOrder(post.likes)
                postButtonLike.isChecked = post.likeByMe
                postVideo.visibility = isVisible(post.video)
            }
        }

        private fun isVisible(check: String?): Int {
            if (check == null) {
                return 4
            }
            return 0
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