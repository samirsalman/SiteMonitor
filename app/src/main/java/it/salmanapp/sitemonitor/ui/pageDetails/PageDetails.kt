package it.salmanapp.sitemonitor.ui.pageDetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import it.salmanapp.sitemonitor.R
import kotlinx.android.synthetic.main.activity_page_details.*
import java.lang.Exception

class PageDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page_details)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val url : String = intent.getStringExtra("url")!!
        try {
            site_view.loadUrl(url)
        }catch (e:Exception){
            Log.e("WebViewError",e.message.toString())
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        super.onSupportNavigateUp()
        this.finish()
        return true
    }
}