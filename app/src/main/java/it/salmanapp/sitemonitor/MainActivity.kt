package it.salmanapp.sitemonitor

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import it.salmanapp.sitemonitor.ui.settings.SettingsActivity
import it.salmanapp.sitemonitor.ui.settings.SettingsFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkBatteryOptimization(this)
        setUpNavigation()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId) {

        R.id.settings->{
            startActivity(Intent(this,SettingsActivity::class.java))
            true
        }

        else -> super.onOptionsItemSelected(item)
    }


    private fun setUpNavigation() {
        // Finding the Navigation Controller
        val navController = findNavController(R.id.main_nav_host)

        // Setting Navigation Controller with the BottomNavigationView
        bottom_app_bar.setupWithNavController(navController)
    }

    fun checkBatteryOptimization(mContext: Context) {

        val powerManager =
            mContext.getSystemService(POWER_SERVICE) as PowerManager
        val packageName = mContext.packageName
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val i = Intent()
            if (!powerManager.isIgnoringBatteryOptimizations(packageName)) {
                i.action = Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
                i.data = Uri.parse("package:$packageName")
                mContext.startActivity(i)
            }
        }
    }


}