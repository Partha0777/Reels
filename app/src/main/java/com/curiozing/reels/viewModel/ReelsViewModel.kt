package com.curiozing.reels.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.curiozing.reels.data.api.ApiService
import com.curiozing.reels.model.reels.Reels
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ReelsViewModel : ViewModel() {

    private var _reenlist:MutableState<List<Reels>> = mutableStateOf(listOf())

    var reels: State<List<Reels>> = _reenlist

    init {
        getReelsList()
    }

    private fun getReelsList(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val reelsData = ApiService.reelsAPI.getReels().await()
                _reenlist.value = reelsData
            }
        }
    }
}