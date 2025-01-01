package com.curiozing.reels.composeUi

import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView


@Composable
fun VideoPlayer(src: String) {

    val context = LocalContext.current

    val exoplayer = ExoPlayer.Builder(context).build().apply {
        this.setMediaItem(MediaItem.fromUri(Uri.parse(src)))
        prepare()
        playWhenReady = true
    }


    AndroidView(factory = {
        PlayerView(context).apply {
            player = exoplayer
            layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
            )

            findViewById<View>(androidx.media3.ui.R.id.exo_settings)?.visibility = View.GONE
            findViewById<View>(androidx.media3.ui.R.id.exo_next)?.visibility = View.GONE
            findViewById<View>(androidx.media3.ui.R.id.exo_prev)?.visibility = View.GONE
            findViewById<View>(androidx.media3.ui.R.id.exo_settings)?.visibility = View.GONE
        }
    })

    DisposableEffect(Unit) {
        onDispose {
            exoplayer.release()
        }
    }
}