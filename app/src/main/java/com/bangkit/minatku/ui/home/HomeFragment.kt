package com.bangkit.minatku.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bangkit.minatku.R
import com.bangkit.minatku.databinding.FragmentHomeBinding
import com.bangkit.minatku.ui.joinpremium.JoinPremiumActivity
import com.squareup.picasso.Picasso

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        // Set an OnClickListener for the join_button using View Binding
        binding.joinButton.setOnClickListener {
            // Launch the JoinPremiumActivity
            val intent = Intent(activity, JoinPremiumActivity::class.java)
            startActivity(intent)
        }

        val name = arguments?.getString("names")
        val foto = arguments?.getString("foto")

        binding.apply {
            username.text = name.toString()

            // Check if the photo URL is not null or empty before loading it with Picasso
            if (!foto.isNullOrEmpty()) {
                Picasso.get()
                    .load(foto)
                    .resize(65, 64)
                    .centerCrop()
                    .into(ppImg)
            } else {
                Picasso.get().load(R.drawable.error_image).into(ppImg)
            }
            return binding.root
        }
    }

    companion object {
        fun newInstance(name: String, foto: String): HomeFragment {
            val fragment = HomeFragment()
            val args = Bundle()
            args.putString("names", name)
            args.putString("foto", foto)

            fragment.arguments = args
            return fragment
        }
    }
}
