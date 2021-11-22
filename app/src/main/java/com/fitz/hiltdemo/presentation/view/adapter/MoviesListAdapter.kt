package com.fitz.hiltdemo.presentation.view.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fitz.hiltdemo.R
import com.fitz.hiltdemo.presentation.RefreshHandler
import com.fitz.hiltdemo.usecase.model.MovieViewItem
import com.fitz.hiltdemo.usecase.model.MovieViewItem.Companion.SELECTED_MOVIE_ITEM
import timber.log.Timber

class MoviesListAdapter(
    var items: List<MovieViewItem>,
    private val navController: NavController,
    private val refreshHandler: RefreshHandler
): RecyclerView.Adapter<MoviesListAdapter.MovieViewHolder>() {

    class MovieViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val title: TextView = itemView.findViewById(R.id.movie_title)
        private val image: ImageView = itemView.findViewById(R.id.movie_image)
        private val stars: TextView = itemView.findViewById(R.id.movie_stars)
        private val bookmark: ImageView = itemView.findViewById(R.id.bookmark_icon)

        fun bind(movieEntity: MovieViewItem, navController: NavController) {
            title.text = movieEntity.title
            stars.text = movieEntity.movieRating.toString()
            Glide.with(image.context).load(movieEntity.imageURL)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(image)
            itemView.setOnClickListener {
                val bundle = Bundle()
                bundle.putSerializable(SELECTED_MOVIE_ITEM, movieEntity)
                navController.navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
            }
            setBookmarkBackground(movieEntity.saved)
        }

        private fun setBookmarkBackground(filled: Boolean) {
            bookmark.background = if(filled) {
                bookmark.contentDescription = itemView.context.getString(R.string.bookmark_saved)
                AppCompatResources.getDrawable(itemView.context, R.drawable.ic_bookmark_filled)
            } else {
               bookmark.contentDescription = itemView.context.getString(R.string.bookmark_unsaved)
                AppCompatResources.getDrawable(itemView.context, R.drawable.ic_bookmark_empty)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_list_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(items[position], navController)
        if(position == itemCount - 1) {
            Timber.d("requesting more data")
            refreshHandler.requestMoreData()
        }
    }

    override fun getItemCount(): Int = items.size
}