package com.example.duoblogistics.ui.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.duoblogistics.R
import com.example.duoblogistics.settings.SettingsFragment

/** Hosts the preference fragment to configure settings.  */
class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_settings)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings_container, SettingsFragment())
            .commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
