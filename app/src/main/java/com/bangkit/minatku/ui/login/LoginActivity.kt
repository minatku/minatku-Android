package com.bangkit.minatku.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.minatku.ui.signup.SignUpActivity
import com.bangkit.minatku.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set an OnClickListener for txt_daftar TextView
        binding.txtDaftar.setOnClickListener {
            // Start SignUpActivity when txt_daftar is clicked
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }
}
