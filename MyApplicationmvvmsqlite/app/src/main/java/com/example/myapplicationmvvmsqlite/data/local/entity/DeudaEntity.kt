package com.example.myapplicationmvvmsqlite.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "deuda",
    foreignKeys = [
        ForeignKey(
            entity = ClienteEntity::class,
            parentColumns = ["idCliente"], childColumns = ["idCliente"],
            onDelete = ForeignKey.RESTRICT
        ),
        ForeignKey(
            entity = PrestamistaEntity::class,
            parentColumns = ["idPrestamista"], childColumns = ["idPrestamista"],
            onDelete = ForeignKey.RESTRICT
        )
    ],
    indices = [Index("idCliente"), Index("idPrestamista")]
)
data class DeudaEntity(
    @PrimaryKey(autoGenerate = true) val idDeuda: Long = 0,
    val monto: Double,
    val fechaPrestamo: Long,     // millis
    val fechaCobro: Long,        // millis
    val idPrestamista: Long,
    val idCliente: Long,
    val pagado: Boolean = false
)

