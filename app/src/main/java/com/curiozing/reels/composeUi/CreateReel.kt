package com.curiozing.reels.composeUi

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.curiozing.reels.viewModel.ReelsViewModel


@Composable
fun CreateReel() {

    val reelsViewModel: ReelsViewModel = viewModel()
    val reelsList =  reelsViewModel.reels.collectAsState()


    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        if (reelsList.value.isNotEmpty()){
            LazyVerticalGrid(columns = GridCells.Fixed(2),
                content = {
                    reelsList.value.forEach {reel ->
                        item {
                            Column {

                                Text(text = reel.descriptions)
                            }
                        }
                    }
                })
        }else{
            CircularProgressIndicator()
        }
    }
}