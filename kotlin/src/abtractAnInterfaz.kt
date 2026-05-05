fun main(){
    val alum : Alumno2 = Alumno2("alan brito")
    print(alum.darnombre())

}
//class abtract -- no intancia pero si se hacer puede herredar.
abstract class Personass{
    abstract fun darNombreDetallado()
}

class Alumno(var nombre:String):Personass(){
    override fun darNombreDetallado(){
        println("dar nombre a alumno: $nombre")
    }
}



//intefaz -- son contratos.
interface Personass2{
    //lista de funciones abstract
    //las clausulas del contrato.

    fun darnombre()
    fun correr()

}

class Alumno2(val nombre:String): Personass(), Personass2{
    override fun darNombreDetallado(){
        println("dar nombre a alumno detallado: $nombre")
    }

    override fun darnombre() {
        println("nombresssss")
        //TODO("Not yet implemented")
    }

    override fun correr() {
        //TODO("Not yet implemented")
    }
}