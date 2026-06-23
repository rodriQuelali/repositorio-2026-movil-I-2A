package com.example.myaplicactionui_codeexamplo

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myaplicactionui_codeexamplo.data.Calculadora
import com.example.myaplicactionui_codeexamplo.data.Calculadora2
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    var auxASignacion:String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //clase R, puente entre xml y la clase KT
        //realizando casting
        //creando el objeto.
        val txtEntra: TextInputEditText = findViewById(R.id.txtEntrada)
        val tvView = findViewById<TextView>(R.id.tvView)

        //todos los numeros
        val btn1 = findViewById<Button>(R.id.btn1)
        val btn2 = findViewById<Button>(R.id.btn2)
        val btn3 = findViewById<Button>(R.id.btn3)
        val btn4 = findViewById<Button>(R.id.btn4)
        val btn5 = findViewById<Button>(R.id.btn5)
        val btn6 = findViewById<Button>(R.id.btn6)
        val btn7 = findViewById<Button>(R.id.btn7)
        val btn8 = findViewById<Button>(R.id.btn8)
        val btn9 = findViewById<Button>(R.id.btn9)
        val btn0 = findViewById<Button>(R.id.btn0)
        val btnComa = findViewById<Button>(R.id.btnComa)
        val btnIgual = findViewById<Button>(R.id.btnIgual)

        val btnSuma = findViewById<Button>(R.id.btnSuma)
        val resul = findViewById<TextView>(R.id.tvResultado)

        val btnResta = findViewById<Button>(R.id.btnResta)
        val btnMulti = findViewById<Button>(R.id.btnMulti)
        val btnDivision = findViewById<Button>(R.id.btnDivision)

        val rbLatam = findViewById<RadioButton>(R.id.rbLatam)
        val rbUsa = findViewById<RadioButton>(R.id.rbUsa)

        rbLatam.setOnClickListener {
            if(rbLatam.isChecked){
                //operaciones con formato latam
            }
        }

        rbUsa.setOnClickListener {
            if(rbUsa.isChecked){
                //operaciones con formato latam
            }
        }


        //valor axiliar
        //1, 12

        var operadores:String = ""

        var n1:String = ""
        var n2:String = ""


        //asignacion ade valor
        btn1.setOnClickListener {
            auxASignacion += "1"
            txtEntra.setText(auxASignacion)
        }
        btn2.setOnClickListener {
            auxASignacion += "2"
            txtEntra.setText(auxASignacion)
        }

        btn3.setOnClickListener {
            auxASignacion += "3"
            txtEntra.setText(auxASignacion)
        }

        btn4.setOnClickListener {
            auxASignacion += "4"
            txtEntra.setText(auxASignacion)
        }
        btn5.setOnClickListener {
            auxASignacion += "5"
            txtEntra.setText(auxASignacion)
        }
        btn6.setOnClickListener {
            auxASignacion += "6"
            txtEntra.setText(auxASignacion)
        }
        btn7.setOnClickListener {
            auxASignacion += "7"
            txtEntra.setText(auxASignacion)
        }
        btn8.setOnClickListener {
            auxASignacion += "8"
            txtEntra.setText(auxASignacion)
        }
        btn9.setOnClickListener {
            auxASignacion += "9"
            txtEntra.setText(auxASignacion)
        }
        btn0.setOnClickListener {
            auxASignacion += "0"
            txtEntra.setText(auxASignacion)
        }
        btnComa.setOnClickListener {
            //validar si ya existe mas de una coma
            validarComaUnaSola(auxASignacion)
            txtEntra.setText(auxASignacion)
        }



        btnSuma.setOnClickListener {
            tvView.setText(auxASignacion + "+")
            n1 = auxASignacion
            operadores = "+"
            //operaciones
            auxASignacion = ""
            txtEntra.setText("")

        }

        btnResta.setOnClickListener {
            tvView.setText(auxASignacion + "-")
            n1 = auxASignacion
            operadores = "-"
            //operaciones
            auxASignacion = ""
            txtEntra.setText("")
        }

        btnMulti.setOnClickListener {
            // parceo de datos
            //resul.text = (num1.text.toString().toInt() * num2.text.toString().toInt()).toString()
        }

        btnDivision.setOnClickListener {
            // parceo de datos
            /*val n1= num1.text.toString().toInt()
            val n2= num2.text.toString().toInt()
            if(n2!=0){
                val res:Int = n1 / n2
                resul.text = res.toString()
            }else{
                resul.text = "No se puede dividir por cero"
            }*/

        }

        btnIgual.setOnClickListener {
            //when
            //division y multiplicacion.
            n2 = auxASignacion
            if(operadores == "+"){
                //suma
                tvView.setText(n1 + "+" + n2)
                resul.text = Calculadora(n1,n2).suma()
                //borra todo
                auxASignacion = ""
                txtEntra.setText("")
            }else if(operadores == "-"){
                //resta
                tvView.setText(n1 + "-" + n2)
                resul.text = Calculadora(n1,n2).resta()
                //borra todo
                auxASignacion = ""
                txtEntra.setText("")
            }
            operadores
        }
        //funciones como un collback
    }

    //funcion de validar coma
    fun validarComaUnaSola(a:String){
        var contador = a.count{it == ','}
        if(contador > 0){
            Toast.makeText(this, "No se puede ingresar mas de una coma", Toast.LENGTH_SHORT).show()
        }else{
            auxASignacion += ","
        }
    }

    //otra manera para capturar el evento click del boton
    //en sus propiedaes llamar seleccionar onclick para llamar al metodo  eventoOnclick()
    fun eventoOnclick(view: View){
        Toast.makeText(this, "Hola mundo", Toast.LENGTH_SHORT).show()
    }

    //funciones o procedimientos

}