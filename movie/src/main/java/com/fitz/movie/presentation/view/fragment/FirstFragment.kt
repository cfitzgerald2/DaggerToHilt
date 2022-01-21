package com.fitz.movie.presentation.view.fragment

import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import com.fitz.movie.R
import com.fitz.movie.databinding.FragmentFirstBinding
import com.fitz.movie.presentation.RefreshHandler
import com.fitz.movie.presentation.view.adapter.MoviesListAdapter
import com.fitz.movie.presentation.viewmodel.FirstFragmentViewModel
import com.fitz.movie.usecase.model.RepositoryResult
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class FirstFragment : Fragment(), RefreshHandler, DialogInterface.OnClickListener {

    private val viewModel: FirstFragmentViewModel by viewModels()

    @Inject
    lateinit var moviesListAdapter: MoviesListAdapter

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

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

        binding.moviesList.adapter = moviesListAdapter

        viewModel.moviesListLiveData.observe(viewLifecycleOwner) {
            val originalSize = moviesListAdapter.items.size
            if(moviesListAdapter.items != it.list.toList()) {
                moviesListAdapter.items = it.list.toList()
            }
            when (it.dataState) {
                RepositoryResult.DataOperation.LOADED -> {
                    moviesListAdapter.notifyDataSetChanged()
                }
                RepositoryResult.DataOperation.ADDED -> {
                    moviesListAdapter.notifyItemRangeInserted(originalSize, moviesListAdapter.itemCount - originalSize)
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
                    Snackbar.make(binding.root, it.errorMessage, Snackbar.LENGTH_LONG).show()
                    it.errorMessage = R.string.loading_error_still_present
                }
                RepositoryResult.DataOperation.NO_MORE_DATA -> {
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

        // persist search string through configuration changes
        if(viewModel.searchString.isNotBlank()) {
            searchView.onActionViewExpanded()
            searchView.isIconified = false
            searchView.clearFocus()
            searchView.setQuery(viewModel.searchString, false)
        }

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

        val filter = menu.findItem(R.id.filter)
        filter.setOnMenuItemClickListener {
            AlertDialog.Builder(requireContext())
                .setPositiveButton(requireContext().getString(viewModel.getPositiveButton()), this)
                .setNegativeButton(viewModel.getNegativeButton(), this)
                .setTitle(requireActivity().getString(viewModel.getDialogTitle()))
                .create()
                .show()
            true
        }
    }

    override fun requestMoreData() {
        viewModel.requestMoreData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(dialogIntergace: DialogInterface, item: Int) {
        dialogIntergace.cancel()
        if(item == AlertDialog.BUTTON_POSITIVE) {
            viewModel.getSavedMovies()
        } else if(item == AlertDialog.BUTTON_NEGATIVE) {
            viewModel.clearAllFilters()
        }
    }
}