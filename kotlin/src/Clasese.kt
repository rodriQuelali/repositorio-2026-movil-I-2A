fun main(){
    val persona:Persona = Persona()
    persona.inicializar("alan", 23)
    //print(persona.imprimipersona())


    val estudiante:Estudiante = Estudiante("alan brito", 21)
    //print(estudiante.imprimipersona())

    val docente:Docente = Docente("Rodrigo Quelali", 21)
    //print(docente.imprimipersona())

    val carera:Carreras = Carreras("Sistemas informaticos", true)
    print(carera.getNombre())
}

//clases en kotlin
//forma antigua
open class Persona{
    var nombre:String = ""
    var edad: Int = 0

    //constructor
    fun inicializar(nombre: String, edad: Int) {
        this.nombre = nombre
        this.edad = edad
    }

    //metodos
    open fun caminar(){
    println("camina persona")
    }
}
//forma actual

class Estudiante constructor(nombre: String, edad: Int) {
    var nombre:String = nombre
    private var edad = edad

    //metodos
    fun imprimipersona(){
        println(nombre)
    }

}

//forma mas moderna de realizar el constructor.

class Docente constructor(val nombre: String, val edad: Int) {
    //metodos
    fun imprimipersona(){
        println(nombre)
    }
}

// privados propiedas
class Admnistrativos constructor(private val nombre: String, private val edad: Int) {

    //metodos privado
    private fun imprimipersona(){
        println(nombre)
    }
}

//set and get
class Carreras constructor(private var nombre: String, private var estado: Boolean){

    //funciones flechas  funcion de una sola linea
    fun getNombre(): String = nombre

    fun setNombre(nombre: String){
        this.nombre = nombre
    }
}

//herencia
//open = final
//cosntructor o ()
class Becados (val tipo:String): Persona(){

    //override --- sobre escritura de metos o prolimorfismo
    override fun caminar(){
        super.caminar()
        print("caminando becario")
    }
}
