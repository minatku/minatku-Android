package com.bangkit.minatku.ui.profil

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
        // Initialize the binding
        _binding = FragmentProfilBinding.inflate(inflater, container, false)

        val view = binding.root
        val session = logoutListener?.getSession()
        val detail = logoutListener?.getDetail()
        if (session != null && detail != null) {
            binding.apply {
                tvUsername.text = session.name
                tvNama.text = detail.name_lengkap
                tvEmail.text = session.email
                tvJeniskelamin.text = detail.gender
                tvHandphone.text = detail.no_telp.toString()
                tvLokasi.text = detail.lokasi
                tvTtl.text = detail.tgl_lahir
                Picasso.get()
                    .load(detail.fotoPP)
                    .resize(400, 400)
                    .centerCrop()
                    .into(ivUser)

            }
        }
        // Set a click listener for the Edit button using ViewBinding
        binding.btnEdit.setOnClickListener {
            val intent = Intent(activity, EditProfilActivity::class.java)
            if (session != null) {
                intent.putExtra(EditProfilActivity.EXTRA_ID,session.userId)
            }
            startActivity(intent)
        }

        // Set a click listener for the Logout button using ViewBinding
        binding.btnLogout.setOnClickListener {
            logoutListener?.onLogout()
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
        if (session != null && detail != null) {
            binding.apply {
                tvUsername.text = session.name
                tvNama.text = detail.name_lengkap
                tvEmail.text = session.email
                tvJeniskelamin.text = detail.gender
                tvHandphone.text = detail.no_telp.toString()
                tvLokasi.text = detail.lokasi
                tvTtl.text = detail.tgl_lahir
                Picasso.get()
                    .load(detail.fotoPP)
                    .resize(400, 400)
                    .centerCrop()
                    .into(ivUser)
            }
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
