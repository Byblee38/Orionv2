package com.astrobyte.orion

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// IsiAbsenActivity.kt
class IsiAbsenActivity : AppCompatActivity() {
    private lateinit var db: AppDatabase
    private lateinit var adapter: AbsenAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var buttonSimpan: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_isi_absen)

        db = AppDatabase.getDatabase(this)

        recyclerView = findViewById(R.id.recycleAbsen)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        buttonSimpan = findViewById(R.id.btnSimpanAbsen)

        lifecycleScope.launch {
            val siswaList = db.dataSiswaDao().getAll()
            adapter = AbsenAdapter(siswaList)
            recyclerView.adapter = adapter
        }

        buttonSimpan.setOnClickListener {
            simpanAbsen()
        }

        val btnBack: ImageButton = findViewById(R.id.backIconAbsen)
        btnBack.setOnClickListener {
            finish()
        }
    }

    private fun simpanAbsen() {
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val absenList = adapter.statusMap.map { (nisn, status) ->
            AbsenSiswa(nisn = nisn, tanggal = today, status = status)
        }

        lifecycleScope.launch {
            absenList.forEach {
                db.absenSiswaDao().insert(it)
            }
            Toast.makeText(this@IsiAbsenActivity, "Absensi disimpan!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}

