package com.bangkit.minatku.ui.success

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.bangkit.minatku.R

class SuccessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success)

        val myButton: Button = findViewById(R.id.masuk)
        myButton.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#4CD9D9"))
    }
}