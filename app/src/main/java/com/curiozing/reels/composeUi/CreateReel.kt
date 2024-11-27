package com.curiozing.reels.composeUi

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.MediaStore
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.ripple
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


private val REQUEST_VIDEO_CAPTURE = 1
private val REQUEST_PERMISSIONS = 2

@Composable
fun CreateReel() {

    var permissionsGranted by remember { mutableStateOf(false) }
    var showRationale by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val permissions = listOf(
        Manifest.permission.CAMERA,
    )
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()
        , onResult = {
            if(it.resultCode == Activity.RESULT_OK){
                println("Data... ${it.data?.data}")
            }
        })


    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = {permissionsResult ->
            val allGranted = permissionsResult.values.all { it }
            permissionsGranted = allGranted
        }
    )


    val mediaWriteLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.OpenDocumentTree()
    ) { uri ->
        // Handle the selected directory URI
        if (uri != null) {
            context.contentResolver.takePersistableUriPermission(
                uri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
            )
        }
    }

    LaunchedEffect(Unit){
        mediaWriteLauncher.launch(null)
       permissionsGranted = permissions.all {permissions ->
           ContextCompat.checkSelfPermission(context,permissions) == PackageManager.PERMISSION_GRANTED
       }
    }




    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {



        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            Box(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(20))
                    .background(MaterialTheme.colorScheme.primary)
                    .clickable(onClick = {},
                        indication = ripple(bounded = true, color = Color.Gray),
                        interactionSource = remember { MutableInteractionSource() }
                    )
                    .padding(start = 20.dp, end = 20.dp, top = 80.dp, bottom = 80.dp)

            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable(onClick = {
                        if(permissionsGranted){
                            val videoIntent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
                            videoIntent.resolveActivity(context.packageManager)?.let {
                                launcher.launch(videoIntent)
                            }
                        }else{
                            requestPermissionLauncher.launch(permissions.toTypedArray())
                        }
                    })
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
                    .clickable(onClick = {},
                        indication = ripple(bounded = true, color = Color.Gray),
                        interactionSource = remember { MutableInteractionSource() }
                    )
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

fun checkAndRequestPermission(context:Context) : Boolean {
    val permissions = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)

    val requiredPermission = permissions.filter {
        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }

    if(requiredPermission.isNotEmpty()){
        ActivityCompat.requestPermissions(context as Activity,requiredPermission.toTypedArray(), REQUEST_PERMISSIONS)
     return false
    }

    return true
}

@SuppressLint("QueryPermissionsNeeded")
fun startRecordingVideo(
    context: Context,
    launcher: ManagedActivityResultLauncher<Intent, ActivityResult>
){


}
