package com.astrobyte.orion

import androidx.room.*

@Dao
interface DataSiswaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(siswa: DataSiswa)

    @Query("SELECT * FROM datasiswa")
    suspend fun getAll(): List<DataSiswa>

    @Delete
    suspend fun delete(siswa: DataSiswa)

    @Update
    suspend fun update(siswa: DataSiswa)
}
