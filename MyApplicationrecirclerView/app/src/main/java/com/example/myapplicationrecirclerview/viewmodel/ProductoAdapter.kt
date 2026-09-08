package com.example.myapplicationrecirclerview.viewmodel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplicationrecirclerview.R
import com.example.myapplicationrecirclerview.model.Producto

class ProductoAdapter(
    private val listPro: List<Producto>
): RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductoViewHolder {
        //layuot
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto, parent,false)
        return ProductoViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ProductoViewHolder,
        position: Int
    ) {
        //val nombre = listPro[position]
        holder.tvN.text = "Nombre: ${listPro[position].getNombre()}"
        holder.tvP.text = "Precio: ${listPro[position].getPrecio().toString()}"
        holder.tvS.text = "Stock: ${listPro[position].getStock().toString()}"
    }

    override fun getItemCount(): Int = listPro.size

    class ProductoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
       //clase R. de la UI.
        val tvN = itemView.findViewById<TextView>(R.id.tvNombre)
        val tvP = itemView.findViewById<TextView>(R.id.tvPrecio)
        val tvS = itemView.findViewById<TextView>(R.id.tvStock)
    }


}