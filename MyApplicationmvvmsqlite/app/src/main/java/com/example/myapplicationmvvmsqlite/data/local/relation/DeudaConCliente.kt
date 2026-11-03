package com.example.myapplicationmvvmsqlite.data.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.myapplicationmvvmsqlite.data.local.entity.ClienteEntity
import com.example.myapplicationmvvmsqlite.data.local.entity.DeudaEntity

data class DeudaConCliente(
    @Embedded val deuda: DeudaEntity,
    @Relation(parentColumn = "idCliente", entityColumn = "idCliente")
    val cliente: ClienteEntity
)
