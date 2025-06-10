package com.astrobyte.orion

import android.view.*
import android.widget.*
import androidx.core.content.ContextCompat
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

        val options = listOf("Hadir", "Sakit", "Izin", "Alpa")
        val adapter = object : ArrayAdapter<String>(holder.itemView.context, R.layout.spinner_item, options) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.spinner_item, parent, false)
                val textView = view.findViewById<TextView>(R.id.text_spinner)
                val status = getItem(position)
                textView.text = status

                when (status) {
                    "Alpa" -> {
                        textView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_red_light))
                        textView.setTextColor(ContextCompat.getColor(context, android.R.color.white))
                    }
                    "Hadir" -> {
                        textView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_blue_light))
                        textView.setTextColor(ContextCompat.getColor(context, android.R.color.white))
                    }
                    "Izin" -> {
                        textView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_green_light))
                        textView.setTextColor(ContextCompat.getColor(context, android.R.color.white))
                    }
                    "Sakit" -> {
                        textView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_orange_light))
                        textView.setTextColor(ContextCompat.getColor(context, android.R.color.white))
                    }
                }
                return view
            }

            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.spinner_dropdown_item, parent, false)
                val textView = view.findViewById<TextView>(R.id.text_spinner)
                val status = getItem(position)
                textView.text = status

                when (status) {
                    "Alpa" -> {
                        textView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_red_light))
                        textView.setTextColor(ContextCompat.getColor(context, android.R.color.white))
                    }
                    "Hadir" -> {
                        textView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_blue_light))
                        textView.setTextColor(ContextCompat.getColor(context, android.R.color.white))
                    }
                    "Izin" -> {
                        textView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_green_light))
                        textView.setTextColor(ContextCompat.getColor(context, android.R.color.white))
                    }
                    "Sakit" -> {
                        textView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_orange_light))
                        textView.setTextColor(ContextCompat.getColor(context, android.R.color.white))
                    }
                }
                return view
            }
        }

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
