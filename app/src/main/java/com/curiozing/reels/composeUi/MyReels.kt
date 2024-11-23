package com.curiozing.reels.composeUi

import android.annotation.SuppressLint
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.curiozing.reels.AppKeysAndBaseUrl.IMAGE_BASE_URL
import com.curiozing.reels.R
import com.curiozing.reels.model.reels.Reels
import com.curiozing.reels.viewModel.ReelsViewModel
import kotlin.random.Random


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MyReels(navigateTo: () -> Unit) {
    val configuration = LocalConfiguration
    val density = LocalDensity.current

    val reelsViewModel: ReelsViewModel = viewModel()
    val reelsList = reelsViewModel.reels.collectAsState()
    val reelItemHeight = configuration.current.screenHeightDp / 2.5

    val reelItemHeightInPx = with(density) {
        reelItemHeight.dp.toPx()
    }


    Scaffold(
        floatingActionButton = {
            Box(
                modifier = Modifier.padding(bottom = 50.dp)
            ) {
                FloatingActionButton(
                    containerColor = MaterialTheme.colorScheme.primary,
                    shape = CircleShape,
                    onClick = {
                        navigateTo.invoke()
                    }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Create Reel",
                        tint = Color.Black
                    )
                }
            }
        }
    ) {
        if (reelsList.value.isNotEmpty()) {
            ReelsGrid(reelsList.value,reelItemHeight,reelItemHeightInPx)
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,

                ) {
                CircularProgressIndicator()
            }
        }

    }
}

@Composable
fun ReelsGrid(reelsList:List<Reels>,reelItemHeight:Double,reelItemHeightInPx:Float){


    LazyVerticalGrid(columns = GridCells.Fixed(2),
        content = {

            item(span = { GridItemSpan(maxLineSpan) }) {
                Column(
                    modifier = Modifier
                        .padding(top = 20.dp, bottom = 20.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Your Posts!")
                }
            }

            items(reelsList.size) {
                val reel = reelsList[it]
                val randomInt = Random.nextInt(200, 500)

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
                                            Color.Black.copy(alpha = 0.5f),
                                            Color.Black.copy(alpha = 0.7f),
                                            Color.Black.copy(alpha = 9f)
                                        ),
                                        startY = 0f,
                                        endY = reelItemHeightInPx
                                    )
                                ),
                            contentAlignment = Alignment.BottomStart
                        ) {
                            Column {
                                Text(
                                    text = reel.descriptions, maxLines = 2,
                                    lineHeight = 16.sp,
                                    modifier = Modifier.padding(start = 12.dp),
                                    fontSize = 12.sp, color = Color.White
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 12.dp, end = 12.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    UserInteractionItem(
                                        iconId = R.drawable.like_icon,
                                        content = reel.userInteractions.likeCount.toString()
                                    )
                                    UserInteractionItem(
                                        iconId = R.drawable.comment_icon,
                                        content = reel.userInteractions.commentsCount.toString()
                                    )
                                    UserInteractionItem(
                                        iconId = R.drawable.share_icon,
                                        content = reel.userInteractions.shareCount.toString()
                                    )
                                }
                            }
                        }
                    }
                }
            }

        })


}


@Composable
fun UserInteractionItem(iconId: Int, content: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(
            top = 6.dp,
            bottom = 12.dp
        )
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = "",
            tint = Color.White,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = content,
            fontSize = 14.sp,
            color = Color.White
        )
    }
}