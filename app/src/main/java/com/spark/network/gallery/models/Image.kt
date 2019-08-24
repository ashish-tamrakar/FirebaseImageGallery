package com.spark.network.gallery.models

class Image {
    var id: String? = null
    var title: String? = null
    var coverPhotoURL: String? = null

    //No argument constructor is needed - DO NOT REMOVE
    constructor() {}

    constructor(title: String, coverPhotoURL: String) {
        this.title = title
        this.coverPhotoURL = coverPhotoURL
    }
}
