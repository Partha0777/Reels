package com.curiozing.reels.composeUi

import android.net.Uri
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

@Composable
fun VideoPostView() {
    Box {

    }
}


@Composable
fun VideoPlayer(src: String): PlayerView {
    val context = LocalContext.current
    val exoplayer = ExoPlayer.Builder(context).build().apply {
        this.setMediaItem(MediaItem.fromUri(Uri.parse(src)))
    }

    val player = PlayerView(context).apply {
        player = exoplayer
        layoutParams = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }
    return player
}

