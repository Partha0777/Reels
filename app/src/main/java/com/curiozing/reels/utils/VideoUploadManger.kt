package com.curiozing.reels.utils

class CloudinaryUploader : VideoUploadManger{
    override fun uploadVideo() {

    }

    override fun updateVideoUrlToDatabase() {

    }

}




interface VideoUploadManger {
    fun uploadVideo()
    fun updateVideoUrlToDatabase()
}