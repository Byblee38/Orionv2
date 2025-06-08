package com.astrobyte.orion

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "absen_siswa")
data class AbsenSiswa(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nisn: String,
    val tanggal: String,
    var status: String
)
