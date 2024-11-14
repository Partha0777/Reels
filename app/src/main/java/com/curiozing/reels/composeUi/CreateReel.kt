package com.curiozing.reels.composeUi

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.curiozing.reels.viewModel.ReelsViewModel
import kotlin.math.log


@Composable
fun CreateReel() {
    var reelsViewModel: ReelsViewModel = viewModel()
    Log.d("Print reels -->", reelsViewModel.reels.value.toString())

}