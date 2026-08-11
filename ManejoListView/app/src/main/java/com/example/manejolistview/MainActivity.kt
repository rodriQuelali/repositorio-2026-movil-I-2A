package com.example.manejolistview

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.manejolistview.data.Pais

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



        val tv1=findViewById<TextView>(R.id.tv1)
        val list1=findViewById<ListView>(R.id.list1)

        //pop
        //push

        val paisListObejct: MutableList<String> = mutableListOf()
        val pais: Pais = Pais("BOLIVA")
        val chile: Pais = Pais("CHILE")
        paisListObejct.add(pais.getNombre())
        paisListObejct.add(chile.getNombre())





        val paises = arrayOf( "Argentina", "Chile", "Paraguay", "Bolivia", "Peru", "Ecuador", "Brasil", "Colombia", "Venezuela", "Uruguay")
        var habitantes = arrayOf(40_000_000, 17_000_000, 6_500_000, 10_000_000, 30_000_000, 14_000_000, 183_000_000, 44_000_000, 31_000_000, 3_500_000)
        val adaptador1 = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, paisListObejct)
        list1.adapter = adaptador1
        list1.setOnItemClickListener { adapterView, view, i, l ->
            tv1.text = "Población de: ${habitantes[i]}"
        }
    }
}