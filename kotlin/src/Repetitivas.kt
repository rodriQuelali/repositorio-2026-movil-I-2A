fun main(){
    //while
    var c = 0
    /*while(c < 5){
        println(c)
        c++
    }*/

    //do
    /*do {
        //primero se ejecuta y luego realiza la condicion
        println(c)
    }while (c < 5)*/

    //ejemplo con for
    // for

    //for(int i=0; i<10; i++)

    /*for (i in c downTo 0 step 2){
        println(c)
    }*/
    var listaCurso = listOf<String>("1ro A", "2do A", "3do A")

    //var n: Int = readln().toInt()
    //for (i in 1..n){
        //println(listaCurso[i])
    //}


    //for ech
    //version mejorada de for, mas utilizado en listaso listamutables

    //for(listaCursos in listaCurso){
        //println ("$listaCursos")
    //}

    listaCurso.forEach {
        println ("$it")
    }


    //funciones y procedmiento en kotlin , con parametros. 


}