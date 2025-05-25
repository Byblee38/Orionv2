package com.astrobyte.orion

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.RadioButton
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.FirebaseFirestore

class AddDataActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_data)

        val db = FirebaseFirestore.getInstance()
        val inputNama: TextInputEditText = findViewById(R.id.inpName)
        val inputNisn: TextInputEditText = findViewById(R.id.inpNisn)
        val inputJk: RadioButton = findViewById(R.id.Lk)
        val spinnerPilihan: Spinner = findViewById(R.id.spinJur)

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
            try {
                val nama = inputNama.text.toString()
                val nisn = inputNisn.text.toString()
                val jurusan = spinnerPilihan.selectedItem.toString()
                val jenisKelamin = if (inputJk.isChecked) "Laki-Laki" else "Perempuan"

                val inputData = hashMapOf(
                    "nama" to nama,
                    "nisn" to nisn,
                    "jurusan" to jurusan,
                    "jenis_kelamin" to jenisKelamin)

                db.collection("datasiswa")
                    .document(nisn)
                    .set(inputData)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                        inputNama.setText("")
                        inputNisn.setText("")
                        inputJk.isChecked = false
                        spinnerPilihan.setSelection(0)
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Data gagal ditambahkan", Toast.LENGTH_SHORT).show()
                    }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }

    }
}