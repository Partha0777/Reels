package com.curiozing.reels.composeUi

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.curiozing.reels.AppKeysAndBaseUrl.IMAGE_BASE_URL
import com.curiozing.reels.viewModel.ReelsViewModel
import kotlin.random.Random


@Composable
fun CreateReel() {

    val reelsViewModel: ReelsViewModel = viewModel()
    val reelsList =  reelsViewModel.reels.collectAsState()
    var containerHeight by remember { mutableStateOf(1) }
    val configuration = LocalConfiguration
    val reelItemHeight = configuration.current.screenHeightDp / 2.5

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        if (reelsList.value.isNotEmpty()){
            LazyVerticalGrid(columns = GridCells.Fixed(2),
                content = {
                    reelsList.value.forEachIndexed {index,reel ->
                        val randomInt = Random.nextInt(200, 500)
                        item {
                            Column {

                                Box(
                                    modifier = Modifier.fillMaxSize()
                                ) {

                                    AsyncImage(
                                        model = "$IMAGE_BASE_URL$randomInt/540/960",
                                        contentDescription = "reel image",
                                        contentScale = ContentScale.FillWidth,
                                        modifier = Modifier.fillMaxWidth().height(reelItemHeight.dp)
                                    )

                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth().height(reelItemHeight.dp)
                                            .onSizeChanged { size -> containerHeight = size.height }
                                            .background(
                                                Brush.verticalGradient(
                                                    colors = listOf(
                                                        Color.Transparent,
                                                        Color.Black.copy(alpha = 0.1f),
                                                        Color.Black.copy(alpha = 0.5f),
                                                        Color.Black.copy(alpha = 0.7f),
                                                        Color.Black.copy(alpha = 1f)
                                                    ),
                                                    startY = 0f, // Start from the top
                                                    endY = containerHeight.toFloat() // Dynamic height
                                                )
                                            )
                                    )
                                }


                            }
                        }
                    }
                })
        }else{
            CircularProgressIndicator()
        }
    }
}