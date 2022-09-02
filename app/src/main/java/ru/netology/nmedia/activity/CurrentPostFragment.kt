package ru.netology.nmedia.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.net.Uri.*
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CurrentPostFragmentBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.dto.resFormat
import ru.netology.nmedia.viewmodel.PostViewModel


class CurrentPostFragment : Fragment() {

    private val viewModel: PostViewModel by activityViewModels()
    private val args by navArgs<CurrentPostFragmentArgs>()

    private lateinit var currentPost: Post

    private val Fragment.packageManager
        get() = activity?.packageManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return CurrentPostFragmentBinding.inflate(
            layoutInflater, container, false
        ).also { binding ->
            with(binding) {

                currentPost = viewModel.data.value?.let { listPost ->
                    listPost.firstOrNull {
                        it.id == args.idCurrentPost
                    }
                } as Post
                render(currentPost)


                viewModel.data.observe(viewLifecycleOwner) { listPost ->
                    if (listPost.none { it.id == args.idCurrentPost } ) {
                        return@observe
                    }
                    currentPost = listPost.firstOrNull {
                        it.id == args.idCurrentPost
                    } as Post
                    render(currentPost)
                }

                viewModel.sharePostContent.observe(viewLifecycleOwner) { postContent ->
                    val intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, postContent)
                        type = "text/plain"
                    }

                    val shareIntent = Intent.createChooser(
                        intent, getString(R.string.chooser_share_post)
                    )
                    startActivity(shareIntent)
                }

                viewModel.playVideo.observe(viewLifecycleOwner) { videoUrl ->
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl))
                    if (packageManager?.let { intent.resolveActivity(it) } != null) {
                        startActivity(intent)
                    }
                }

                viewModel
                    .navigateToPostContentScreenEvent
                    .observe(viewLifecycleOwner) { initialContent ->
                        val direction =
                            CurrentPostFragmentDirections.currentPostFragmentToPostContentFragment(
                                initialContent, PostContentFragment.REQUEST_CURRENT_POST_KEY
                            )
                        findNavController().navigate(direction)
                    }

                setFragmentResultListener(
                    requestKey = PostContentFragment.REQUEST_CURRENT_POST_KEY
                ) { requestKey, bundle ->
                    if (requestKey != PostContentFragment.REQUEST_CURRENT_POST_KEY) return@setFragmentResultListener
                    val newPostContent = bundle.getString(
                        PostContentFragment.RESULT_KEY
                    ) ?: return@setFragmentResultListener
                    viewModel.onSaveButtonClicked(newPostContent)
                }

                likeIcon.setOnClickListener {
                    viewModel.onLikeClicked(currentPost)
                }

                shareIcon.setOnClickListener {
                    viewModel.onShareClicked(currentPost)
                }

                binding.videoBanner.setOnClickListener {
                    viewModel.onPlayVideoClicked(currentPost)
                }
                binding.playVideo.setOnClickListener {
                    viewModel.onPlayVideoClicked(currentPost)
                }

                val popupMenu by lazy {
                    PopupMenu(context, binding.menu).apply {
                        inflate(R.menu.options_post)
                        setOnMenuItemClickListener { menuItem ->
                            when (menuItem.itemId) {
                                R.id.remove -> {
                                    viewModel.onRemoveClicked(currentPost)
                                    findNavController().popBackStack()
                                    true
                                }
                                R.id.edit -> {
                                    viewModel.onEditClicked(currentPost)
                                    true
                                }
                                else -> false
                            }
                        }
                    }
                }

                binding.menu.setOnClickListener {
                    popupMenu.show()
                }


            }
        }.root
    }

    private fun CurrentPostFragmentBinding.render(post: Post) {
        authorName.text = post.author
        content.text = post.published
        content.text = post.content
        likeIcon.text = resFormat(post.likes)
        shareIcon.text = resFormat(post.shares)
        likeIcon.isChecked = post.likedByMe
        content.movementMethod = ScrollingMovementMethod()
        videoGroup.isVisible = post.video != null
    }


}

