package com.curiozing.reels.utils

import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class VideoUploadManger {

    val storage = FirebaseStorage.getInstance()
    val storageRef: StorageReference = storage.reference

    fun uploadVideo(){

    }

    fun updateVideoUrlToDatabase(){


    }
}