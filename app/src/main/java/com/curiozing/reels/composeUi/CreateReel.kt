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
import coil.compose.AsyncImage
import com.curiozing.reels.AppKeysAndBaseUrl.IMAGE_BASE_URL
import com.curiozing.reels.viewModel.ReelsViewModel
import kotlin.random.Random


@Composable
fun CreateReel() {

    val reelsViewModel: ReelsViewModel = viewModel()
    val reelsList =  reelsViewModel.reels.collectAsState()


    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        if (reelsList.value.isNotEmpty()){
            LazyVerticalGrid(columns = GridCells.Fixed(2),
                content = {
                    reelsList.value.forEachIndexed {index,reel ->
                        val randomInt = Random.nextInt(200, 500)
                        item {
                            Column {
                                AsyncImage(model = "$IMAGE_BASE_URL$randomInt/540/960", contentDescription = "reel image")
                              //  Text(text = reel.descriptions)
                            }
                        }
                    }
                })
        }else{
            CircularProgressIndicator()
        }
    }
}