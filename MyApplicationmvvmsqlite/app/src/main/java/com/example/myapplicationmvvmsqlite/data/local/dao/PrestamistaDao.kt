package com.example.myapplicationmvvmsqlite.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplicationmvvmsqlite.data.local.entity.PrestamistaEntity

@Dao
interface PrestamistaDao {

    @Insert suspend fun insert(p: PrestamistaEntity): Long

    @Query("SELECT * FROM prestamista WHERE correo = :correo LIMIT 1")
    suspend fun getByCorreo(correo: String): PrestamistaEntity?

    @Query("SELECT COUNT(*) FROM prestamista WHERE correo = :correo OR ci = :ci")
    suspend fun existe(correo: String, ci: String): Int

    @Query("SELECT * FROM prestamista WHERE idPrestamista = :id")
    suspend fun getById(id: Long): PrestamistaEntity?

}