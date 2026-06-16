package com.example.myaplicactionui_codeexamplo.data

/**
* Una calculadora que maneja las operaciones basicas, de la region latinoamericano
* Formato latinoamericao, normalizacion de forma: 3,45 -> 3.45
*
* @property num1 es cadena de texto
* @property num2 es cadena de texto
*
* @constructor creaacion de la intanacia de la clase
*
* @author 2do A - 2026
*
* @version 1.0
* */

class Calculadora (
    private var num1: String,
    private var num2: String,
    private var formatoLatam: FormatoLatam = FormatoLatam()
    ){

    /**
     * Suma de dos numeros
     * @return suma de de lso dos numeros en forma de cadena de texto*/
    fun suma(): String{
        var validar = formatoLatam.validarDatos(num1, num2)
        var n1 = validar[0].toFloat()
        var n2 = validar[1].toFloat()
        var re = n1 + n2

        return formatoLatam.convertirDatos(re.toString()).toString()
    }

    fun resta(): String{
        var validar = formatoLatam.validarDatos(num1, num2)
        var n1 = validar[0].toFloat()
        var n2 = validar[1].toFloat()
        var re = n1 - n2

        return formatoLatam.convertirDatos(re.toString()).toString()
    }

}

//clase con static
 class Calculadora2{
     companion object{
         fun suma(num1: String, num2: String, formatoLatam: FormatoLatam = FormatoLatam()): String{
             var validar = formatoLatam.validarDatos(num1, num2)
             var n1 = validar[0].toFloat()
             var n2 = validar[1].toFloat()
             var re = n1 + n2

             return formatoLatam.convertirDatos(re.toString()).toString()
         }
     }
 }

