package com.bangkit.minatku.ui.modul.premium

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.minatku.Data.Modul_Dummy
import com.bangkit.minatku.R
import com.bangkit.minatku.databinding.FragmentModulPremBinding
import com.bangkit.minatku.ui.modul.premium.detail_modul.Detail_Modul


class ModulPremFragment : Fragment() {
    private lateinit var binding: FragmentModulPremBinding
    private val fullList = ArrayList<Modul_Dummy>()
    private val displayedList = ArrayList<Modul_Dummy>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentModulPremBinding.inflate(inflater, container, false)

        binding.listModul.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
        }

        fullList.addAll(getlist())
        displayedList.addAll(fullList)
        showRecyclerList(displayedList)

        binding.apply{
            searchview.setupWithSearchBar(searchBar)
            searchview
                .editText
                .setOnEditorActionListener { textView, actionid, event ->
                    searchBar.setText(searchview.text)
                    searchview.hide()
                    search(searchview.text.toString())
                    false
                }
        }

        return binding.root
    }

    private fun search(query: String) {
        val filteredList = ArrayList<Modul_Dummy>()

        for (modul in fullList) {
            if (modul.judul.toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(modul)
            }
        }
        showRecyclerList(filteredList)
    }

    private fun getlist(): ArrayList<Modul_Dummy>{
        val judul = resources.getStringArray(R.array.judul_dummy)
        val desc = resources.getStringArray(R.array.desc_dummy)
        val text = resources.getStringArray(R.array.text_dummy)
        val img = resources.getStringArray(R.array.pict_dummy)
        val list = ArrayList<Modul_Dummy>()
        for (i in judul.indices){
            val modul = Modul_Dummy(judul[i],desc[i], img[i], text[i])
            list.add(modul)
        }
        return list
    }

    private fun showRecyclerList(modulList: ArrayList<Modul_Dummy>) {
        val listModulAdapter = ModulAdapter(modulList)
        binding.listModul.adapter = listModulAdapter

        listModulAdapter.setOnItemClickCallback(object : ModulAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Modul_Dummy) {
                showSelectedModul(data)
            }
        })
    }

    private fun showSelectedModul(modulDummy: Modul_Dummy) {
        val movedata = Intent(requireContext(), Detail_Modul::class.java)
        movedata.putExtra(Detail_Modul.EXTRA_JUDUL, modulDummy.judul)
        movedata.putExtra(Detail_Modul.EXTRA_TEXT, modulDummy.text)
        movedata.putExtra(Detail_Modul.EXTRA_PICT, modulDummy.img)
        startActivity(movedata)
    }

}