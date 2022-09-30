@file:Suppress("DEPRECATION")

package com.example.androidhomework


import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.*
import com.example.activity.NewPostActivity
import com.example.adapter.PostsAdapter
import com.example.androidhomework.databinding.ActivityMainBinding
import com.example.data.PostRepository
import com.example.viewModel.PostViewModel
import com.example.util.hideKeyBoard
import com.example.util.showKeyBoard


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()

        val adapter = PostsAdapter(viewModel)

        val activityLauncher = registerForActivityResult(
            NewPostActivity.ResultContract
        ) { postContent: String? ->
            postContent?.let (viewModel::onCreateNewPost)
        }

        binding.postsRecyclerView.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        viewModel.currentPost.observe(this) { currentPost ->
            with(binding.contentEditText) {
                if (currentPost != null) {
                    activityLauncher.launch(currentPost.id)
                }
            }
        }
        viewModel.sharePostContent.observe(this) { postContent ->
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, postContent)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(intent, getString(R.string.chooser_share_post))
            startActivity(shareIntent)
        }

        viewModel.showPostVideo.observe(this) {postVideo ->
            val intent = Intent()
                .setAction(Intent.ACTION_VIEW)
                .setData(Uri.parse("https://www.youtube.com/watch?v=WhWc3b3KhnY"))
            startActivity(intent)
        }




        binding.fab.setOnClickListener{
            activityLauncher.launch(PostRepository.NEW_POST_ID)
        }
    }
}


