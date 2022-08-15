package ru.netology.nmedia.dto

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.PostListItemBinding


class PostsAdapter(

    private val onLikeClicked: (Post) -> Unit,
    private val onShareClicked: (Post) -> Unit
) : ListAdapter<Post, PostsAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PostListItemBinding.inflate(
            inflater, parent, false
        )
        return ViewHolder(binding, onLikeClicked, onShareClicked)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
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
            binding.like.setOnClickListener {
                onLikeClicked(post)

                binding.share.setOnClickListener {
                    onShareClicked(post)
                }
            }
        }

        fun bind(post: Post) = with(binding) {



            author.text = post.author
            published.text = post.published
            content.text = post.content

            likeCount.text = resFormat(post.likes)
            shareCount.text = resFormat(post.shares)
            viewsCount.text = resFormat(post.views)

            like.setImageResource(
                if (post.likedByMe) R.drawable.ic_liked_24 else R.drawable.ic_like_24
            )
            like.setOnClickListener {
                onLikeClicked(post)
            }
            share.setOnClickListener {
                onShareClicked(post)
            }
        }
    }

    private object DiffCallback : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post) =
            oldItem.id == newItem.id


        override fun areContentsTheSame(oldItem: Post, newItem: Post) =
            oldItem == newItem

    }
}