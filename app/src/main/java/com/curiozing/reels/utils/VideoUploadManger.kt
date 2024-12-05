package com.curiozing.reels.utils

import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback

class CloudinaryUploader : VideoUploadManger {
    override fun uploadVideo() {
        MediaManager.get().upload("").callback(object :UploadCallback{
            override fun onStart(requestId: String?) {
                TODO("Not yet implemented")
            }

            override fun onProgress(requestId: String?, bytes: Long, totalBytes: Long) {
                TODO("Not yet implemented")
            }

            override fun onSuccess(requestId: String?, resultData: MutableMap<Any?, Any?>?) {
                TODO("Not yet implemented")
            }

            override fun onError(requestId: String?, error: ErrorInfo?) {
                TODO("Not yet implemented")
            }

            override fun onReschedule(requestId: String?, error: ErrorInfo?) {
                TODO("Not yet implemented")
            }


        })
    }

    override fun updateVideoUrlToDatabase() {

    }

}


interface VideoUploadManger {
    fun uploadVideo()
    fun updateVideoUrlToDatabase()

}