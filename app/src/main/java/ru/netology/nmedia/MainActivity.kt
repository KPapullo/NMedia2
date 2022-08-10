package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageButton
import kotlinx.android.synthetic.main.activity_main.view.*
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.dto.resFormat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            published = "21 мая в 18:36",
            likedByMe = false
        )
        with(binding) {
            likeCount?.text = resFormat(post.likes)
            shareCount?.text = resFormat(post.shares)
            viewsCount?.text = resFormat(post.views)

            author.text = post.author
            published.text = post.published
            content.text = post.content

            if (post.likedByMe) {
                like?.setImageResource(R.drawable.ic_liked_24)
            }

            like?.setOnClickListener {
                post.likedByMe = !post.likedByMe
                like.setImageResource(
                    if (post.likedByMe) R.drawable.ic_liked_24 else R.drawable.ic_like_24
                )
                if (post.likedByMe) post.likes++ else post.likes--
                likeCount?.text = resFormat(post.likes)
            }

            share?.setOnClickListener {
                post.shares++
                shareCount?.text = resFormat(post.shares)
            }
        }

    }
}