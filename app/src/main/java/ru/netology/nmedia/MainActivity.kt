package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.post_list_item.*
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.util.hideKeyboard
import ru.netology.nmedia.util.showKeyboard
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<PostViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = PostsAdapter(viewModel)

        binding.postsRecyclerView.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        binding.saveButton.setOnClickListener {
            with(binding.contentTextEdit) {
                val content = binding.contentTextEdit.text.toString()
                viewModel.onSaveButtonClicked(content)

            }
        }

        binding.closeEditButton.setOnClickListener {
            with(binding.contentTextEdit) {

                viewModel.onCloseButtonClicked()
                clearFocus()
                hideKeyboard()
            }
            binding.groupForEdit.visibility = View.GONE
        }

        viewModel.currentPost.observe(this) { currentPost ->
            with(binding.contentTextEdit) {
                val content = currentPost?.content
                setText(content)
                if (content != null) {
                    binding.groupForEdit.visibility = View.VISIBLE
                    requestFocus()
                    showKeyboard()
                } else {
                    binding.groupForEdit.visibility = View.GONE
                    clearFocus()
                    hideKeyboard()

                }
            }
        }
    }
}
