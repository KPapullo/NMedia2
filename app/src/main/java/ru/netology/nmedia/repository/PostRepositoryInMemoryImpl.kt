package ru.netology.nmedia.repository

import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post


class PostRepositoryInMemoryImpl : PostRepository {

    private var nextId = GENERATED_POSTS_AMOUNT.toLong()

    private val posts
        get() = checkNotNull(data.value) {
            "data value should not be null"
        }

    override val data = MutableLiveData(
        List(PostRepositoryInMemoryImpl.Companion.GENERATED_POSTS_AMOUNT) { index ->

            Post(
                id = index + 1L,
                author = "Нетология. Университет интернет-профессий будущего",
                content = "Post content #${index + 1}",
                published = "23 сентября в 10:12",
                likedByMe = false,
                likes = 999_999,
                shares = 5,
                views = 6,
                video = "https://www.youtube.com/watch?v=WhWc3b3KhnY"

            )
        }
    )

    override fun like(postId: Long) {

        data.value = posts.map { post ->

            val likedCount = if (!post.likedByMe) post.likes + 1 else post.likes - 1

            if (!post.likedByMe) post.likes + 1 else post.likes - 1
            if (post.id == postId) post.copy(likedByMe = !post.likedByMe, likes = likedCount)
            else post
        }
    }

    override fun share(postId: Long) {

        data.value = posts.map { post ->

            if (post.id == postId) post.copy(shares = post.shares + 1)
            else post

        }
    }

    override fun delete(postId: Long) {
        data.value = posts.filter { it.id != postId } // data.value
    }

    override fun save(post: Post) {
        if (post.id == PostRepository.NEW_POST_ID) insert(post) else update(post)
    }

    private fun insert(post: Post) {
        data.value = listOf(
            post.copy(id = ++nextId)
        ) + posts
    }

    private fun update(post: Post) {
        data.value = posts.map {
            if (it.id == post.id) post else it
        }
    }

    private companion object {
        const val GENERATED_POSTS_AMOUNT = 1000
    }

}

