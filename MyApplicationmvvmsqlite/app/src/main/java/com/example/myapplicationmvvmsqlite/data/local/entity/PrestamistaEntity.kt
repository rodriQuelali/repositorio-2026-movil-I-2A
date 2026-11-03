package com.example.myapplicationmvvmsqlite.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "prestamista",
    indices = [Index(value = ["ci"], unique = true), Index(value = ["correo"], unique = true)]
)
data class PrestamistaEntity(
    @PrimaryKey(autoGenerate = true) val idPrestamista: Long = 0,
    val nombre: String,
    val apellido: String,
    val ci: String,
    val celular: String,
    val contrasena: String,      // se guarda "salt:hash", nunca texto plano
    val correo: String
)
