package com.curiozing.reels.utils

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback


class CloudinaryUploader : VideoUploadManger {
    override fun uploadVideo(path:String,onVideoUploading : () ->Unit, onVideoUploadSuccess : () ->Unit,onVideoUploadFailure : () ->Unit) {

        MediaManager.get().upload(path)
            .option("resource_type","video")
            .callback(object : UploadCallback {
            override fun onStart(requestId: String?) {
                Log.d("Started Uploading", "")
            }

            override fun onProgress(requestId: String?, bytes: Long, totalBytes: Long) {
                Log.d("onProgress Uploading", "$bytes")

                onVideoUploading()
            }

            override fun onSuccess(requestId: String?, resultData: MutableMap<Any?, Any?>?) {
                Log.d("onProgress Success", "")

                onVideoUploadSuccess()
            }

            override fun onError(requestId: String?, error: ErrorInfo?) {
                Log.d("uload Error", "$error")

                onVideoUploadFailure()
            }

            override fun onReschedule(requestId: String?, error: ErrorInfo?) {}
        }).dispatch()
    }

    override fun updateVideoUrlToDatabase() {}


}


interface VideoUploadManger {
    fun uploadVideo(path:String, onVideoUploading : () ->Unit, onVideoUploadSuccess : () ->Unit,onVideoUploadFailure : () ->Unit)
    fun updateVideoUrlToDatabase()

}