fun main(){
    //funciones..
    var a = suma(mutableListOf(23,45,56),34)
    //salida: [57, 79, 90]
    println(a)

    println(sumas(23,45))

}

//funciones
//devulev la funcion,
//el tipado de datos: C# : public Int suma(){ return 2+2;}
//tarea para no utilizar la variable nuevalistas ni otra otra varibale, utilizar el a
fun suma(a: MutableList<Int>, b: Int):MutableList<Int>{
    //a.add(0, 25)
    val nuevalistas: MutableList<Int> = mutableListOf()
    a.forEach {
        nuevalistas.add(it+b)
    }
    return nuevalistas
}

//procemientos
//no devule la funcion
//void suam(){
  //  system.prinl(2+2)
//}
fun sumas(a: Int, b: Int){
    print(a+b)
}