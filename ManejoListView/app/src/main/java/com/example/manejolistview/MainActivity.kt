package com.example.manejolistview

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.manejolistview.data.Pais

class MainActivity : AppCompatActivity() {
    //varibles globales

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val paisesList: MutableList<Pais> = mutableListOf()
        val nombresPaises: MutableList<String> = mutableListOf()
        val adaptador1 = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nombresPaises)

        val tv1 = findViewById<TextView>(R.id.tv1)
        val list1 = findViewById<ListView>(R.id.list1)
        list1.adapter = adaptador1

        val btnAgregarPais = findViewById<Button>(R.id.btnAgregarPais)
        val txtPais = findViewById<EditText>(R.id.txtPais)
        val txtHabitantes = findViewById<EditText>(R.id.txtHabitantes)

        btnAgregarPais.setOnClickListener {
            val nombre = txtPais.text.toString()
            val habStr = txtHabitantes.text.toString()

            if (nombre.isNotEmpty() && habStr.isNotEmpty()) {
                val nuevoPais = Pais(nombre, habStr.toLong())
                paisesList.add(nuevoPais)
                nombresPaises.add(nuevoPais.getNombre())
                adaptador1.notifyDataSetChanged()

                // Limpiar campos
                txtPais.text.clear()
                txtHabitantes.text.clear()
            }
        }

        list1.setOnItemClickListener { _, _, i, _ ->
            val p = paisesList[i]
            tv1.text = "Población de ${p.getNombre()}: ${p.getHabitantes()} habitantes"
        }
    }
}