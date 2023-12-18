package com.bangkit.minatku.ui.editprofil

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bangkit.minatku.R

class EditProfilActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profil)

        val backButton = findViewById<ImageView>(R.id.btn_backprofil)

        // Set OnClickListener for the back button
        backButton.setOnClickListener {
            // Finish the activity when the back button is clicked
            finish()
        }
    }
}
