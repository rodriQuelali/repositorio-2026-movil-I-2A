package com.example.myapplicationmvvmsqlite.data.repository

import com.example.myapplicationmvvmsqlite.data.local.dao.PrestamistaDao
import com.example.myapplicationmvvmsqlite.data.local.entity.PrestamistaEntity
import com.example.myapplicationmvvmsqlite.utils.PasswordUtils

class PrestamistaRepository(private val dao: PrestamistaDao) {

    suspend fun registrar(p: PrestamistaEntity): Result<Long> {
        if (dao.existe(p.correo, p.ci) > 0)
            return Result.failure(Exception("El correo o CI ya está registrado"))
        return Result.success(dao.insert(p.copy(contrasena = PasswordUtils.hash(p.contrasena))))
    }

    suspend fun login(correo: String, contrasena: String): PrestamistaEntity? {
        val p = dao.getByCorreo(correo) ?: return null
        return if (PasswordUtils.verify(contrasena, p.contrasena)) p else null
    }
}