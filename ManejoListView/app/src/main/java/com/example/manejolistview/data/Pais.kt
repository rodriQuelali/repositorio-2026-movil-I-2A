package com.example.manejolistview.data

class Pais (
    private val nombre: String,
    private val habitantes: Long
){
    fun getNombre(): String = nombre
    fun getHabitantes(): Long = habitantes
}