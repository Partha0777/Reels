package com.curiozing.reels.utils

import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback

class CloudinaryUploader : VideoUploadManger {
    override fun uploadVideo(path:String,onVideoUploading : () ->Unit, onVideoUploadSuccess : () ->Unit,onVideoUploadFailure : () ->Unit) {

        MediaManager.get().upload(path).callback(object : UploadCallback {
            override fun onStart(requestId: String?) {}

            override fun onProgress(requestId: String?, bytes: Long, totalBytes: Long) {
                onVideoUploading()
            }

            override fun onSuccess(requestId: String?, resultData: MutableMap<Any?, Any?>?) {
                onVideoUploadSuccess()
            }

            override fun onError(requestId: String?, error: ErrorInfo?) {
                onVideoUploadFailure()
            }

            override fun onReschedule(requestId: String?, error: ErrorInfo?) {}
        })
    }

    override fun updateVideoUrlToDatabase() {}


}


interface VideoUploadManger {
    fun uploadVideo(path:String, onVideoUploading : () ->Unit, onVideoUploadSuccess : () ->Unit,onVideoUploadFailure : () ->Unit)
    fun updateVideoUrlToDatabase()

}