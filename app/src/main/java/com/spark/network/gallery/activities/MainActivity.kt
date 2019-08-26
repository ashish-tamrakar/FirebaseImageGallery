package com.spark.network.gallery.activities


import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import com.spark.network.gallery.R
import com.spark.network.gallery.helpers.ImageDatabaseHelper
import com.spark.network.gallery.helpers.GridItemDecoration
import com.spark.network.gallery.models.Image
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(){
    private var images: ArrayList<Image> = ArrayList()
    private var imageDatabaseHelper: ImageDatabaseHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar as Toolbar)
        imageDatabaseHelper = ImageDatabaseHelper()

        recyclerViewMovies.layoutManager = GridLayoutManager(this,2)

        //This will for default android divider
        recyclerViewMovies.addItemDecoration(GridItemDecoration(10, 2))

        imageDatabaseHelper?.all(check_connectivity, image_check_availability, loader, images, this, recyclerViewMovies)
        add_image?.setOnClickListener {
            startActivity(Intent(this@MainActivity, AddImageActivity::class.java))
        }
    }
}
