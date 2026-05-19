package com.example.myaplicactionui_codeexamplo

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myaplicactionui_codeexamplo.data.Calculadora
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {
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
        val num1 = findViewById<TextInputEditText>(R.id.txt1)
        //creando el objeto.
        val num2: TextInputEditText = findViewById(R.id.txt2)
        val btnSuma = findViewById<Button>(R.id.btnSuma)
        val resul = findViewById<TextView>(R.id.tvResultado)

        val btnResta = findViewById<Button>(R.id.btnResta)
        val btnMulti = findViewById<Button>(R.id.btnMulti)
        val btnDivision = findViewById<Button>(R.id.btnDivision)


        btnSuma.setOnClickListener {
            //2.5+45.2 = 47.7
            //2.5 +20 = 22.5
            //20+2.5 = 22.5

           // parceo de datos
            val n1= num1.text.toString()
            val n2= num2.text.toString()
            val calculadora = Calculadora(n1,n2)
            val res: String = calculadora.suma()
            resul.text = res

            //instamcia de la clase calculadora
            //Calculadra(2,5)
            //
        }

        btnResta.setOnClickListener {
            // parceo de datos
            resul.text = (num1.text.toString().toInt() -num2.text.toString().toInt()).toString()
        }

        btnMulti.setOnClickListener {
            // parceo de datos
            resul.text = (num1.text.toString().toInt() * num2.text.toString().toInt()).toString()
        }

        btnDivision.setOnClickListener {
            // parceo de datos
            val n1= num1.text.toString().toInt()
            val n2= num2.text.toString().toInt()
            if(n2!=0){
                val res:Int = n1 / n2
                resul.text = res.toString()
            }else{
                resul.text = "No se puede dividir por cero"
            }

        }

        //funciones como un collback
    }
    //otra manera para capturar el evento click del boton
    //en sus propiedaes llamar seleccionar onclick para llamar al metodo  eventoOnclick()
    fun eventoOnclick(view: View){
        Toast.makeText(this, "Hola mundo", Toast.LENGTH_SHORT).show()
    }

    //funciones o procedimientos

}