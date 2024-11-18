package com.curiozing.reels.composeUi

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.curiozing.reels.AppKeysAndBaseUrl.IMAGE_BASE_URL
import com.curiozing.reels.viewModel.ReelsViewModel
import kotlin.random.Random


@Composable
fun CreateReel() {
    val configuration = LocalConfiguration
    val density = LocalDensity.current

    val reelsViewModel: ReelsViewModel = viewModel()
    val reelsList = reelsViewModel.reels.collectAsState()

    val reelItemHeight = configuration.current.screenHeightDp / 2.5

    val reelItemHeightInPx = with(density) {
        reelItemHeight.dp.toPx()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (reelsList.value.isNotEmpty()) {
            println("reelItemHeight $reelItemHeightInPx")
            LazyVerticalGrid(columns = GridCells.Fixed(2),
                content = {
                    reelsList.value.forEachIndexed { index, reel ->
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
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(reelItemHeight.dp)
                                    )

                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(reelItemHeight.dp)
                                            .background(
                                                Brush.verticalGradient(
                                                    colors = listOf(
                                                        Color.Transparent,
                                                        Color.Black.copy(alpha = 0.1f),
                                                        Color.Black.copy(alpha = 0.4f),
                                                        Color.Black.copy(alpha = 0.7f),
                                                        Color.Black.copy(alpha = 9f)
                                                    ),
                                                    startY = 0f, // Start from the top
                                                    endY = reelItemHeightInPx // Dynamic height
                                                )
                                            ),
                                        contentAlignment = Alignment.BottomStart
                                    ) {

                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(start = 12.dp, end = 12.dp),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                modifier = Modifier.padding(
                                                    top = 6.dp,
                                                    bottom = 12.dp
                                                )
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.FavoriteBorder,
                                                    contentDescription = "",
                                                    tint = Color.White,
                                                    modifier = Modifier.size(18.dp)
                                                )
                                                Spacer(modifier = Modifier.width(4.dp))
                                                Text(
                                                    text = reel.userInteractions.likeCount.toString(),
                                                    fontSize = 12.sp,
                                                    color = Color.White
                                                )
                                            }
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                modifier = Modifier.padding(
                                                    top = 6.dp,
                                                    bottom = 12.dp
                                                )
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.MailOutline,
                                                    contentDescription = "",
                                                    tint = Color.White,
                                                    modifier = Modifier.size(18.dp)
                                                )
                                                Spacer(modifier = Modifier.width(4.dp))
                                                Text(
                                                    text = reel.userInteractions.commentsCount.toString(),
                                                    fontSize = 12.sp,
                                                    color = Color.White
                                                )
                                            }
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                modifier = Modifier.padding(
                                                    top = 6.dp,
                                                    bottom = 12.dp
                                                )
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.Share,
                                                    contentDescription = "",
                                                    tint = Color.White,
                                                    modifier = Modifier.size(18.dp)
                                                )
                                                Spacer(modifier = Modifier.width(4.dp))
                                                Text(
                                                    text = reel.userInteractions.shareCount.toString(),
                                                    fontSize = 12.sp,
                                                    color = Color.White
                                                )
                                            }

                                        }
                                    }
                                }
                            }
                        }
                    }
                })
        } else {
            CircularProgressIndicator()
        }
    }
}