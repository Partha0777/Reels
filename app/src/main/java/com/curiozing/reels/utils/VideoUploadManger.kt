package com.curiozing.reels.utils

import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback


class CloudinaryUploader : VideoUploadManger {
    override fun uploadVideo(
        path: String,
        onVideoUploading: (Int) -> Unit,
        onVideoUploadSuccess: () -> Unit,
        onVideoUploadFailure: () -> Unit
    ) {
        MediaManager.get().upload(path).option("resource_type", "video")
            .callback(object : UploadCallback {
                override fun onStart(requestId: String?) {
                }

                override fun onProgress(requestId: String?, bytes: Long, totalBytes: Long) {
                    val percentage = (bytes.toDouble() / totalBytes.toDouble()) * 100
                    onVideoUploading(percentage.toInt())
                }

                override fun onSuccess(requestId: String?, resultData: MutableMap<Any?, Any?>?) {
                    onVideoUploadSuccess()
                }

                override fun onError(requestId: String?, error: ErrorInfo?) {
                    onVideoUploadFailure()
                }

                override fun onReschedule(requestId: String?, error: ErrorInfo?) {}
            }).dispatch()
    }

    override fun updateVideoUrlToDatabase(videoSrc: String) {}
}


interface VideoUploadManger {
    fun uploadVideo(
        path: String,
        onVideoUploading: (Int) -> Unit,
        onVideoUploadSuccess: () -> Unit,
        onVideoUploadFailure: () -> Unit
    )

    fun updateVideoUrlToDatabase(videoSrc: String)

}