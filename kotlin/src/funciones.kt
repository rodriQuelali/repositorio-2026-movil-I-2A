fun main(){
    //funciones..
    var aaa = mejoradaSuma(mutableListOf(2,4,6,8,11,13,17,105),20)
    //salida: [57, 79, 90]
    println(aaa)

   // println(sumas(23,45))

}

//funciones
//devulev la funcion,
//el tipado de datos: C# : public Int suma(){ return 2+2;}
//tarea para no utilizar la variable nuevalistas ni otra otra varibale, utilizar el a
fun suma(a: MutableList<Int>, b: Int):MutableList<Int>{
    //a.add(0, 25)
    //val nuevalistas: MutableList<Int> = mutableListOf()
    /*a.forEach {
        nuevalistas.add(it+b)
    }*/
    a.mapIndexed { index, i -> a.set(index, i+b) }
    return a
}
fun mejoradaSuma(a: MutableList<Int>, b: Int):MutableList<Int> {
    a.mapIndexed { index, i -> a.set(index, i+b) }
    return a
}




//procemientos
//no devule la funcion
//void suam(){
  //  system.prinl(2+2)
//}
fun sumas(a: Int, b: Int){
    print(a+b)
}