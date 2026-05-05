fun main(){
    //condcioles

    //if -- normal
    var a = 2
    /*if(a == 0){
        print("correcto")
    }
    if (a> 0){
        print("es mayor a 0")
    }*/

    //if con el else
    a = 2
    /*if(a == 0){
        print("correcto")
    }else{
        print("es mayor a 0")
    }*/

    //if anidado
    a = 2
    /*if(a == 0){
        print("correcto")
    }else if(a>0){
        print("es mayor a 0")
    }else{
        print("no entra al rango")
    }*/

    //if ternario
    //print(if(a>0) "es mayor a 0 ternario" else "no entra no encontrado ternario")
    //console.write(a > 0 ? "es mayor a 0" : "no entra no encontrado")

    // es el remplazo de CASE o swith.
    // a = 0 o 1, mensaje de el numero es menor, pero si es mayor de  e igual que 2 , es mayor
    a = -1
    /*when{
        a >= 0 && a <= 1  -> print("es menor")
        a>=2 -> print("$a es mayor a 2")
        a< 0 -> print("$a es negativo")
    }*/

    a = 50
    var b = 75
    var c = 1

    when{
        a>b && a>c && c>b -> print("el amyor es $a, desendente: $a, $c, $b")
        a>b && a>c && b>c -> print("el amyor es $a, desendente: $a, $b, $c")
        //b
        b>a && b>c && a>c -> print("el amyor es $b, desendente: $b, $a, $c")
        b>a && b>c && c>a -> print("el amyor es $b, desendente: $b, $c, $a")
        //c
        c>a && c>b && b>a -> print("el amyor es $c, desendente: $c, $b, $a")
        c>a && c>b && a>b -> print("el amyor es $c, desendente: $c, $a, $b")

    }

}