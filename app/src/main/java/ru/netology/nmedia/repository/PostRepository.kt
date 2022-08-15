package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import ru.netology.nmedia.dto.Post
import java.sql.RowId

interface PostRepository {
    val data: LiveData<List<Post>>
    fun like(postId: Long)
    fun share(postId: Long)
}
