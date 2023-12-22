package com.bangkit.minatku.ui.joinpremium

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.minatku.R

class JoinPremiumActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_premium)

        val backButton: ImageButton = findViewById(R.id.btn_backfromprem)

        backButton.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        // Add any additional logic if needed
        super.onBackPressed()
        finish() // This will invoke onDestroy
    }
}
