package ru.netology.nmedia.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.adapter.PostInteractionListener
import ru.netology.nmedia.data.impl.SharedPrefsPostRepository
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.util.SingleLifeEvent

class PostViewModel(
    application: Application
) : AndroidViewModel(application), PostInteractionListener {


    private val repository: PostRepository = SharedPrefsPostRepository(application)

    val data by repository::data

    val sharePostContent = SingleLifeEvent<String>()
    val navigateToPostContentScreenEvent = SingleLifeEvent<String>()

    val playVideo = SingleLifeEvent<String>()

    val currentPost = MutableLiveData<Post?>(null)

    fun onSaveButtonClicked(content: String) {
        if (content.isBlank()) return

        val post = currentPost.value?.copy(
            content = content
        ) ?: Post(
            id = PostRepository.NEW_POST_ID,
            author = "Me",
            content = content,
            published = "Today"
        )
        repository.save(post)
        currentPost.value = null
    }

    fun onAddClicked() {
        navigateToPostContentScreenEvent.call()
    }

    fun onCloseButtonClicked() {
        currentPost.value = null
    }

    // region PostInteractionListener
    override fun onLikeClicked(post: Post) {
        repository.like(post.id)
    }

    override fun onShareClicked(post: Post) {
       // repository.share(post.id)
        sharePostContent.value = post.content
        repository.share(post.id)
    }

    override fun onRemoveClicked(post: Post) {
        repository.delete(post.id)
    }

    override fun onEditClicked(post: Post) {
        currentPost.value = post
            navigateToPostContentScreenEvent.value = post.content

    }

    override fun onPlayVideoClicked(post: Post) {
        val url: String = requireNotNull(post.video) {
            "Url must not be null"
        }
        playVideo.value = url
    }
}