package com.curiozing.reels.utils

import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback

class CloudinaryUploader : VideoUploadManger {
    override fun uploadVideo(path:String) {

        MediaManager.get().upload(path).callback(object : UploadCallback {
            override fun onStart(requestId: String?) {}

            override fun onProgress(requestId: String?, bytes: Long, totalBytes: Long) {}

            override fun onSuccess(requestId: String?, resultData: MutableMap<Any?, Any?>?) {}

            override fun onError(requestId: String?, error: ErrorInfo?) {}

            override fun onReschedule(requestId: String?, error: ErrorInfo?) {}
        })
    }

    override fun updateVideoUrlToDatabase() {}
    override fun onUploading() {
        TODO("Not yet implemented")
    }

    override fun onUploadingSuccess() {
        TODO("Not yet implemented")
    }

    override fun onUploadingFailure() {
        TODO("Not yet implemented")
    }

}


interface VideoUploadManger {
    fun uploadVideo(path:String)
    fun updateVideoUrlToDatabase()
    fun onUploading()
    fun onUploadingSuccess()
    fun onUploadingFailure()

}