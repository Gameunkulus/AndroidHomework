package com.example.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.androidhomework.databinding.ActivityNewPostBinding
import com.example.data.Post
import com.example.util.hideKeyBoard
import kotlinx.android.synthetic.main.activity_new_post.*
import kotlinx.android.synthetic.main.activity_new_post.view.*

class NewPostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityNewPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.edit.requestFocus()
        binding.ok.setOnClickListener {
            onOkButtonClicked(binding.edit.text?.toString())
        }
        binding.edit.setOnClickListener {
            panel_decline_button.visibility = View.VISIBLE
        }
        binding.panelDeclineButton.setOnClickListener {
            with(binding.edit) {
                clearFocus()
                hideKeyBoard()
                onDecklineButtonClicked()
            }

        }
    }

    private fun onOkButtonClicked(postContent: String?) {

        if (postContent.isNullOrBlank()) {
            setResult(Activity.RESULT_CANCELED)
        } else {
            val resultIntent = Intent()
            resultIntent.putExtra(POST_CONTENT_EXTRA_KEY, postContent)
            setResult(Activity.RESULT_OK, resultIntent)
        }
        finish()
    }

    private fun onDecklineButtonClicked(){
        panel_decline_button.visibility = View.INVISIBLE
        setResult(Activity.RESULT_CANCELED)
        finish()
    }

    private companion object {
        const val POST_CONTENT_EXTRA_KEY = "postContent"
    }

    object ResultContract : ActivityResultContract<Int, String?>() {

        override fun createIntent(context: Context, input: Int) =
            Intent(context, NewPostActivity::class.java)

        override fun parseResult(resultCode: Int, intent: Intent?): String? {
            if (resultCode != Activity.RESULT_OK) return null
            intent ?: return null
            return intent.getStringExtra(POST_CONTENT_EXTRA_KEY)
        }
    }
}