package com.astrobyte.orion

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class AddDataActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase
    private lateinit var dao: DataSiswaDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_data)

        db = AppDatabase.getDatabase(this)
        dao = db.dataSiswaDao()

        val inputNama: TextInputEditText = findViewById(R.id.inpName)
        val inputNisn: TextInputEditText = findViewById(R.id.inpNisn)
        val inputJk: RadioButton = findViewById(R.id.Lk)
        val spinnerPilihan: Spinner = findViewById(R.id.spinJur)
        val buttonBack : ImageButton = findViewById(R.id.btnBack)

        buttonBack.setOnClickListener {
            val intent = Intent(this, DataSiswaActivity::class.java)
            startActivity(intent)
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.jurusan,
            R.layout.spinner
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerPilihan.adapter = adapter
        }

        val btnSimpan: Button = findViewById(R.id.btnSimpan)

        btnSimpan.setOnClickListener {
            val nama = inputNama.text.toString()
            val nisn = inputNisn.text.toString()
            val jurusan = spinnerPilihan.selectedItem.toString()
            val jenisKelamin = if (inputJk.isChecked) "Laki-Laki" else "Perempuan"

            val siswa = DataSiswa(
                nisn = nisn,
                nama = nama,
                jurusan = jurusan,
                jenisKelamin = jenisKelamin
            )

            lifecycleScope.launch {
                dao.insert(siswa)
                runOnUiThread {
                    Toast.makeText(this@AddDataActivity, "Data berhasil disimpan", Toast.LENGTH_SHORT).show()
                    inputNama.setText("")
                    inputNisn.setText("")
                    inputJk.isChecked = false
                    spinnerPilihan.setSelection(0)

                    val intent = Intent(this@AddDataActivity, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }
}
