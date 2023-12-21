package com.bangkit.minatku.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
        val top1 = arguments?.getString("top1")
        val top2 = arguments?.getString("top2")
        val top3 = arguments?.getString("top3")
        val top4 = arguments?.getString("top4")
        val top5 = arguments?.getString("top5")

        binding.apply {
            username.text = name.toString()
            "1. ${top1.toString()}".also { judul1.text = it }
            "2. ${top2.toString()}".also { judul2.text = it }
            "3. ${top3.toString()}".also { judul3.text = it }
            "4. ${top4.toString()}".also { judul4.text = it }
            "5. ${top5.toString()}".also { judul5.text = it }

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
        fun newInstance(name: String, foto: String, top1:String, top2:String, top3:String, top4:String, top5:String): HomeFragment {
            val fragment = HomeFragment()
            val args = Bundle()
            args.putString("names", name)
            args.putString("foto", foto)
            args.putString("top1", top1)
            args.putString("top2", top2)
            args.putString("top3", top3)
            args.putString("top4", top4)
            args.putString("top5", top5)

            fragment.arguments = args
            return fragment
        }
    }
}
