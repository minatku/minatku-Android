package com.bangkit.minatku.ui.navbar

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bangkit.minatku.R
import com.bangkit.minatku.data.ViewModelFactory
import com.bangkit.minatku.data.pref.UserDetail
import com.bangkit.minatku.data.pref.UserModel
import com.bangkit.minatku.databinding.ActivityNavbarBinding
import com.bangkit.minatku.ui.community.CommunityFragment
import com.bangkit.minatku.ui.home.HomeFragment
import com.bangkit.minatku.ui.mentor.MentorFragment
import com.bangkit.minatku.ui.modul.premium.ModulPremFragment
import com.bangkit.minatku.ui.profil.ProfilFragment
import com.bangkit.minatku.ui.welcome.WelcomeActivity

class NavbarActivity : AppCompatActivity(), ProfilFragment.LogoutListener {

    private lateinit var binding: ActivityNavbarBinding
    private var id = 0
    private var name = ""
    private var email = ""
    private var gender = ""
    private var foto = ""
    private var name_lengkap = ""
    private var no = ""
    private var tgl = ""
    private var lok = ""
    private lateinit var currentUser: UserModel
    private lateinit var currentUserDetail: UserDetail


    private val viewModel by viewModels<NavbarViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavbarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.NavHome -> {
                    updateData()
                    replaceFragment(HomeFragment.newInstance(name, foto))
                }

                R.id.NavCommunity -> {
                    updateData()
                    replaceFragment(CommunityFragment())
                }

                R.id.NavProfile -> {
                    updateData()
                    replaceFragment(ProfilFragment().apply { setLogoutListener(this@NavbarActivity) })
                }

                R.id.NavModul -> {
                    updateData()
                    replaceFragment(ModulPremFragment())
                }

                R.id.NavMentor -> {
                    updateData()
                    replaceFragment(MentorFragment())
                }
            }
            true
        }

        viewModel.getSession().observe(this) { user ->
            currentUser = user
            id = user.userId
            name = user.name
            email = user.email
            viewModel.detail(id)
            if (!user.isLogin) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
        }
        viewModel.getDetail().observe(this) { tes ->
            currentUserDetail = tes
            gender = tes.gender
            foto = tes.fotoPP
            name_lengkap = tes.name_lengkap
            no = tes.no_telp
            tgl = tes.tgl_lahir
            lok = tes.lokasi
        }

        if (intent.hasExtra("replaceFragment")) {
            val fragmentToReplace = intent.getStringExtra("replaceFragment")
            when (fragmentToReplace) {
                "ProfileFragment" -> replaceFragment(ProfilFragment().apply {
                    setLogoutListener(this@NavbarActivity)
                })
            }
        } else {
            viewModel.getDetail().observe(this) { userDetail ->
                val photoPath = userDetail.fotoPP
                val name = userDetail.name_lengkap

                val homeFragment = HomeFragment.newInstance(name, photoPath)
                replaceFragment(homeFragment)
            }
        }
    }

    private fun updateData() {
        viewModel.detailResult.observe(this) {

        }
        viewModel.getSession().observe(this) { user ->
            id = user.userId
            name = user.name
            email = user.email
        }
        viewModel.getDetail().observe(this) { user ->
            gender = user.gender
            foto = user.fotoPP
            name_lengkap = user.name_lengkap
            no = user.no_telp
            tgl = user.tgl_lahir
            lok = user.lokasi
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmenttransaction = fragmentManager.beginTransaction()
        fragmenttransaction.replace(R.id.frame, fragment)
        fragmenttransaction.commit()
    }

    private fun logout() {
        viewModel.logout()
    }

    override fun onLogout() {
        logout()
    }

    override fun getSession(): UserModel {
        return currentUser
    }

    override fun getDetail(): UserDetail {
        return currentUserDetail
    }
}

