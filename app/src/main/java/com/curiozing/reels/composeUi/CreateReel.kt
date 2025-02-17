package com.curiozing.reels.composeUi

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.ripple
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.curiozing.reels.ui.theme.Orange300
import com.curiozing.reels.ui.theme.Orange400
import com.curiozing.reels.ui.theme.Orange50
import com.curiozing.reels.viewModel.CreateReelViewModel


@Composable
fun CreateReel() {

    val context = LocalContext.current
    var permissionsGranted by remember { mutableStateOf(false) }
    val permissions = Manifest.permission.CAMERA
    val createReelViewModel: CreateReelViewModel = viewModel()
    var tfPostMessage by remember {
        mutableStateOf(TextFieldValue(text = ""))
    }

    val localConfiguration = LocalConfiguration.current

    val videoRecordRequestLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult(),
            onResult = {
                if (it.resultCode == Activity.RESULT_OK) {
                    val datPath = it.data?.data
                    datPath?.path?.let { _ ->
                        getFilePathFromUri(context, it.data?.data!!)?.let { path ->
                            createReelViewModel.videoUri.value = path
                            //Need to call below code for upload the video
                        }
                    }
                }
            })

    fun recordVideo() {
        val videoIntent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
        videoRecordRequestLauncher.launch(videoIntent)
    }

    val permissionRequestLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission(),
            onResult = { isGrandad ->
                permissionsGranted = isGrandad
                if (permissionsGranted) {
                    recordVideo()
                }
            })

    LaunchedEffect(Unit) {
        permissionsGranted = ContextCompat.checkSelfPermission(
            context, permissions
        ) == PackageManager.PERMISSION_GRANTED
    }

    if (createReelViewModel.videoUri.collectAsState().value.isNotEmpty()) {
        Spacer(modifier = Modifier.height(80.dp))
        when (createReelViewModel.progress.collectAsState().value) {
            0 -> {
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxHeight()
                        .background(color = Color.White)
                ) {
                    Column {
                        Box(
                            modifier = Modifier
                                .height(localConfiguration.screenHeightDp.div(2).dp)
                                .background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(
                                            Orange50, Orange300
                                        )
                                    )
                                )
                        ) {
                            VideoPlayer(src = createReelViewModel.videoUri.value)
                        }
                        Spacer(modifier = Modifier.height(40.dp))
                        BottomOutlineTextField(
                            placeholder = "Write your message here...", value = tfPostMessage
                        ) {
                            tfPostMessage =
                                tfPostMessage.copy(
                                    text = it.text,
                                    selection = TextRange(it.text.length)
                                )
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Box(modifier = Modifier.padding(horizontal = 20.dp))
                        {
                            Text(
                                text = "Add popular #",
                                fontSize = 16.sp,
                                color = Color.DarkGray,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        LazyRow(content = {
                            items(hashtags.size) {
                                Box(modifier = Modifier
                                    .padding(start = 12.dp)
                                    .clip(shape = RoundedCornerShape(50))
                                    .clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = ripple(
                                            bounded = true,
                                        )
                                    ) {
                                        val currentText = tfPostMessage.text + " ${hashtags[it]}"
                                        tfPostMessage = tfPostMessage.copy(
                                            text = currentText,
                                            selection = TextRange(currentText.length),
                                            composition = TextRange(currentText.length)
                                        )
                                    }
                                    .background(color = Orange400)
                                    .padding(horizontal = 12.dp, vertical = 8.dp)) {
                                    Text(text = hashtags[it], fontSize = 14.sp, color = Color.White)
                                }
                            }
                        })
                    }
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 20.dp),
                        onClick = {
                            createReelViewModel.startRecording()
                        }) {
                        Text(text = "Post", fontSize = 14.sp, color = Color.White)
                    }

                }
            }

            100 -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "",
                        tint = Orange400,
                        modifier = Modifier.size(100.dp)
                    )
                    Text(text = "Successfully Posted!")
                }
            }

            else -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Uploading ${createReelViewModel.progress.collectAsState().value}%")
                }
            }
        }

    } else {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Box(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(20))
                        .background(MaterialTheme.colorScheme.primary)
                        .clickable(onClick = {
                            if (permissionsGranted) {
                                recordVideo()
                            } else {
                                permissionRequestLauncher.launch(permissions)
                            }
                        },
                            indication = ripple(bounded = true, color = Color.Gray),
                            interactionSource = remember { MutableInteractionSource() })
                        .padding(start = 20.dp, end = 20.dp, top = 80.dp, bottom = 80.dp)

                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Icon(
                            imageVector = Icons.Filled.PlayArrow,
                            contentDescription = "",
                            Modifier.size(50.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(text = "Create Reel")
                    }
                }
                Box(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(20))
                        .background(MaterialTheme.colorScheme.primary)
                        .clickable(onClick = {
                        },
                            indication = ripple(bounded = true, color = Color.Gray),
                            interactionSource = remember { MutableInteractionSource() })
                        .padding(start = 20.dp, end = 20.dp, top = 80.dp, bottom = 80.dp)

                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "",
                            Modifier.size(50.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(text = "Create Post")
                    }
                }
            }
        }

    }
}

private fun getFilePathFromUri(context: Context, uri: Uri): String? {
    val projection = arrayOf(MediaStore.MediaColumns.DATA)
    val cursor = context.contentResolver.query(uri, projection, null, null, null)
    if (cursor != null) {
        val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
        cursor.moveToFirst()
        val filePath = cursor.getString(columnIndex)
        cursor.close()
        return filePath
    }
    return null
}

@Composable
fun BottomOutlineTextField(
    placeholder: String,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit
) {
    val indicationColor = remember {
        mutableStateOf(Color.Gray)
    }

    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        BasicTextField(modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 22.dp),
            textStyle = TextStyle(fontSize = 16.sp, color = Color.DarkGray),
            value = value,
            onValueChange = {
                onValueChange(it)
            },
            decorationBox = { innerTextField ->
                Box(modifier = Modifier.fillMaxWidth()) {
                    if (value.text.isEmpty()) {
                        Text(
                            text = placeholder, color = Color.Gray, fontSize = 16.sp
                        )
                        indicationColor.value = Color.Gray
                    } else {
                        indicationColor.value = Orange400
                    }
                }
                innerTextField()
            })
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(indicationColor.value)
        )
    }
}

val hashtags = listOf(
    "#ViralReels",
    "#ReelsDaily",
    "#TrendingNow",
    "#ExplorePage",
    "#ReelsInspiration",
    "#ReelsOfInstagram",
    "#ReelsChallenge",
    "#ReelsTrend",
    "#IGReels",
    "#SocialMediaTrends",
    "#InstaReels",
    "#InstagramReels",
    "#ExploreMore",
    "#CreativeContent",
    "#ShortVideos",
    "#ContentCreator",
    "#ReelsPopular",
    "#OnTheReel",
    "#EntertainmentDaily",
    "#DailyReels"
)