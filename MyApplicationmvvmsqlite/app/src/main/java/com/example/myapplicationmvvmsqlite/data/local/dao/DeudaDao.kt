package com.example.myapplicationmvvmsqlite.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.myapplicationmvvmsqlite.data.local.entity.DeudaEntity
import com.example.myapplicationmvvmsqlite.data.local.relation.DeudaConCliente
import kotlinx.coroutines.flow.Flow

@Dao
interface DeudaDao {

    @Transaction
    @Query("SELECT * FROM deuda WHERE idPrestamista = :idPrestamista ORDER BY fechaPrestamo ASC")
    fun getDeudas(idPrestamista: Long): Flow<List<DeudaConCliente>>

    @Transaction
    @Query("SELECT * FROM deuda WHERE idDeuda = :id")
    suspend fun getById(id: Long): DeudaConCliente?

    @Query("SELECT COALESCE(SUM(monto), 0) FROM deuda WHERE idPrestamista = :idPrestamista AND pagado = 0")
    fun totalPendiente(idPrestamista: Long): Flow<Double>

    @Insert suspend fun insert(d: DeudaEntity): Long
    @Update suspend fun update(d: DeudaEntity)
    @Delete suspend fun delete(d: DeudaEntity)
}