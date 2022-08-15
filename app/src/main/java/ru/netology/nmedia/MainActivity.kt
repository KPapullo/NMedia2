package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModel
import kotlinx.android.synthetic.main.activity_main.*


import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.databinding.PostListItemBinding
import ru.netology.nmedia.dto.PostsAdapter


import ru.netology.nmedia.dto.resFormat
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: PostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()

        val adapter = PostsAdapter(
            onLikeClicked = { post ->
                viewModel.onLikeClicked(post)
            },
            onShareClicked = { post ->
              viewModel.onShareClicked(post)
            })


        viewModel.data.observe(this) { posts ->

            binding.postRecyclerView.adapter = adapter
            adapter.submitList(posts)

        }

    }

}

