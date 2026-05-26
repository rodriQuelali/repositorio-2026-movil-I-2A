package com.example.myaplicactionui_codeexamplo.data

//Todo, Documentacion

class Calculadora (var num1: String, var num2: String){
    //Any depende de lo que se le pase
    //dos formas
    //1era es hacer desde constrcutor.
    //sobrecarga de metodo suma


    //3,45 -- serapar los digitos
    //

    //se encontro 2 bug
    // 1, mi primer bug, solo se coloca una sola coma
    // 2, segundo bug, 3,1 + 3,9 = 7 entero no 7,0

    fun suma(): String{
        var validar = validarDatos()
        var n1 = validar[0].toFloat()
        var n2 = validar[1].toFloat()
        var resulFinal = convertirDatos((n1 + n2).toString()).toString()

        return resulFinal.toString()
        //return this.num1.toString().toFloat() + this.num2.toString().toFloat()
    }

    fun validarDatos(): MutableList<String>{
        if(this.num1.contains(",") || this.num2.contains(",")){
            //3,4 +
            var listDataNu: MutableList<String> = mutableListOf()
            val nuevoNumero = this.num1.replace(",",".")
            val nuevoNumero2 = this.num2.replace(",",".")

            var validador = convertirDatosEspecial(nuevoNumero)
            listDataNu.add(validador.toString())

            validador = convertirDatosEspecial(nuevoNumero2)
            listDataNu.add(validador.toString())

            return listDataNu
        }
        return mutableListOf(this.num1, this.num2)
    }

    fun convertirDatosEspecial(a:String): Float{
        return a.toFloat()
    }

    fun convertirDatos(a:String):Any{

        //soloucionar el bug. de 4 + 5 = 9.0, no se tien que dar eso.

        if(a.contains(".")){
            return a.toFloat()
        }
        return a.toInt()
    }


    //desde los metodos sin el constructor
    //podemos hacer la sobrecarga de metodos.



}

