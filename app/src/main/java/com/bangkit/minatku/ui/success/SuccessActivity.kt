package com.bangkit.minatku.ui.success

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.bangkit.minatku.R
import com.bangkit.minatku.databinding.ActivitySuccessBinding
import com.bangkit.minatku.ui.login.LoginActivity

class SuccessActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySuccessBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success)
        binding = ActivitySuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val myButton: Button = findViewById(R.id.masuk)
        myButton.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#4CD9D9"))

        binding.masuk.setOnClickListener {
            // Start SignUpActivity when txt_daftar is clicked
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}