package com.bangkit.minatku.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.bangkit.minatku.data.Hasil
import com.bangkit.minatku.data.ViewModelFactory
import com.bangkit.minatku.data.response.LoginResponse
import com.bangkit.minatku.databinding.ActivityLoginBinding
import com.bangkit.minatku.ui.navbar.NavbarActivity
import com.bangkit.minatku.ui.signup.SignUpActivity


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private fun showErrorMessage(errorMessage: String) {
        AlertDialog.Builder(this).apply {
            setTitle("Waduh")
            setMessage(errorMessage)
            setPositiveButton("OK") { _, _ -> }
            create()
            show()
        }
    }

    private fun setupAction() {
        binding.btnLogin.setOnClickListener {
            val password = binding.edPassword.text.toString()
            val email = binding.edEmail.text.toString()

            viewModel.loginResult.observe(this) { result ->
                tunjukkanLoading(false)
                when (result) {
                    is Hasil.Error -> result.error?.let { it1 -> showErrorMessage(it1) }
                    is Hasil.Success -> showSuccessMessage(result.data)
                    is Hasil.Loading -> tunjukkanLoading(true)
                }
            }
            viewModel.login(email, password)
        }
    }


    private fun showSuccessMessage(response: LoginResponse) {
        AlertDialog.Builder(this).apply {
            setTitle("Mantap")
            setMessage(response.message)
            setPositiveButton("OK") { _, _ ->
                val intent = Intent(this@LoginActivity, NavbarActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }
            create()
            show()
        }
    }


    private fun tunjukkanLoading(isLoading: Boolean) {
        binding.pbLogin.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.txtDaftar.setOnClickListener {
            // Start SignUpActivity when txt_daftar is clicked
            startActivity(Intent(this, SignUpActivity::class.java))
        }
        setupAction()
    }
}
