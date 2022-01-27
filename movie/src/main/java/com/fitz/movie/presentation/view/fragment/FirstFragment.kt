package com.fitz.movie.presentation.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.fitz.movie.R
import com.fitz.movie.databinding.FragmentFirstBinding
import com.fitz.movie.di.MovieComponentProvider
import com.fitz.movie.presentation.view.adapter.MoviesListAdapter
import com.fitz.movie.presentation.viewmodel.FirstFragmentViewModel
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: FirstFragmentViewModel by viewModels { viewModelFactory }

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as MovieComponentProvider).movieComponent.inject(this)
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

        setHasOptionsMenu(true)

        viewModel.scrollToTopLiveData.observe(viewLifecycleOwner) { shouldScrollToTop ->
            if(shouldScrollToTop) {
                binding.moviesList.scrollToPosition(0)
            }
        }

        val moviesListAdapter = MoviesListAdapter(mutableListOf(), findNavController(), viewModel)
        binding.moviesList.adapter = moviesListAdapter

        viewModel.moviesListLiveData.observe(viewLifecycleOwner) {
            val originalSize = moviesListAdapter.items.size
            if(moviesListAdapter.items != it.list.toList()) {
                moviesListAdapter.items = it.list.toList()
            }
            when (it.dataState) {
                com.fitz.movie.usecase.model.RepositoryResult.DataOperation.LOADED -> {
                    moviesListAdapter.notifyDataSetChanged()
                }
                com.fitz.movie.usecase.model.RepositoryResult.DataOperation.ADDED -> {
                    moviesListAdapter.notifyItemRangeInserted(originalSize, moviesListAdapter.itemCount - originalSize)
                }
                com.fitz.movie.usecase.model.RepositoryResult.DataOperation.DELETED-> {
                    moviesListAdapter.notifyItemRemoved(it.editedIndex)
                }
                com.fitz.movie.usecase.model.RepositoryResult.DataOperation.EDITED -> {
                    moviesListAdapter.notifyItemChanged(it.editedIndex)
                }
                com.fitz.movie.usecase.model.RepositoryResult.DataOperation.SORTED -> {
                    moviesListAdapter.notifyItemRangeChanged(0, moviesListAdapter.itemCount - 1)
                }
                com.fitz.movie.usecase.model.RepositoryResult.DataOperation.ERROR -> {
                    Snackbar.make(binding.root, it.errorMessage, Snackbar.LENGTH_LONG).show()
                    it.errorMessage = R.string.loading_error_still_present
                }
                com.fitz.movie.usecase.model.RepositoryResult.DataOperation.NO_MORE_DATA -> {
                    Snackbar.make(binding.root, it.errorMessage, Snackbar.LENGTH_LONG).show()
                    it.errorMessage = R.string.no_more_data_error_still_present
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.search_menu, menu)

        val searchMenuItem = menu.findItem(R.id.search)
        val searchView: SearchView = searchMenuItem.actionView as SearchView

        searchView.onActionViewExpanded()
        searchView.isIconified = false
        searchView.clearFocus()
        searchView.setQuery(viewModel.searchString, false)

        searchView.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    viewModel.searchForData(query)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    viewModel.searchForData(newText)
                    return true
                }
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}