package com.example.myapplicationmvvmsqlite.ui.cliente

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
import com.example.myapplicationmvvmsqlite.databinding.FragmentClienteListBinding
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ClienteListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ClienteListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _b: FragmentClienteListBinding? = null
    private val b get() = _b!!
    private val vm: ClienteViewModel by activityViewModels { AppViewModelProvider.Factory }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _b = FragmentClienteListBinding.bind(view)

        val adapter = ClienteAdapter(
            onClick = { c ->   // editar
                findNavController().navigate(
                    R.id.action_clientes_to_form, bundleOf("clienteId" to c.idCliente)
                )
            },
            onLongClick = { c -> confirmarEliminar(c) }
        )
        b.recycler.layoutManager = LinearLayoutManager(requireContext())
        b.recycler.adapter = adapter

        b.edtBuscar.doAfterTextChanged { vm.buscar(it.toString()) }

        b.fabAdd.setOnClickListener {
            vm.limpiar()
            findNavController().navigate(R.id.action_clientes_to_form)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    vm.clientes.collect {
                        adapter.submitList(it)
                        b.txtVacio.isVisible = it.isEmpty()
                    }
                }
                launch {
                    vm.events.collect { if (it is UiEvent.Message) toast(it.text) }
                }
            }
        }
    }

    private fun confirmarEliminar(c: ClienteEntity) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Eliminar cliente")
            .setMessage("¿Eliminar a${c.nombreCompleto}?")
            .setNegativeButton("Cancelar", null)
            .setPositiveButton("Eliminar") { _, _ -> vm.eliminar(c) }
            .show()
    }

    override fun onDestroyView() { _b = null; super.onDestroyView() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cliente_list, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ClienteListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ClienteListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}