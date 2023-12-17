package com.bangkit.minatku.ui.navbar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.bangkit.minatku.R
import com.bangkit.minatku.databinding.ActivityNavbarBinding
import com.bangkit.minatku.ui.community.CommunityFragment
import com.bangkit.minatku.ui.home.HomeFragment
import com.bangkit.minatku.ui.mentor.MentorFragment
import com.bangkit.minatku.ui.modul.premium.ModulPremFragment
import com.bangkit.minatku.ui.profil.ProfilFragment

class NavbarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNavbarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavbarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment())

        binding.bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.NavHome -> replaceFragment(HomeFragment())
                R.id.NavCommunity -> replaceFragment(CommunityFragment())
                R.id.NavProfile -> replaceFragment(ProfilFragment())
                R.id.NavModul -> replaceFragment(ModulPremFragment())
                R.id.NavMentor -> replaceFragment(MentorFragment())
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmenttransaction = fragmentManager.beginTransaction()
        fragmenttransaction.replace(R.id.frame, fragment)
        fragmenttransaction.commit()
    }
}