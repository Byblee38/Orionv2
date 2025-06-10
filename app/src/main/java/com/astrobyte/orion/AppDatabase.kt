package com.astrobyte.orion
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DataSiswa::class, AbsenSiswa::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dataSiswaDao(): DataSiswaDao
    abstract fun absenSiswaDao(): AbsenSiswaDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "datasiswa_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

