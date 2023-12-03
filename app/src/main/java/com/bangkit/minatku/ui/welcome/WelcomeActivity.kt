package com.bangkit.minatku.ui.welcome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.bangkit.minatku.R
import com.bangkit.minatku.ui.login.LoginActivity

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val btnNextLogin: ImageButton = findViewById(R.id.btn_nextlogin)

        btnNextLogin.setOnClickListener {
            // Start LoginActivity when btn_nextlogin is clicked
            val intent = Intent(this@WelcomeActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
