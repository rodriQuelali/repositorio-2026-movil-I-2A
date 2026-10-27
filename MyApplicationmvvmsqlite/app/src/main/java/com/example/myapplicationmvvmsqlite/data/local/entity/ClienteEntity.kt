package com.example.myapplicationmvvmsqlite.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "cliente", indices = [Index(value = ["ci"], unique = true)])
data class ClienteEntity(
    @PrimaryKey(autoGenerate = true) val idCliente: Long = 0,
    val nombre: String,
    val apellido: String,
    val ci: String,
    val celular: String,
    val fotoPath: String? = null,
    val latitud: Double? = null,
    val longitud: Double? = null

){
    val nombreCompleto get() = "$nombre$apellido"
}


