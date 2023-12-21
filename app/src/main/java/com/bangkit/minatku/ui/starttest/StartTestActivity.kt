package com.bangkit.minatku.ui.starttest

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.minatku.databinding.ActivityStartTestBinding  // Sesuaikan dengan nama file layout binding Anda
import com.bangkit.minatku.ui.assesment.AssessmentActivity

class StartTestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartTestBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnStarttest.setOnClickListener {
            // Pindah ke AssessmentActivity
            val intent = Intent(this, AssessmentActivity::class.java)
            startActivity(intent)
        }
    }
}
