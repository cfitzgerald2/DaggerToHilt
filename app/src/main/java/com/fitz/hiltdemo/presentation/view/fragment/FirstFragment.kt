package com.fitz.hiltdemo.presentation.view.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fitz.hiltdemo.R
import com.fitz.hiltdemo.app.DemoApplication
import com.fitz.hiltdemo.databinding.FragmentFirstBinding
import com.fitz.hiltdemo.usecase.model.MovieResult
import com.fitz.hiltdemo.presentation.view.adapter.MoviesListAdapter
import com.fitz.hiltdemo.presentation.viewmodel.FirstFragmentViewModel
import com.fitz.hiltdemo.presentation.viewmodel.ViewModelProviderFactory
import com.fitz.hiltdemo.usecase.model.RepositoryResult
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_first.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    @Inject lateinit var viewModelFactory: ViewModelProviderFactory
    private val viewModel: FirstFragmentViewModel by viewModels { viewModelFactory }

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as DemoApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val moviesListAdapter = MoviesListAdapter(mutableListOf(), findNavController())
        movies_list.adapter = moviesListAdapter

        viewModel.moviesListLiveData.observe(viewLifecycleOwner) {
            val originalSize = moviesListAdapter.itemCount
            moviesListAdapter.items = it.list
            when (it.dataState) {
                RepositoryResult.DataOperation.LOADED -> {
                    moviesListAdapter.notifyItemRangeInserted(0, moviesListAdapter.itemCount - 1)
                }
                RepositoryResult.DataOperation.ADDED -> {
                    moviesListAdapter.notifyItemRangeInserted(originalSize, moviesListAdapter.itemCount - originalSize - 1)
                }
                RepositoryResult.DataOperation.DELETED-> {
                    moviesListAdapter.notifyItemRemoved(it.editedIndex)
                }
                RepositoryResult.DataOperation.EDITED -> {
                    moviesListAdapter.notifyItemChanged(it.editedIndex)
                }
                RepositoryResult.DataOperation.SORTED -> {
                    moviesListAdapter.notifyItemRangeChanged(0, moviesListAdapter.itemCount - 1)
                }
                RepositoryResult.DataOperation.ERROR -> {
                    Snackbar.make(binding.root, R.string.loading_error_string, Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}