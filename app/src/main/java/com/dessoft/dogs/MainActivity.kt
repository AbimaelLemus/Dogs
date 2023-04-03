package com.dessoft.dogs

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.dessoft.dogs.api.ApiServiceInterceptor
import com.dessoft.dogs.auth.LoginActivity
import com.dessoft.dogs.databinding.ActivityMainBinding
import com.dessoft.dogs.doglist.DogListActivity
import com.dessoft.dogs.model.User
import com.dessoft.dogs.settings.SettingsActivity
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName
    private lateinit var binding: ActivityMainBinding
    private lateinit var imageCapture: ImageCapture
    private lateinit var cameraExecutor: ExecutorService
    private var isCameraReady = false

    val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                setupCamera()
            } else {
                Toast.makeText(
                    this,
                    getString(R.string.camera_permission_rejected_message),
                    Toast.LENGTH_LONG
                ).show()
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = User.getLoggedInUser(this)
        if (user == null) {
            openLoginActivity()
            return
        } else {
            //si el usuario existe entonces mandamos al interceptor el token
            ApiServiceInterceptor.setSessionToken(user.authenticationToken)
        }

        binding.settingsFab.setOnClickListener {
            openSettings()
        }

        binding.dogListFab.setOnClickListener {
            openDogList()
        }

        binding.takePothoFab.setOnClickListener {
            if (isCameraReady) {
                takePhoto()
            }
        }

        requestCameraPermission()

    }

    override fun onDestroy() {
        super.onDestroy()
        //revisa si ya esta inicializado...
        if (::cameraExecutor.isInitialized) {
            cameraExecutor.shutdown()
        }
    }

    private fun setupCamera() {
        //despues de que se inicialice la camara, hace lo de adentro, para que no crashee
        binding.pvCamera.post {
            imageCapture = ImageCapture.Builder()
                .setTargetRotation(binding.pvCamera.display.rotation)
                .build()
            cameraExecutor = Executors.newSingleThreadExecutor()
            startCamera()
            isCameraReady = true
        }
    }

    fun requestCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED -> {
                    setupCamera()
                }
                shouldShowRequestPermissionRationale(
                    android.Manifest.permission.CAMERA
                ) -> { // solo es true, cuando el usuario ya le haya dado click en "Rechazar"
                    // In an educational UI, explain to the user why your app requires this
                    // permission for a specific feature to behave as expected, and what
                    // features are disabled if it's declined. In this UI, include a
                    // "cancel" or "no thanks" button that lets the user continue
                    // using your app without granting the permission.
                    AlertDialog.Builder(this)
                        .setTitle(getString(R.string.camera_permission_rationale_dialog_title))
                        .setMessage(getString(R.string.camera_permission_rationale_dialog_message))
                        .setPositiveButton(android.R.string.ok) { _, _ ->
                            requestPermissionLauncher.launch(
                                android.Manifest.permission.CAMERA
                            )
                        }
                        .setNegativeButton(android.R.string.cancel) { _, _ ->
                        }
                        .show()
                }
                else -> {
                    // You can directly ask for the permission.
                    // The registered ActivityResultCallback gets the result of this request.
                    requestPermissionLauncher.launch(
                        android.Manifest.permission.CAMERA
                    )
                }
            }
        } else {
            setupCamera()
        }

    }

    private fun takePhoto() {
        val outputFileOptions = ImageCapture.OutputFileOptions.Builder(getOutputPhotoFile()).build()
        imageCapture.takePicture(outputFileOptions, cameraExecutor,
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(error: ImageCaptureException) {
                    Toast.makeText(
                        this@MainActivity,
                        "Error taking prhoto ${error.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val photoUri = outputFileResults.savedUri
                    openWholeImageActivity(photoUri.toString())
                }
            })

    }

    private fun openWholeImageActivity(photoUri: String) {
        val intent = Intent(this, WholeImageActivity::class.java)
        intent.putExtra(WholeImageActivity.PHOTO_URI_KEY, photoUri)
        startActivity(intent)
    }

    private fun getOutputPhotoFile(): File {
        val mediaDir = externalMediaDirs.firstOrNull()
            ?.let { File(it, resources.getString(R.string.app_name) + ".jpg").apply { mkdir() } }

        return if (mediaDir != null && mediaDir.exists()) {
            mediaDir
        } else {
            filesDir
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            //Used to bind to lifecycle of cameras to the lifecycle owner
            val cameraProvider = cameraProviderFuture.get()

            //preview
            val preview = Preview.Builder().build()
            preview.setSurfaceProvider(binding.pvCamera.surfaceProvider)

            //select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            val imageAnalysis = ImageAnalysis.Builder()
                // enable the following line if RGBA output is needed.
                // .setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888)
                //.setTargetResolution(Size(1280, 720))
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                //lo de arriba es para que solo utilice la ultima foto tomada
                .build()
            imageAnalysis.setAnalyzer(cameraExecutor) { imageProxy ->
                val rotationDegrees = imageProxy.imageInfo.rotationDegrees
                // insert your code here.

                // after done, release the ImageProxy object
                imageProxy.close()
            }

            //Bind use cases to camera
            cameraProvider.bindToLifecycle(
                this,
                cameraSelector,
                preview,
                imageCapture,
                imageAnalysis
            )

        }, ContextCompat.getMainExecutor(this))
    }

    private fun openDogList() {
        startActivity(Intent(this, DogListActivity::class.java))
    }

    private fun openSettings() {
        startActivity(Intent(this, SettingsActivity::class.java))
    }

    private fun openLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}