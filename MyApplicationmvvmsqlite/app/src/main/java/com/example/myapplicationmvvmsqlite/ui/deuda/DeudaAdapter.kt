package com.example.myapplicationmvvmsqlite.ui.deuda

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplicationmvvmsqlite.data.local.relation.DeudaConCliente
import com.example.myapplicationmvvmsqlite.databinding.ItemDeudaBinding

class DeudaAdapter(
    private val onClick: (DeudaConCliente) -> Unit
) : ListAdapter<DeudaConCliente, DeudaAdapter.VH>(DIFF) {

    inner class VH(private val b: ItemDeudaBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(item: DeudaConCliente) {
            val d = item.deuda
            b.txtCliente.text = item.cliente.nombreCompleto
            b.txtMonto.text = d.monto.toMoneda()
            b.txtFechas.text = "Prestado:${d.fechaPrestamo.toFecha()}  ·  Cobro:${d.fechaCobro.toFecha()}"

            val (texto, color) = when {
                d.pagado -> "COBRADA" to Color.parseColor("#2E7D32")
                d.fechaCobro < System.currentTimeMillis() -> "VENCIDA" to Color.parseColor("#C62828")
                else -> "PENDIENTE" to Color.parseColor("#EF6C00")
            }
            b.chipEstado.text = texto
            b.chipEstado.setChipBackgroundColor(ColorStateList.valueOf(color))

            b.root.setOnClickListener { onClick(item) }
        }
    }

    override fun onCreateViewHolder(p: ViewGroup, t: Int) =
        VH(ItemDeudaBinding.inflate(LayoutInflater.from(p.context), p, false))

    override fun onBindViewHolder(h: VH, pos: Int) = h.bind(getItem(pos))

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<DeudaConCliente>() {
            override fun areItemsTheSame(a: DeudaConCliente, b: DeudaConCliente) =
                a.deuda.idDeuda == b.deuda.idDeuda
            override fun areContentsTheSame(a: DeudaConCliente, b: DeudaConCliente) = a == b
        }
    }
}