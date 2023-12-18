package com.bangkit.minatku.ui.community

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.minatku.Data.PostDummy
import com.bangkit.minatku.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CommunityFragment : Fragment() {
    private lateinit var listAdapter: RecyclerView
    private val list = ArrayList<PostDummy>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_community, container, false)

        listAdapter = view.findViewById(R.id.rv_community)
        listAdapter.setHasFixedSize(true)
        list.addAll(getlist())
        showRecyclerList()

        // Find the FloatingActionButton
        val fabAdd: FloatingActionButton = view.findViewById(R.id.fab_add)

        // Set an OnClickListener to navigate to MTActivity
        fabAdd.setOnClickListener {
            val intent = Intent(requireContext(), MTActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    private fun getlist(): ArrayList<PostDummy> {
        val username = resources.getStringArray(R.array.username_dummy)
        val nama = resources.getStringArray(R.array.namauser_dummy)
        val fpimg = resources.getStringArray(R.array.fpuser_dummy)
        val tanggal = resources.getStringArray(R.array.tanggal_dummy)
        val descpost = resources.getStringArray(R.array.descpost_dummy)
        val list = ArrayList<PostDummy>()
        for (i in username.indices) {
            val modul = PostDummy(username[i], descpost[i], fpimg[i], nama[i], tanggal[i])
            list.add(modul)
        }
        return list
    }

    private fun showRecyclerList() {
        listAdapter.layoutManager = LinearLayoutManager(requireContext())
        val listmodulAdapter = CommunityAdapter(list)
        listAdapter.adapter = listmodulAdapter
    }
}
