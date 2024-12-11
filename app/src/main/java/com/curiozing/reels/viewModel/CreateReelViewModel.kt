package com.curiozing.reels.viewModel

import androidx.lifecycle.ViewModel
import com.curiozing.reels.utils.CloudinaryUploader
import com.curiozing.reels.utils.VideoUploadManger

class CreateReelViewModel : ViewModel() {

    private val videoUploadManger: VideoUploadManger = CloudinaryUploader()

    fun startRecording(path: String) {
        videoUploadManger.uploadVideo(path, {

        }, {

        }, {

        })
    }
}