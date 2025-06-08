@file:Suppress("DEPRECATION")

package com.astrobyte.orion

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class UpdateDataActivity : AppCompatActivity() {
    private lateinit var siswa: DataSiswa
    private lateinit var spinnerJurusan: Spinner
    private lateinit var radioGroupJK: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_data)

        val btnBack: ImageButton = findViewById(R.id.btnBack)

        btnBack.setOnClickListener {
            val intent = Intent(this, DataSiswaActivity::class.java)
            startActivity(intent)
        }

        siswa = intent.getParcelableExtra<DataSiswa>("EXTRA_SISWA") ?: DataSiswa().also {
            Toast.makeText(this, "Data kosong, isi manual", Toast.LENGTH_SHORT).show()
            finish()
        }

        findViewById<EditText>(R.id.formNama).setText(siswa.nama)
        findViewById<EditText>(R.id.formNisn).setText(siswa.nisn)

        spinnerJurusan = findViewById(R.id.spinJur)
        ArrayAdapter.createFromResource(
            this,
            R.array.jurusan,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerJurusan.adapter = adapter
        }

        val jurusanArray = resources.getStringArray(R.array.jurusan)
        val jurusanPosition = jurusanArray.indexOf(siswa.jurusan)
        if (jurusanPosition >= 0) {
            spinnerJurusan.setSelection(jurusanPosition)
        }

        radioGroupJK = findViewById(R.id.radioGroup)
        when (siswa.jenisKelamin.toLowerCase()) {
            "laki-laki" -> radioGroupJK.check(R.id.Lk)
            "perempuan" -> radioGroupJK.check(R.id.Pr)
        }
        findViewById<Button>(R.id.btnSimpan).setOnClickListener {
            updateData()
        }
    }

    private fun updateData() {
        val nama = findViewById<EditText>(R.id.formNama).text.toString()
        val nisn = findViewById<EditText>(R.id.formNisn).text.toString()

        if (nama.isEmpty() || nisn.isEmpty()) {
            Toast.makeText(this, "Nama dan NISN tidak boleh kosong", Toast.LENGTH_SHORT).show()
            return
        }

        siswa.apply {
            this.nama = nama
            this.nisn = nisn
            jurusan = spinnerJurusan.selectedItem.toString()
            jenisKelamin = when (radioGroupJK.checkedRadioButtonId) {
                R.id.Lk -> "Laki-Laki"
                R.id.Pr -> "Perempuan"
                else -> ""
            }
        }

        lifecycleScope.launch {
            AppDatabase.getDatabase(this@UpdateDataActivity)
                .dataSiswaDao()
                .update(siswa = siswa)

            Toast.makeText(this@UpdateDataActivity, "Data berhasil diupdate", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}