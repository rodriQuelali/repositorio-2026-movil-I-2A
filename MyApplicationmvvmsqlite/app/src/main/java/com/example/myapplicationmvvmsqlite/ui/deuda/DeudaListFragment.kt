package com.example.myapplicationmvvmsqlite.ui.deuda

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
import com.example.myapplicationmvvmsqlite.databinding.FragmentDeudaListBinding
import kotlinx.coroutines.launch
import kotlin.getValue

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DeudaListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DeudaListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null



    private var _b: FragmentDeudaListBinding? = null
    private val b get() = _b!!
    private val vm: DeudaViewModel by activityViewModels { AppViewModelProvider.Factory}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _b = FragmentDeudaListBinding.bind(view)

        val adapter = DeudaAdapter { item ->
            findNavController().navigate(
                R.id.action_deudas_to_detalle, bundleOf("deudaId" to item.deuda.idDeuda)
            )
        }
        b.recycler.layoutManager = LinearLayoutManager(requireContext())
        b.recycler.adapter = adapter

        b.chipGroup.setOnCheckedStateChangeListener { _, ids ->
            vm.setFiltro(when (ids.firstOrNull()) {
                R.id.chipVencidas -> FiltroDeuda.VENCIDAS
                R.id.chipCobradas -> FiltroDeuda.COBRADAS
                R.id.chipTodas -> FiltroDeuda.TODAS
                else -> FiltroDeuda.PENDIENTES
            })
        }
        b.fabAdd.setOnClickListener { findNavController().navigate(R.id.action_deudas_to_form) }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { vm.deudas.collect {
                    adapter.submitList(it); b.txtVacio.isVisible = it.isEmpty()
                } }
                launch { vm.totalPendiente.collect {
                    b.txtTotal.text = "Por cobrar:${it.toMoneda()}"
                } }
            }
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
        return inflater.inflate(R.layout.fragment_deuda_list, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DeudaListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DeudaListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}