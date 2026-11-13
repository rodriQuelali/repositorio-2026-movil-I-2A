package com.example.myapplicationmvvmsqlite.ui.deuda

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.myapplicationmvvmsqlite.AppViewModelProvider
import com.example.myapplicationmvvmsqlite.R
import com.example.myapplicationmvvmsqlite.data.local.entity.ClienteEntity
import com.example.myapplicationmvvmsqlite.databinding.FragmentDeudaFormBinding
import com.example.myapplicationmvvmsqlite.ui.UiEvent
import com.example.myapplicationmvvmsqlite.ui.cliente.ClienteViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DeudaFormFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DeudaFormFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _b: FragmentDeudaFormBinding? = null
    private val b get() = _b!!
    private val vm: DeudaViewModel by activityViewModels { AppViewModelProvider.Factory }
    private val clienteVm: ClienteViewModel by activityViewModels { AppViewModelProvider.Factory }

    private var clienteElegido: ClienteEntity? = null
    private var fechaPrestamo: Long? = null
    private var fechaCobro: Long? = null
    private var listaClientes: List<ClienteEntity> = emptyList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _b = FragmentDeudaFormBinding.bind(view)

        // Dropdown de clientes
        b.dropCliente.setOnItemClickListener { _, _, pos, _ -> clienteElegido = listaClientes[pos] }

        b.edtFechaPrestamo.setOnClickListener {
            elegirFecha("Fecha del préstamo") { fechaPrestamo = it; b.edtFechaPrestamo.setText(it.toFecha()) }
        }
        b.edtFechaCobro.setOnClickListener {
            elegirFecha("Fecha de cobro") { fechaCobro = it; b.edtFechaCobro.setText(it.toFecha()) }
        }
        b.btnGuardar.setOnClickListener {
            vm.guardar(
                idCliente = clienteElegido?.idCliente,
                monto = b.edtMonto.text.toString().toDoubleOrNull(),
                fechaPrestamo = fechaPrestamo,
                fechaCobro = fechaCobro
            )
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    clienteVm.clientes.collect { lista ->
                        listaClientes = lista
                        b.dropCliente.setAdapter(
                            ArrayAdapter(
                                requireContext(), android.R.layout.simple_list_item_1,
                                lista.map { "${it.nombreCompleto} (${it.ci})" }
                            )
                        )
                    }
                }
                launch { vm.events.collect {
                    when (it) {
                        UiEvent.Saved -> findNavController().popBackStack()
                        is UiEvent.Message -> toast(it.text)
                    }
                } }
            }
        }
    }

    private fun elegirFecha(titulo: String, onOk: (Long) -> Unit) {
        MaterialDatePicker.Builder.datePicker().setTitleText(titulo).build().apply {
            addOnPositiveButtonClickListener { onOk(it) }
        }.show(childFragmentManager, "date")
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
        return inflater.inflate(R.layout.fragment_deuda_form, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DeudaFormFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DeudaFormFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}