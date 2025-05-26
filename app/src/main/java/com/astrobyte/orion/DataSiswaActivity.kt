package com.astrobyte.orion

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject

class DataSiswaActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var siswaAdapter: SiswaAdapter
    private val siswaList = ArrayList<Siswa>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_siswa)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        recyclerView = findViewById(R.id.recyclerViewSiswa)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(Space(16, 1))
        siswaAdapter = SiswaAdapter(siswaList)
        recyclerView.adapter = siswaAdapter

        val db = FirebaseFirestore.getInstance()
        val btnAddData:ImageButton = findViewById(R.id.btnAdd)

        btnAddData.setOnClickListener {
            val intent = Intent(this, AddDataActivity::class.java)
            startActivity(intent)
        }

        db.collection("datasiswa")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val siswa = document.toObject(Siswa::class.java)
                    siswaList.add(siswa)
                }
                siswaAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Gagal ambil data", Toast.LENGTH_SHORT).show()
            }
    }
}