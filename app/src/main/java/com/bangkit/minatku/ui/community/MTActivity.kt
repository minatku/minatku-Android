package com.bangkit.minatku.ui.community

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.minatku.R

class MTActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mtactivity)

        val btnBackToCom = findViewById<ImageView>(R.id.btn_backtocom)
        btnBackToCom.setOnClickListener {
            finish() // Call finish() when the back button is pressed
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish() // Call finish() when the back button is pressed
    }
}
