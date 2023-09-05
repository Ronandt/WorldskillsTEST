package com.example.worldskillstest

import android.content.Context
import android.os.Environment
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCapture.OnImageSavedCallback
import androidx.camera.core.ImageCapture.OutputFileOptions
import androidx.camera.core.ImageCaptureException
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.worldskillstest.ui.theme.Red
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CameraScreen(navController: NavController, activity: MainActivity, baseContext: Context){
    var perms = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) {
        if(!it) {
            Toast.makeText(baseContext, "We need camera for you to take an image", Toast.LENGTH_LONG).show()
        }
    }
    LaunchedEffect(key1 = Unit) {
        perms.launch(android.Manifest.permission.CAMERA)

    }
    var cameraController = remember {LifecycleCameraController(baseContext)}
    LaunchedEffect(Unit) {
        cameraController.bindToLifecycle(activity)

    }

    Box(modifier = Modifier.fillMaxSize()) {

        AndroidView(factory = {
            PreviewView(it).apply {
                controller                           = cameraController
            }
        }, update = {}, modifier = Modifier.fillMaxSize())
        Card(shape = CircleShape, colors = CardDefaults.cardColors(containerColor = Red), modifier = Modifier
            .size(48.dp)
            .align(
                Alignment.BottomCenter
            ), onClick = {
            cameraController.takePicture(OutputFileOptions.Builder(File(activity.getExternalFilesDir(
                Environment.DIRECTORY_PICTURES), "pfp")).build(), ContextCompat.getMainExecutor(baseContext), object :
                ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    outputFileResults.savedUri?.let {
                        SharedPreferenceResolver(baseContext).setUserPfp(
                            it
                        )
                    }
                    navController.navigateUp()

                }

                override fun onError(exception: ImageCaptureException) {
                 Toast.makeText(baseContext, "Something went wrong", Toast.LENGTH_LONG).show()
                }

            })
        }) {

        }
    }
}