package com.bangkit.minatku.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.bangkit.minatku.R
import com.bangkit.minatku.ui.joinpremium.JoinPremiumActivity

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Find the join_button by its ID
        val joinButton: Button = view.findViewById(R.id.join_button)

        // Set an OnClickListener for the join_button
        joinButton.setOnClickListener {
            // Launch the JoinPremiumActivity
            val intent = Intent(activity, JoinPremiumActivity::class.java)
            startActivity(intent)
        }

        return view
    }
}
