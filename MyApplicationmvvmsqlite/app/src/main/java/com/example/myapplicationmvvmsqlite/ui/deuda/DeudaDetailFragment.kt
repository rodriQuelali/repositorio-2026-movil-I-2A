package com.example.myapplicationmvvmsqlite.ui.deuda

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplicationmvvmsqlite.AppViewModelProvider
import com.example.myapplicationmvvmsqlite.R
import com.example.myapplicationmvvmsqlite.data.local.relation.DeudaConCliente
import com.example.myapplicationmvvmsqlite.databinding.FragmentDeudaDetailBinding
import com.example.myapplicationmvvmsqlite.databinding.FragmentDeudaListBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DeudaDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DeudaDetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _b: FragmentDeudaDetailBinding? = null
    private val b get() = _b!!
    private val vm: DeudaViewModel by activityViewModels { AppViewModelProvider.Factory }
    private val args: DeudaDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _b = FragmentDeudaDetailBinding.bind(view)
        vm.cargarDetalle(args.deudaId)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { vm.detalle.collect { item -> item?.let { pintar(it) } } }
                launch { vm.events.collect { if (it == UiEvent.Saved) findNavController().popBackStack() } }
            }
        }
    }

    private fun pintar(item: DeudaConCliente) {
        val d = item.deuda
        val c = item.cliente

        b.imgCliente.load(c.fotoPath)
        b.txtCliente.text = c.nombreCompleto
        b.txtMonto.text = d.monto.toMoneda()
        b.txtFechas.text = "Prestado:${d.fechaPrestamo.toFecha()}\nCobro:${d.fechaCobro.toFecha()}"
        b.btnCobrada.isVisible = !d.pagado

        b.btnCobrada.setOnClickListener { vm.marcarPagada(d) }
        b.btnEliminar.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Eliminar deuda").setMessage("Esta acción no se puede deshacer.")
                .setNegativeButton("Cancelar", null)
                .setPositiveButton("Eliminar") { _, _ -> vm.eliminar(d) }
                .show()
        }

        // 📞 Llamar (sin permiso: solo abre el marcador)
        b.btnLlamar.setOnClickListener {
            startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:${c.celular}")))
        }

        // 🗺 Cómo llegar al domicilio guardado con GPS
        b.btnMapa.isEnabled = c.latitud != null
        b.btnMapa.setOnClickListener {
            val uri = Uri.parse("geo:${c.latitud},${c.longitud}?q=${c.latitud},${c.longitud}(${Uri.encode(c.nombreCompleto)})")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            if (intent.resolveActivity(requireContext().packageManager) != null) startActivity(intent)
            else toast("No hay una app de mapas instalada")
        }
    }

    override fun onDestroyView() { _b = null; super.onDestroyView() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_deuda_detail, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DeudaDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DeudaDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}