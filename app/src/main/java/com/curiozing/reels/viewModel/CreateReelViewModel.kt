package com.curiozing.reels.viewModel

import androidx.lifecycle.ViewModel
import com.curiozing.reels.utils.CloudinaryUploader
import com.curiozing.reels.utils.VideoUploadManger
import kotlinx.coroutines.flow.MutableStateFlow

class CreateReelViewModel : ViewModel() {

    private val videoUploadManger: VideoUploadManger = CloudinaryUploader()

    val progress: MutableStateFlow<Int> = MutableStateFlow(0)

    fun startRecording(path: String) {
        videoUploadManger.uploadVideo(path, { progressValue ->
            progress.value = progressValue
        }, {
        }, {
        })
    }
}