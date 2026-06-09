package com.example.myaplicactionui_codeexamplo.data

class FormatoLatam {

    /**
     * Valida y normaliza los datos que tiene "," por "."
     * @return Una lista de cadenas de texto con . en vez de ,
     */
    fun validarDatos(a:String, b:String): MutableList<String>{
        if(a.contains(",") || b.contains(",")){

            var listDataNu: MutableList<String> = mutableListOf()
            val nuevoNumero = a.replace(",",".")
            val nuevoNumero2 = b.replace(",",".")

            listDataNu.add(nuevoNumero.toFloat().toString())

            listDataNu.add(nuevoNumero2.toFloat().toString())

            return listDataNu
        }

        return mutableListOf(a, b)
    }

    /**
     * Convierte una cadena de texto a flotante
     * @property a es cadena de texto
     * @return flotante
     */
    fun convertirDatosEspecial(a:String): Float{
        return a.toFloat()
    }

    /**
     * Procesa el resultado de la opracion y convierte a float o int
     *
     * caso 1: 7.0 , elimina el .0, devuelve entero [Int]
     * caso 2: 2.5, se mantien en decimal, devuielve decimal [Float] pero con formato [String]
     * @property a es cadena de texto.
     * @return un tipo [Any] que ser [Int] o [String] lo que corresponde.
     */
    fun convertirDatos(a:String):Any{

        if(a.toFloat() % 1 == 0f){
            val aux = a.toFloat()
            return aux.toInt()
        }

        return a.replace(".",",")
    }

    fun convertirComa(a:String):String = a.replace(".",",")

}