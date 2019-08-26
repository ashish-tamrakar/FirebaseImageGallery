package com.spark.network.gallery.helpers

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.spark.network.gallery.R

object Config {
    const val TITLE_EMPTY_MESSAGE = "Title is required !"
    const val IMAGE_URL_NULL_MESSAGE = "Choose an image !"
    const val IMAGE_ADD_SUCCESS_MSG = "Image added successfully !"
    const val DATABASE_REFERENCE = "images"
    const val STORAGE_PATH = "cover_photo/"
    const val IMAGE_ADDING_MESSAGE = "Adding image please wait...."

    //show custom success toast
    @SuppressLint("InflateParams")
    fun showToast(message: String, context: Context) {
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.custom_toast_layout, null)
        val toast = Toast(context)
        toast.duration = Toast.LENGTH_LONG
        toast.view = view
        (view.findViewById<View>(R.id.message) as TextView).text = message
        toast.show()
    }

    //check internet availability
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}
