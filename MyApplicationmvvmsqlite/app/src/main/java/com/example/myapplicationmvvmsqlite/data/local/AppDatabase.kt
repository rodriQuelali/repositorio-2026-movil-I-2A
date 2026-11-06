package com.example.myapplicationmvvmsqlite.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplicationmvvmsqlite.data.local.dao.ClienteDao
import com.example.myapplicationmvvmsqlite.data.local.dao.DeudaDao
import com.example.myapplicationmvvmsqlite.data.local.dao.PrestamistaDao
import com.example.myapplicationmvvmsqlite.data.local.entity.ClienteEntity
import com.example.myapplicationmvvmsqlite.data.local.entity.DeudaEntity
import com.example.myapplicationmvvmsqlite.data.local.entity.PrestamistaEntity

@Database(
    entities = [ClienteEntity::class, PrestamistaEntity::class, DeudaEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun clienteDao(): ClienteDao
    abstract fun prestamistaDao(): PrestamistaDao
    abstract fun deudaDao(): DeudaDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext, AppDatabase::class.java, "misprestamos.db"
                ).build().also { INSTANCE = it }
            }
    }
}