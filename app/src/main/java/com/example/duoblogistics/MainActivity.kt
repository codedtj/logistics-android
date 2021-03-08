package com.example.duoblogistics

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    private val CAMERA_PERMISSION_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_dashboard,
                R.id.navigation_notifications,
                R.id.navigation_barcode_scanner
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        checkPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE)
    }


    private fun checkPermission(permission: String, requestCode: Int) {
        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(
                this@MainActivity,
                permission
            )
            == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat
                .requestPermissions(
                    this@MainActivity, arrayOf(permission),
                    requestCode
                )
        } else {
            Toast
                .makeText(
                    this@MainActivity,
                    "Permission already granted",
                    Toast.LENGTH_SHORT
                )
                .show()
        }
    }
}