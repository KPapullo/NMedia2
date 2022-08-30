package ru.netology.nmedia.dto

data class Post(
    val id: Long,
    val author: String,
    var content: String,
    val published: String,
    var likes: Int = 0,
    var likedByMe: Boolean = false,
    var shares: Int = 1,
    var views: Int = 5,
    val video: String? = null

)

