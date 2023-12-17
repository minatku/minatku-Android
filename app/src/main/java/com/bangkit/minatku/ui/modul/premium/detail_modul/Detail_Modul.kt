package com.bangkit.minatku.ui.modul.premium.detail_modul

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.bangkit.minatku.R
import com.squareup.picasso.Picasso

class Detail_Modul : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_modul)

        val judul: TextView = findViewById(R.id.title)
        val text: TextView = findViewById(R.id.modul_text)
        val image: ImageView = findViewById(R.id.modul_video)
        val back:ImageButton = findViewById(R.id.backButton)
        val home: Button = findViewById(R.id.home)

        home.setOnClickListener {
            finish()
        }

        back.setOnClickListener{
            finish()
        }

        judul.text = intent.getStringExtra(EXTRA_JUDUL)
        text.text = intent.getStringExtra(EXTRA_TEXT)
        val imageurl = intent.getStringExtra(EXTRA_PICT)
        if (imageurl != null) {
            Log.d("tes", imageurl)
        }
        Picasso.get()
            .load(imageurl)
            .into(image)
    }

    companion object{
        const val EXTRA_JUDUL = "extra_judul"
        const val EXTRA_TEXT = "extra_text"
        const val EXTRA_PICT = "extra_pict"
    }
}