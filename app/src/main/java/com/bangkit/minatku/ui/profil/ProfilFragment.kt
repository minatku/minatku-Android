package com.bangkit.minatku.ui.profil

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bangkit.minatku.databinding.FragmentProfilBinding
import com.bangkit.minatku.ui.editprofil.EditProfilActivity

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

        // Set a click listener for the button using ViewBinding
        binding.btnEdit.setOnClickListener {
            // Start the EditProfilActivity when the button is clicked
            val intent = Intent(activity, EditProfilActivity::class.java)
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
