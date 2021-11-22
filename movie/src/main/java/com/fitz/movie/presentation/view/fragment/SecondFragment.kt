package com.fitz.movie.presentation.view.fragment

import android.content.Context
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.fitz.movie.R
import com.fitz.movie.databinding.FragmentSecondBinding
import com.fitz.movie.di.MovieComponentProvider
import com.fitz.movie.presentation.viewmodel.SecondFragmentViewModel
import com.fitz.movie.presentation.viewmodel.ViewModelProviderFactory
import com.fitz.movie.usecase.model.MovieViewItem.Companion.SELECTED_MOVIE_ITEM
import java.lang.IllegalArgumentException
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory
    private val viewModel: SecondFragmentViewModel by viewModels { viewModelFactory }

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    // item being displayed on screen
    private lateinit var movieViewItem: com.fitz.movie.usecase.model.MovieViewItem

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as MovieComponentProvider).movieComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieViewItem = requireArguments().get(SELECTED_MOVIE_ITEM) as com.fitz.movie.usecase.model.MovieViewItem

        (requireActivity() as AppCompatActivity).supportActionBar?.title = movieViewItem.title

        binding.buttonSecond.setOnClickListener {
            toggleDescriptionVisibility()
        }

        binding.descriptionTextView.movementMethod = ScrollingMovementMethod()

        Glide.with(view).load(movieViewItem.imageURL)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_foreground)
            .into(binding.backgroundImageView)

        getAnimatedDrawableAndStart(movieViewItem.saved)

        binding.bookmarkIcon.setOnClickListener {
            movieViewItem.saved = !movieViewItem.saved
            getAnimatedDrawableAndStart(movieViewItem.saved)
            viewModel.saveMovie(movieViewItem)
        }

        binding.descriptionTextView.text = movieViewItem.description
    }

    private fun getAnimatedDrawableAndStart(filled: Boolean) {
        val drawable = if(filled) {
            binding.bookmarkIcon.contentDescription = requireContext().getString(R.string.bookmark_saved)
            AppCompatResources.getDrawable(requireContext(), R.drawable.bookmark_transformation_empty_to_filled)
        } else {
            binding.bookmarkIcon.contentDescription = requireContext().getString(R.string.bookmark_unsaved)
            AppCompatResources.getDrawable(requireContext(), R.drawable.bookmark_transformation_filled_to_empty)
        }

        binding.bookmarkIcon.background = drawable
        (drawable as AnimatedVectorDrawable).start()
    }

    // flips icon upside-down and expands/collapses description text
    private fun toggleDescriptionVisibility() {
        val drawable: Drawable? = when (binding.descriptionTextView.visibility) {
            View.GONE -> {
                binding.descriptionTextView.visibility = View.VISIBLE
                AppCompatResources.getDrawable(requireContext(), R.drawable.ic_dropdown_collapsed)
            }
            View.VISIBLE -> {
                binding.descriptionTextView.visibility = View.GONE
                AppCompatResources.getDrawable(requireContext(), R.drawable.ic_dropdown_expanded)
            }
            else -> {
                throw IllegalArgumentException("This TextView is not in an expected state")
            }
        }
        binding.buttonSecond.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawable, null)
        (drawable as AnimatedVectorDrawable).start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}