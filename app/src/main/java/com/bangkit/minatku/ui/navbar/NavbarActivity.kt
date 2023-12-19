package com.bangkit.minatku.ui.navbar

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bangkit.minatku.R
import com.bangkit.minatku.data.ViewModelFactory
import com.bangkit.minatku.databinding.ActivityNavbarBinding
import com.bangkit.minatku.ui.community.CommunityFragment
import com.bangkit.minatku.ui.home.HomeFragment
import com.bangkit.minatku.ui.mentor.MentorFragment
import com.bangkit.minatku.ui.modul.premium.ModulPremFragment
import com.bangkit.minatku.ui.profil.ProfilFragment
import com.bangkit.minatku.ui.welcome.WelcomeActivity

class NavbarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNavbarBinding

    private val viewModel by viewModels<NavbarViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavbarBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewModel.getSession().observe(this) {

        }

        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.NavHome -> replaceFragment(HomeFragment())
                R.id.NavCommunity -> replaceFragment(CommunityFragment())
                R.id.NavProfile -> replaceFragment(ProfilFragment())
                R.id.NavModul -> replaceFragment(ModulPremFragment())
                R.id.NavMentor -> replaceFragment(MentorFragment())
            }
            true
        }


        viewModel.getSession().observe(this) { user ->
            Log.d("token", "onCreate: ${user.token}")
            Log.d("user", "onCreate: ${user.isLogin}")
            if (!user.isLogin) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }

        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmenttransaction = fragmentManager.beginTransaction()
        fragmenttransaction.replace(R.id.frame, fragment)
        fragmenttransaction.commit()
    }
}

