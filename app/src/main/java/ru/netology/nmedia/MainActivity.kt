package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.dto.resFormat
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity(){

    private val viewModel: PostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.data.observe(this) { post ->
            binding.render(post)
        }

        binding.like?.setOnClickListener {
            viewModel.onLike()

        }

        binding.share?.setOnClickListener {
            viewModel.onShare()
        }


    }


private fun ActivityMainBinding.render(post: Post) {
    author.text = post.author
    published.text = post.published
    content.text = post.content

    likeCount?.text = resFormat(post.likes)
    shareCount?.text = resFormat(post.shares)
    viewsCount?.text = resFormat(post.views)

  like?.setImageResource(getLikeIconResId(post.likedByMe))}

@DrawableRes
private fun getLikeIconResId(liked:Boolean) =
    if (liked) R.drawable.ic_liked_24 else R.drawable.ic_like_24

}

