package com.astrobyte.orion

import androidx.room.*

@Dao
interface AbsenSiswaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(absen: AbsenSiswa)

    @Query("SELECT * FROM absen_siswa WHERE tanggal = :tanggal")
    suspend fun getAbsensiByTanggal(tanggal: String): List<AbsenSiswa>

    @Query("SELECT * FROM absen_siswa WHERE nisn = :nisn")
    suspend fun getAbsensiByNisn(nisn: String): List<AbsenSiswa>

    @Update
    suspend fun update(absen: AbsenSiswa)

    @Delete
    suspend fun delete(absen: AbsenSiswa)
}
