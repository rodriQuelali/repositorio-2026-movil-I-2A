package com.example.myapplicationrecirclerview

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplicationrecirclerview.databinding.ActivityMainBinding
import com.example.myapplicationrecirclerview.model.Producto
import com.example.myapplicationrecirclerview.viewmodel.ProductoAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    // en la vista "--> layout -> activity_mains.xml - ActivityMainBinding
    //vista registro producti -> layout -> activity_producto.xml - ActivityProductoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*enableEdgeToEdge()
        setContentView(R.layout.activity_main)*/
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //codigo
        /*val listaOb = mutableListOf<Producto>(
            Producto("laptop", 1500, 10),
            Producto("Monitor", 500, 5)
        )*/
        val listaOb = mutableListOf<Producto>()

        binding.rclProducto.layoutManager = LinearLayoutManager(this)
        val adapterProducto = ProductoAdapter(listaOb)
        binding.rclProducto.adapter = adapterProducto

        binding.btnAgregarP.setOnClickListener {
            val producto = Producto(
                binding.txtNombreP.text.toString(),
                binding.txtPrecio.text.toString().toInt(),
                binding.txtStock.text.toString().toInt()
            )
            listaOb.add(producto)
            binding.rclProducto.adapter = adapterProducto
        }


    }
}