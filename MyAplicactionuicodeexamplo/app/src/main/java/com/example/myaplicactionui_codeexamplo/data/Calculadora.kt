package com.example.myaplicactionui_codeexamplo.data

class Calculadora (var num1: String, var num2: String){
    //Any depende de lo que se le pase
    //dos formas
    //1era es hacer desde constrcutor.
    //sobrecarga de metodo suma


    //3,45 -- serapar los digitos
    //
    fun suma(): String{
        //utlilizar metodo String
        if(this.num1.contains(",")){
            //realizar el parceo de datos
            //tiene coma, se tiene operar con float

            //"3,4" a float -- como ?
            //"3.5" a flot -- si puede realizar el parceo
            return "tiene coma"
        }
        return "no tien coma"
        //return this.num1.toString().toFloat() + this.num2.toString().toFloat()
    }


    //desde los metodos sin el constructor
    //podemos hacer la sobrecarga de metodos.



}

