package com.astrobyte.orion

import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SiswaAdapter(
    private val listSiswa: ArrayList<DataSiswa>,
    private val onDeleteClick: (DataSiswa) -> Unit = {},
    private val onUpdateClick: (DataSiswa) -> Unit
) : RecyclerView.Adapter<SiswaAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvnama: TextView = itemView.findViewById(R.id.nama)
        val tvnisn: TextView = itemView.findViewById(R.id.nisn)
        val tvjurusan: TextView = itemView.findViewById(R.id.jurusan)
        val tvjk: TextView = itemView.findViewById(R.id.jeniskelamin)
        val menuBtn: ImageView = itemView.findViewById(R.id.menuBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val siswa = listSiswa[position]
        holder.tvnama.text = siswa.nama
        holder.tvnisn.text = siswa.nisn
        holder.tvjurusan.text = siswa.jurusan
        holder.tvjk.text = siswa.jenisKelamin

        holder.menuBtn.setOnClickListener {
            val popup = PopupMenu(it.context, it)
            val inflater: MenuInflater = popup.menuInflater
            inflater.inflate(R.menu.option_menu, popup.menu)
            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_update -> {
                        onUpdateClick(siswa)
                        true
                    }
                    R.id.action_delete -> {
                        onDeleteClick(siswa)
                        true
                    }
                    else -> false
                }
            }
            popup.show()
        }
    }

    override fun getItemCount(): Int = listSiswa.size
}
