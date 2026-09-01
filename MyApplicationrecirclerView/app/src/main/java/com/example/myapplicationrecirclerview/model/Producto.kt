package com.example.myapplicationrecirclerview.model

class Producto(
    //private val id:Int?,
    private val nombre:String,
    private val precio:Int,
    private val stock:Int
) {
    //metodos
    //calcuar el precio de venta al 12%
    fun getNombre():String = this.nombre

    fun getPrecio():Int = this.precio

    fun getStock():Int = this.stock

}