package com.dessoft.dogs

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.dessoft.dogs.api.ApiServiceInterceptor
import com.dessoft.dogs.auth.LoginActivity
import com.dessoft.dogs.databinding.ActivityMainBinding
import com.dessoft.dogs.doglist.DogListActivity
import com.dessoft.dogs.model.User
import com.dessoft.dogs.settings.SettingsActivity

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName

    val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                // openCamera()
                Log.i(TAG, "requestPermissionLauncher")
            } else {
                Toast.makeText(
                    this,
                    "You eed to accept camera permission to use camera",
                    Toast.LENGTH_LONG
                ).show()
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
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

        requestCameraPermission()

    }

    fun requestCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED -> {
                    // openCamera()
                    Log.d(TAG, "requestCameraPermission: ")
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
                        .setTitle("Aceptame por favor")
                        .setMessage("Aceta el permiso  o me da ansiedad")
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
            // openCamera()
        }

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