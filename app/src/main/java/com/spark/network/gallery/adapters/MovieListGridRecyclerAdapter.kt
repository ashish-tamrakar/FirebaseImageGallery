package com.spark.network.gallery.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.spark.network.gallery.R
import com.spark.network.gallery.models.Image
import com.spark.network.gallery.viewholder.MovieListViewHolder

class MovieListGridRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listOfMovies = listOf<Image>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MovieListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_gallery_layout, parent, false))
    }

    override fun getItemCount(): Int = listOfMovies.size

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val movieViewHolder = viewHolder as MovieListViewHolder
        movieViewHolder.bindView(listOfMovies[position])
    }

    /**
     * Function to set the movie List.
     * @param listOfMovies - This param holds the list of movies.
     * */
    fun setMovieList(listOfMovies: List<Image>) {
        this.listOfMovies = listOfMovies
        notifyDataSetChanged()
    }
}