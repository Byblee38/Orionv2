package com.astrobyte.orion
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "datasiswa")
data class DataSiswa(
    @PrimaryKey var nisn: String,
    var nama: String,
    var jurusan: String,
    var jenisKelamin: String
) : Parcelable {
    constructor() : this("", "", "", "")
}