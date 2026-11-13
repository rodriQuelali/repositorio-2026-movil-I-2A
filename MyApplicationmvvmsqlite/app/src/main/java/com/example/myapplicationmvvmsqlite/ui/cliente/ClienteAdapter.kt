package com.example.myapplicationmvvmsqlite.ui.cliente

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplicationmvvmsqlite.data.local.entity.ClienteEntity
import com.example.myapplicationmvvmsqlite.databinding.ItemClienteBinding

class ClienteAdapter(
    private val onClick: (ClienteEntity) -> Unit,
    private val onLongClick: (ClienteEntity) -> Unit
) : ListAdapter<ClienteEntity, ClienteAdapter.VH>(DIFF) {

    inner class VH(private val b: ItemClienteBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(c: ClienteEntity) {
            b.txtNombre.text = c.nombreCompleto
            b.txtCi.text = "CI:${c.ci}"
            b.txtCelular.text = "📞${c.celular}"
            b.imgGps.isVisible = c.latitud != null
            b.imgFoto.load(c.fotoPath) {
                placeholder(android.R.drawable.ic_menu_gallery)
                error(android.R.drawable.ic_menu_gallery)
            }
            b.root.setOnClickListener { onClick(c) }
            b.root.setOnLongClickListener { onLongClick(c); true }
        }
    }

    override fun onCreateViewHolder(p: ViewGroup, t: Int) =
        VH(ItemClienteBinding.inflate(LayoutInflater.from(p.context), p, false))

    override fun onBindViewHolder(h: VH, pos: Int) = h.bind(getItem(pos))

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<ClienteEntity>() {
            override fun areItemsTheSame(a: ClienteEntity, b: ClienteEntity) = a.idCliente == b.idCliente
            override fun areContentsTheSame(a: ClienteEntity, b: ClienteEntity) = a == b
        }
    }
}