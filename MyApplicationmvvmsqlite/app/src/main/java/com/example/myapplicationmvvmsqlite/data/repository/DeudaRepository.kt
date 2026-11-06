package com.example.myapplicationmvvmsqlite.data.repository

import com.example.myapplicationmvvmsqlite.data.local.dao.DeudaDao
import com.example.myapplicationmvvmsqlite.data.local.entity.DeudaEntity

class DeudaRepository(private val dao: DeudaDao) {
    fun deudas(idPrestamista: Long) = dao.getDeudas(idPrestamista)
    fun totalPendiente(idPrestamista: Long) = dao.totalPendiente(idPrestamista)
    suspend fun getById(id: Long) = dao.getById(id)
    suspend fun insertar(d: DeudaEntity) = dao.insert(d)
    suspend fun actualizar(d: DeudaEntity) = dao.update(d)
    suspend fun eliminar(d: DeudaEntity) = dao.delete(d)
}