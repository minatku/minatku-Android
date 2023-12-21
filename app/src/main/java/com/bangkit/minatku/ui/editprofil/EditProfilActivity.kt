package com.bangkit.minatku.ui.editprofil

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.bangkit.minatku.R
import com.bangkit.minatku.data.Hasil
import com.bangkit.minatku.data.ViewModelFactory
import com.bangkit.minatku.data.response.UpdatePP
import com.bangkit.minatku.data.response.UserUpdate
import com.bangkit.minatku.databinding.ActivityEditProfilBinding
import com.bangkit.minatku.ui.navbar.NavbarActivity
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody

class EditProfilActivity : AppCompatActivity() {

    private val viewModel by viewModels<EditProfileViewmodel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityEditProfilBinding
    private var currentImageUri: Uri? = null

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Permission request granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Permission request denied", Toast.LENGTH_LONG).show()
            }
        }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            this,
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivUser.setImageResource(R.drawable.placeholder_image)

        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        binding.btnBackprofil.setOnClickListener {
            finish()
        }

        binding.btnEditphoto.setOnClickListener {
            showImageSourceDialog()
        }

        setupAction()
    }

    private fun showImageSourceDialog() {
        val options = arrayOf("Galeri", "Kamera")

        AlertDialog.Builder(this)
            .setTitle("Pilih Sumber Gambar")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> selectProfileImageFromGallery()
                    1 -> takeProfileImageWithCamera()
                }
            }
            .show()
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.ivUser.setImageURI(it)
        }
    }

    private fun selectProfileImageFromGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun takeProfileImageWithCamera() {
        currentImageUri = getImageUri(this)
        launcherIntentCamera.launch(currentImageUri)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        }
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
                                    val intent = Intent(this@EditProfilActivity,NavbarActivity::class.java)
                                    startActivity(intent)
                                }
                                create()
                                show()
                            }
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
                if (currentImageUri != null) {
                    currentImageUri?.let { uri ->
                        val imageFile = uriToFile(uri, this).reduceFileImage()
                        val requestImageFile =
                            imageFile.asRequestBody("image/jpeg".toMediaType())

                        val multipartBody =
                            MultipartBody.Part.createFormData("file", imageFile.name, requestImageFile)

                        viewModel.updatepp(id, multipartBody)
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
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }
}
