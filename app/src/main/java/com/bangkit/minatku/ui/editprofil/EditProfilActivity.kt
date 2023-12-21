package com.bangkit.minatku.ui.editprofil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.bangkit.minatku.data.Hasil
import com.bangkit.minatku.data.ViewModelFactory
import com.bangkit.minatku.data.response.UserUpdate
import com.bangkit.minatku.databinding.ActivityEditProfilBinding
import com.bangkit.minatku.ui.navbar.NavbarActivity

class EditProfilActivity : AppCompatActivity() {

    private val viewModel by viewModels<EditProfileViewmodel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityEditProfilBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBackprofil.setOnClickListener {
            finish()
        }

        setupAction()
    }

    private fun setupAction() {
        binding.btnSave.setOnClickListener {
            val id = intent.getIntExtra(EXTRA_ID, 0)
            var check = ""
            if (binding.checkLak.isChecked) {
                check = "1"
            }
            if (binding.checkPer.isChecked) {
                check = "2"
            }
            val username = binding.edUsername.text.toString()
            val nama_lengkap = binding.edNama.text.toString()
            val tgl_lahir = binding.edTtl.text.toString()
            val lokasi = binding.edLokasi.text.toString()
            val nomor = binding.edNohp.text.toString()

            if (username.isNotEmpty() && nama_lengkap.isNotEmpty() && tgl_lahir.isNotEmpty() && lokasi.isNotEmpty() && nomor.isNotEmpty()) {
                viewModel.update(id, username, nama_lengkap, tgl_lahir, check, nomor, lokasi)

                viewModel.updateResult.observe(this) { result ->
                    when (result) {
                        is Hasil.Loading -> {
                        }

                        is Hasil.Success -> {
                            val response: UserUpdate = result.data
                            AlertDialog.Builder(this).apply {
                                setTitle("Yeah!")
                                setMessage(response.message)
                                setPositiveButton("Lanjut") { _, _ ->
                                    val intent = Intent(this@EditProfilActivity, NavbarActivity::class.java)
                                    intent.putExtra("replaceFragment", "ProfileFragment") // Pass the fragment identifier or any relevant data
                                    startActivity(intent)
                                }
                                create()
                                show()
                            }
                            finish()
                        }

                        is Hasil.Error -> {
                            val errorMessage: String? = result.error
                            AlertDialog.Builder(this).apply {
                                setTitle("OOPS")
                                setMessage(errorMessage)
                                setPositiveButton("OKE") { _, _ ->
                                }
                                create()
                                show()
                            }
                        }
                    }
                }
            } else {
                AlertDialog.Builder(this).apply {
                    setTitle("Perhatian")
                    setMessage("Semua field harus diisi!")
                    setPositiveButton("OK") { _, _ ->
                    }
                    create()
                    show()
                }
            }
        }
    }

    companion object {
        const val EXTRA_ID = "extra_id"
    }
}
