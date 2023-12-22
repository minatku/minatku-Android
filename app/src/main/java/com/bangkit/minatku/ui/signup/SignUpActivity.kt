package com.bangkit.minatku.ui.signup

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.bangkit.minatku.data.Hasil
import com.bangkit.minatku.data.ViewModelFactory
import com.bangkit.minatku.data.response.RegisterResponse
import com.bangkit.minatku.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding

    private val viewModel by viewModels<SignUpViewModel>{
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
    }


    private fun setupAction() {
        binding.btnLogin.setOnClickListener {
            val username = binding.edUsername.text.toString()
            val nama_lengkap = binding.edName.text.toString()
            val email = binding.edEmail.text.toString()
            val password = binding.edPassword.text.toString()

            viewModel.register(username, nama_lengkap, email, password)
            viewModel.registrationResult.observe(this) { result ->
                when (result) {
                    is Hasil.Loading -> {
                        showLoading(true)
                    }

                    is Hasil.Success -> {
                        showLoading(false)
                        // Lakukan sesuatu dengan hasil sukses
                        val response: RegisterResponse = result.data
                        // Tampilkan response atau lakukan sesuatu dengan data response
                        AlertDialog.Builder(this).apply {
                            setTitle("Yeah!")
                            setMessage(response.message)
                            setPositiveButton("Lanjut") { _, _ ->
                                finish()
                            }
                            create()
                            show()
                        }
                    }

                    is Hasil.Error -> {
                        showLoading(false)
                        // Lakukan sesuatu dengan hasil error
                        val errorMessage: String? = result.error
                        // Tampilkan pesan error atau lakukan sesuatu dengan pesan error
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
        }
    }

    fun showLoading(isLoading: Boolean) {
        binding.apply {
            if (isLoading) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        }
    }
}