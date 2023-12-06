package com.bangkit.minatku.ui.modul.premium

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.minatku.Data.Modul_Dummy
import com.bangkit.minatku.R
import com.bangkit.minatku.ui.modul.premium.detail_modul.Detail_Modul

class ModulPremActivity : AppCompatActivity() {
    private lateinit var listAdapter: RecyclerView
    private val list = ArrayList<Modul_Dummy>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modul_prem)

        listAdapter = findViewById(R.id.list_modul)
        listAdapter.setHasFixedSize(true)
        list.addAll(getlist())
        showRecyclerList()
    }


    private fun getlist(): ArrayList<Modul_Dummy>{
        val judul = resources.getStringArray(R.array.judul_dummy)
        val desc = resources.getStringArray(R.array.desc_dummy)
        val text = resources.getStringArray(R.array.text_dummy)
        val list = ArrayList<Modul_Dummy>()
        for (i in judul.indices){
            val modul = Modul_Dummy(judul[i],desc[i], text[i])
            list.add(modul)
        }
        return list
    }

    private fun showRecyclerList() {
        listAdapter.layoutManager = LinearLayoutManager(this)
        val listmodulAdapter = ModulAdapter(list)
        listAdapter.adapter = listmodulAdapter

        listmodulAdapter.setOnItemClickCallback(object : ModulAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Modul_Dummy) {
                showSelectedModul(data)
            }
        })
    }

    private fun showSelectedModul(modulDummy: Modul_Dummy){
        val movedata = Intent(this, Detail_Modul::class.java)
        movedata.putExtra(Detail_Modul.EXTRA_JUDUL,modulDummy.judul)
        movedata.putExtra(Detail_Modul.EXTRA_TEXT,modulDummy.text)
        startActivity(movedata)
    }
}