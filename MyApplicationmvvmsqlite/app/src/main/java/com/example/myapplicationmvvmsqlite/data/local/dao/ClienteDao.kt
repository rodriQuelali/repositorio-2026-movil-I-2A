package com.example.myapplicationmvvmsqlite.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.myapplicationmvvmsqlite.data.local.entity.ClienteEntity
import kotlinx.coroutines.flow.Flow


/**
 * Si se necesita realizar otra consulta o un metodo de la
 * tabla cliente se realiza aqui.*/
@Dao
interface ClienteDao {

    @Query(
        """SELECT * FROM cliente
           WHERE nombre LIKE '%' || :q || '%'
              OR apellido LIKE '%' || :q || '%'
              OR ci LIKE '%' || :q || '%'
           ORDER BY apellido, nombre"""
    )
    fun buscar(q: String): Flow<List<ClienteEntity>>

    @Query("SELECT * FROM cliente ORDER BY apellido, nombre")
    fun getAll(): Flow<List<ClienteEntity>>

    @Query("SELECT * FROM cliente WHERE idCliente = :id")
    suspend fun getById(id: Long): ClienteEntity?

    @Insert suspend fun insert(c: ClienteEntity): Long
    @Update suspend fun update(c: ClienteEntity)
    @Delete suspend fun delete(c: ClienteEntity)
}