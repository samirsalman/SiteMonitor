package it.salmanapp.sitemonitor.ui.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import it.salmanapp.sitemonitor.R

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }


    override fun onSupportNavigateUp(): Boolean {
        super.onSupportNavigateUp()
        this.finish()
        return true
    }
}