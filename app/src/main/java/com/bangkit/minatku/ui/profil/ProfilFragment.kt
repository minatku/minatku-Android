package com.bangkit.minatku.ui.profil

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bangkit.minatku.databinding.FragmentProfilBinding
import com.bangkit.minatku.ui.editprofil.EditProfilActivity
import com.bangkit.minatku.ui.login.LoginActivity

class ProfilFragment : Fragment() {

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

        // Set a click listener for the Edit button using ViewBinding
        binding.btnEdit.setOnClickListener {
            // Start the EditProfilActivity when the Edit button is clicked
            val intent = Intent(activity, EditProfilActivity::class.java)
            startActivity(intent)
        }

        // Set a click listener for the Logout button using ViewBinding
        binding.btnLogout.setOnClickListener {
            // Start the LoginActivity when the Logout button is clicked
            val intent = Intent(activity, LoginActivity::class.java)
            // Add flags to clear the back stack and start a new task
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        return view
    }

    // Don't forget to clean up the binding instance
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
