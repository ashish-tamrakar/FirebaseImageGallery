package com.spark.network.gallery.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.spark.network.gallery.models.Image
import kotlinx.android.synthetic.main.custom_gallery_layout.view.*


class MovieListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindView(movieModel: Image) {
        itemView.title.text = movieModel.title
        Glide.with(itemView.context)
                .load(movieModel.coverPhotoURL)
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(RequestOptions().placeholder(com.spark.network.gallery.R.drawable.ic_photo))
                .into(itemView.cover)
    }
}