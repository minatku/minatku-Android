package com.bangkit.minatku.ui.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.minatku.databinding.ActivitySignUpBinding
import com.bangkit.minatku.ui.success.SuccessActivity

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            // Start SignUpActivity when txt_daftar is clicked
            startActivity(Intent(this, SuccessActivity::class.java))
        }
    }
}