package com.fitz.hiltdemo.presentation.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fitz.hiltdemo.R
import com.fitz.hiltdemo.usecase.model.MovieViewItem

class MoviesListAdapter(var items: List<MovieViewItem>, private val navController: NavController): RecyclerView.Adapter<MoviesListAdapter.MovieViewHolder>() {

    class MovieViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val title: TextView = itemView.findViewById(R.id.movie_title)
        private val image: ImageView = itemView.findViewById(R.id.movie_image)
        private val stars: TextView = itemView.findViewById(R.id.movie_stars)

        fun bind(movieEntity: MovieViewItem, navController: NavController) {
            title.text = movieEntity.title
            stars.text = movieEntity.movieRating.toString()
            Glide.with(image.context).load(movieEntity.imageURL)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(image)
            itemView.setOnClickListener {
                navController.navigate(R.id.action_FirstFragment_to_SecondFragment)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_list_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(items[position], navController)
    }

    override fun getItemCount(): Int = items.size
}