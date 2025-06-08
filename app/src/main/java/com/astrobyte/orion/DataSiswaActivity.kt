package com.astrobyte.orion

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class DataSiswaActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var siswaAdapter: SiswaAdapter
    private val siswaList = ArrayList<DataSiswa>()
    private lateinit var dataSiswaDao: DataSiswaDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_siswa)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        recyclerView = findViewById(R.id.recyclerViewSiswa)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(Space(16, 1))

        val db = AppDatabase.getDatabase(this)
        dataSiswaDao = db.dataSiswaDao()


        siswaAdapter = SiswaAdapter(
            listSiswa = siswaList,
            onDeleteClick = { siswa ->
                showDeleteDialog(siswa)
            },
            onUpdateClick = { siswa ->
                val intent = Intent(this, UpdateDataActivity::class.java).apply {
                    putExtra("EXTRA_SISWA", siswa)
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                }
                startActivity(intent)
            }
        )
        recyclerView.adapter = siswaAdapter

        val btnAddData: ImageButton = findViewById(R.id.btnAdd)
        btnAddData.setOnClickListener {
            val intent = Intent(this, AddDataActivity::class.java)
            startActivity(intent)
        }

        val btnBack: ImageButton = findViewById(R.id.btnBack)
        btnBack.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        loadData()
    }

    private fun loadData() {
        lifecycleScope.launch {
            val dataSiswa = dataSiswaDao.getAll()
            siswaList.clear()
            siswaList.addAll(dataSiswa)
            siswaAdapter.notifyDataSetChanged()
        }
    }

    private fun showDeleteDialog(siswa: DataSiswa) {
        AlertDialog.Builder(this)
            .setTitle("Hapus Data")
            .setMessage("Yakin hapus data ${siswa.nama}?")
            .setPositiveButton("HAPUS") { _, _ ->
                deleteSiswa(siswa)
            }
            .setNegativeButton("BATAL", null)
            .show()
    }

    private fun deleteSiswa(siswa: DataSiswa) {
        lifecycleScope.launch {
            dataSiswaDao.delete(siswa)
            siswaList.remove(siswa)
            siswaAdapter.notifyDataSetChanged()
            Toast.makeText(
                this@DataSiswaActivity,
                "${siswa.nama} berhasil dihapus",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }
}