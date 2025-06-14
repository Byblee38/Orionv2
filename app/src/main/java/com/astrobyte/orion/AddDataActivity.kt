package com.astrobyte.orion

import android.content.Context
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
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
        val buttonBack : ImageButton = findViewById(R.id.btnBackAddData)

        buttonBack.setOnClickListener {
            finish()
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.jurusan,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerPilihan.adapter = adapter
        }

        val btnSimpan: Button = findViewById(R.id.btnSimpan)

        btnSimpan.setOnClickListener {
            val nama = inputNama.text.toString()
            val nisn = inputNisn.text.toString()
            val jurusan = spinnerPilihan.selectedItem.toString()
            val radioGroupJK: RadioGroup = findViewById(R.id.radioGroupJK)
            val selectedJKId = radioGroupJK.checkedRadioButtonId
            val selectedRadioButton = findViewById<RadioButton>(selectedJKId)
            val jenisKelamin = selectedRadioButton.text.toString()


            val siswa = DataSiswa(
                nisn = nisn,
                nama = nama,
                jurusan = jurusan,
                jenisKelamin = jenisKelamin
            )

            lifecycleScope.launch {
                val existingSiswa = dao.getByNisn(nisn)
                if (existingSiswa != null) {
                    AlertDialog.Builder(this@AddDataActivity).apply {
                        setTitle("Data Sudah Ada")
                        setMessage("Siswa dengan NISN $nisn sudah ada di database.")
                        setPositiveButton("OK") { dialog, _ ->
                            dialog.dismiss()
                        }
                        setOnDismissListener {
                            inputNisn.setText("")
                        }
                        create()
                    }.show()
                }else{
                    dao.insert(siswa)
                    runOnUiThread {
                        Toast.makeText(this@AddDataActivity, "Data berhasil disimpan", Toast.LENGTH_SHORT).show()
                        inputNama.setText("")
                        inputNisn.setText("")
                        inputJk.isChecked = false
                        spinnerPilihan.setSelection(0)

                        finish()
                    }
                }
            }
        }
    }
}
