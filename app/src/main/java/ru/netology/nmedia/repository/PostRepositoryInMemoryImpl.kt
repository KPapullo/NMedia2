package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.dto.resFormat

class PostRepositoryInMemoryImpl : PostRepository {
    private var posts
        get() = checkNotNull(data.value)
        set(value) {
            data.value = value
        }

    override val data: MutableLiveData<List<Post>>

    init {
        val initialPosts = List(1000) { index ->

            Post(
                id = index + 1L,
                author = "Нетология. Университет интернет-профессий будущего",
                content = "Post content #${index + 1}",
                published = "23 сентября в 10:12",
                likedByMe = false,
                likes = 999_999,
                shares = 5,
                views = 6

            )
        }

        data = MutableLiveData(initialPosts)
    }


    override fun like(postId: Long) {


        posts = posts.map { post ->

            val likedCount = if (!post.likedByMe) post.likes + 1 else post.likes - 1

            if (!post.likedByMe) post.likes + 1 else post.likes - 1
            if (post.id == postId) post.copy(likedByMe = !post.likedByMe, likes = likedCount)
            else post
        }
    }

    override fun share(postId: Long) {

        posts = posts.map { post ->

            if (post.id == postId) post.copy(shares = post.shares + 1)
            else post

        }
    }
}

