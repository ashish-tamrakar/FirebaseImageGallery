package com.spark.network.gallery.helpers

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.spark.network.gallery.activities.MainActivity
import com.spark.network.gallery.adapters.MovieListGridRecyclerAdapter
import com.spark.network.gallery.models.Image
import com.wang.avi.AVLoadingIndicatorView
import java.util.*

class ImageDatabaseHelper {
    private var databaseReference: DatabaseReference? = null
    private var storageReference: StorageReference? = null

    // add new Image into the firebase database
    fun add(context: Context, title: String, coverPhotoURL: Uri) {
        databaseReference = FirebaseDatabase.getInstance().getReference(Config.DATABASE_REFERENCE)
        Config.showToast(Config.IMAGE_ADDING_MESSAGE, context)
        databaseReference!!.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val uniqueKey = databaseReference!!.push().key
                storageReference = FirebaseStorage.getInstance().reference.child(uniqueKey!!).child(Config.STORAGE_PATH + coverPhotoURL.lastPathSegment!!)
                val storageTask = storageReference!!.putFile(coverPhotoURL)
                val uriTask = storageTask.continueWithTask { taskSnapshot ->
                    if (!taskSnapshot.isSuccessful) {
                        throw taskSnapshot.exception!!
                    }
                    storageReference!!.downloadUrl
                }.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val downloadURi = task.result
                        val image = Image(title, downloadURi!!.toString())
                        databaseReference!!.child(uniqueKey).setValue(image)
                        Config.showToast(Config.IMAGE_ADD_SUCCESS_MSG, context)
                    }
                    val intent = Intent(context, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    context.startActivity(intent)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }

    // check if there is any image in the database
    fun anyImageExists(dataSnapshot: DataSnapshot): Boolean {
        return dataSnapshot.childrenCount > 0
    }

    // get all the images from the database
    fun all(checkInternet: TextView, check: TextView, loader: AVLoadingIndicatorView, images: ArrayList<Image>, context: Context, imageGallery: RecyclerView) {
        databaseReference = FirebaseDatabase.getInstance().getReference(Config.DATABASE_REFERENCE)
        loader.visibility = View.VISIBLE
        if (Config.isNetworkAvailable(context)) {
            databaseReference!!.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (anyImageExists(dataSnapshot)) {
                        images.clear()
                        for (snapshot in dataSnapshot.children) {
                            val image = snapshot.getValue(Image::class.java)
                            image!!.id = snapshot.key
                            images.add(image)
                        }
                        val movieListAdapter = MovieListGridRecyclerAdapter()
                        imageGallery.adapter = movieListAdapter
                        movieListAdapter.setMovieList(images)
                        loader.visibility = View.GONE
                        check.visibility = View.GONE
                    } else {
                        imageGallery.visibility = View.GONE
                        check.visibility = View.VISIBLE
                        loader.visibility = View.GONE
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {

                }

            })
            checkInternet.visibility = View.GONE
        } else {
            checkInternet.visibility = View.VISIBLE
            loader.visibility = View.GONE
            imageGallery.visibility = View.GONE
            check.visibility = View.GONE
        }
    }
}
