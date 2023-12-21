package com.bangkit.minatku.ui.profil

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bangkit.minatku.R
import com.bangkit.minatku.data.pref.UserDetail
import com.bangkit.minatku.data.pref.UserModel
import com.bangkit.minatku.databinding.FragmentProfilBinding
import com.bangkit.minatku.ui.editprofil.EditProfilActivity
import com.squareup.picasso.Picasso

class ProfilFragment : Fragment() {
    private var logoutListener: LogoutListener? = null

    // Declare the ViewBinding variable
    private var _binding: FragmentProfilBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfilBinding.inflate(inflater, container, false)
        val view = binding.root
        val session = logoutListener?.getSession()
        val detail = logoutListener?.getDetail()

        try {
            binding.apply {
                tvUsername.text = session?.name ?: ""
                tvNama.text = detail?.name_lengkap ?: ""
                tvEmail.text = session?.email ?: ""
                tvJeniskelamin.text = detail?.gender ?: ""
                tvHandphone.text = detail?.no_telp?: ""
                tvLokasi.text = detail?.lokasi ?: ""
                tvTtl.text = detail?.tgl_lahir ?: ""

                detail?.fotoPP?.takeIf { it.isNotBlank() }?.let {
                    Picasso.get()
                        .load(it)
                        .placeholder(R.drawable.placeholder_image)
                        .error(R.drawable.placeholder_image)
                        .resize(400, 400)
                        .centerCrop()
                        .into(ivUser)
                } ?: run {
                    Picasso.get()
                        .load(R.drawable.placeholder_image)
                        .resize(400, 400)
                        .centerCrop()
                        .into(ivUser)
                }

                // Set click listeners for the buttons
                btnEdit.setOnClickListener {
                    val intent = Intent(activity, EditProfilActivity::class.java)
                    session?.userId?.let { userId ->
                        intent.putExtra(EditProfilActivity.EXTRA_ID, userId)
                    }
                    startActivity(intent)
                }

                btnLogout.setOnClickListener {
                    logoutListener?.onLogout()
                }
            }
        } catch (e: Exception) {
            Log.e("ProfilFragment", "Error in onCreateView", e)
        }

        return view
    }

    override fun onResume() {
        super.onResume()

        updateProfileData()
    }

    private fun updateProfileData() {
        val session = logoutListener?.getSession()
        val detail = logoutListener?.getDetail()

        try {
            // Check for nullability using the safe call operator and provide default values if null
            binding.apply {
                tvUsername.text = detail?.name ?: ""
                Log.d("tes",detail?.name.toString())
                tvNama.text = detail?.name_lengkap ?: ""
                tvEmail.text = session?.email ?: ""
                tvJeniskelamin.text = detail?.gender ?: ""
                tvHandphone.text = detail?.no_telp?.toString() ?: ""
                tvLokasi.text = detail?.lokasi ?: ""
                tvTtl.text = detail?.tgl_lahir ?: ""

                // Load the image if the URL is not null, otherwise, load a placeholder or handle accordingly
                detail?.fotoPP?.let {
                    Picasso.get()
                        .load(it)
                        .placeholder(R.drawable.placeholder_image) // Placeholder image resource
                        .error(R.drawable.placeholder_image) // Error image resource (if loading fails)
                        .resize(400, 400)
                        .centerCrop()
                        .into(ivUser)
                } ?: run {
                    // Load a placeholder image when fotoPP is null
                    Picasso.get()
                        .load(R.drawable.placeholder_image)
                        .resize(400, 400)
                        .centerCrop()
                        .into(ivUser)
                }
            }
        } catch (e: Exception) {
            Log.e("ProfilFragment", "Error in updateProfileData", e)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setLogoutListener(listener: LogoutListener) {
        logoutListener = listener
    }

    interface LogoutListener {
        fun onLogout()
        fun getSession(): UserModel
        fun getDetail(): UserDetail
    }
}
