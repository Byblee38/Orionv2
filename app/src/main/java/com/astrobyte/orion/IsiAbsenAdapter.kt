package com.astrobyte.orion

import android.content.Context
import android.view.*
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class IsiAbsenAdapter(
    private val context: Context,
    private val listAbsen: List<AbsenWithSiswa>
) : RecyclerView.Adapter<IsiAbsenAdapter.AbsenViewHolder>() {

    private val statusList = listOf("Hadir", "Izin", "Sakit", "Alfa")

    inner class AbsenViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val namaSiswa: TextView = itemView.findViewById(R.id.namasiswaAbsen)
        val spinner: Spinner = itemView.findViewById(R.id.spinnerAbsen)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbsenViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.cardisiabsen, parent, false)
        return AbsenViewHolder(view)
    }

    override fun onBindViewHolder(holder: AbsenViewHolder, position: Int) {
        val currentData = listAbsen[position]
        val absenSiswa = currentData.absen
        val siswa = currentData.siswa

        holder.namaSiswa.text = siswa.nama

        val adapter = ArrayAdapter(context, R.layout.spinner_item, statusList)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        holder.spinner.adapter = adapter

        val currentIndex = statusList.indexOf(absenSiswa.status)

        if (currentIndex != -1) {
            holder.spinner.setSelection(currentIndex)
        }

        holder.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                absenSiswa.status = statusList[pos]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    override fun getItemCount(): Int = listAbsen.size
}
