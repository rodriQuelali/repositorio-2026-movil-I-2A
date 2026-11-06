package com.example.myapplicationmvvmsqlite.data.repository

import com.example.myapplicationmvvmsqlite.data.local.dao.ClienteDao
import com.example.myapplicationmvvmsqlite.data.local.entity.ClienteEntity
import com.example.myapplicationmvvmsqlite.data.local.entity.DeudaEntity

class ClienteRepository(private val dao: ClienteDao) {

    fun buscar(q: String) = dao.buscar(q)

    val todos =  dao.getAll()

    suspend fun getById(id: Long) = dao.getById(id)

    suspend fun insert(c: ClienteEntity) = dao.insert(c)

    suspend fun actualizar(c: ClienteEntity) = dao.update(c)

    suspend fun eliminar(c: ClienteEntity) = dao.delete(c)

}