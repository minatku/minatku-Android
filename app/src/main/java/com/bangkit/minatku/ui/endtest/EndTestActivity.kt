package com.bangkit.minatku.ui.endtest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.minatku.databinding.ActivityEndTestBinding
import com.bangkit.minatku.ui.navbar.NavbarActivity

class EndTestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEndTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEndTestBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnStarttest.setOnClickListener {
            // Pindah ke AssessmentActivity
            val intent = Intent(this, NavbarActivity::class.java)
            startActivity(intent)
        }
    }
}