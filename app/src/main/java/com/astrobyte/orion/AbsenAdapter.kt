package com.astrobyte.orion

import android.view.*
import android.widget.*
import androidx.recyclerview.widget.*

// AbsenAdapter.kt
class AbsenAdapter(
    private val siswaList: List<DataSiswa>
) : RecyclerView.Adapter<AbsenAdapter.AbsenViewHolder>() {

    val statusMap = mutableMapOf<String, String>() // NISN -> status

    inner class AbsenViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val namaText: TextView = itemView.findViewById(R.id.namasiswaAbsen)
        val statusSpinner: Spinner = itemView.findViewById(R.id.spinnerAbsen)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbsenViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardisiabsen, parent, false)
        return AbsenViewHolder(view)
    }

    override fun onBindViewHolder(holder: AbsenViewHolder, position: Int) {
        val siswa = siswaList[position]
        holder.namaText.text = siswa.nama

        val options = listOf("Hadir", "Sakit", "Izin", "Alfa")
        val adapter = ArrayAdapter(holder.itemView.context, android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        holder.statusSpinner.adapter = adapter

        holder.statusSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                statusMap[siswa.nisn] = options[pos]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    override fun getItemCount(): Int = siswaList.size
}
