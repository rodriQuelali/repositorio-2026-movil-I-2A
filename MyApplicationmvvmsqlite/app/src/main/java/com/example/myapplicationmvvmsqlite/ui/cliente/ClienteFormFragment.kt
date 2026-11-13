package com.example.myapplicationmvvmsqlite.ui.cliente

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.myapplicationmvvmsqlite.AppViewModelProvider
import com.example.myapplicationmvvmsqlite.R
import com.example.myapplicationmvvmsqlite.databinding.FragmentClienteFormBinding
import com.example.myapplicationmvvmsqlite.ui.UiEvent
import com.example.myapplicationmvvmsqlite.utils.FileUtils
import kotlinx.coroutines.launch
import java.io.File

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ClienteFormFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ClienteFormFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _b: FragmentClienteFormBinding? = null
    private val b get() = _b!!
    private val vm: ClienteViewModel by activityViewModels { AppViewModelProvider.Factory }

    private val args: ClienteFormFragmentArgs by navArgs()

    private lateinit var fused: FusedLocationProviderClient
    private var cameraFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    // ---------- Launchers (propiedades de la clase) ----------
    private val reqCamera = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { ok -> if (ok) abrirCamara() else toast("Permiso de cámara denegado") }

    private val reqLocation = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { r ->
        if (r[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
            r[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        ) obtenerUbicacion() else toast("Permiso de ubicación denegado")
    }

    private val takePicture = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { ok -> if (ok) vm.setFoto(cameraFile?.absolutePath) else cameraFile?.delete() }

    private val pickMedia = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri -> uri?.let { vm.setFoto(FileUtils.copyUriToInternal(requireContext(), it)) } }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _b = FragmentClienteFormBinding.bind(view)
        fused = LocationServices.getFusedLocationProviderClient(requireActivity())

        vm.cargar(args.clienteId)          // -1 = cliente nuevo

        b.btnCamara.setOnClickListener { pedirCamara() }
        b.btnGaleria.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
        b.btnGps.setOnClickListener { pedirUbicacion() }
        b.btnGuardar.setOnClickListener { guardar() }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { vm.draft.collect { d ->
                    b.imgFoto.load(d.fotoPath)
                    b.txtGps.text = if (d.latitud != null)
                        "📍 %.5f, %.5f".format(d.latitud, d.longitud) else "Sin ubicación"
                } }
                launch { vm.clienteCargado.collect { c ->       // rellenar al editar
                    c?.let {
                        if (b.edtNombre.text.isNullOrEmpty()) {
                            b.edtNombre.setText(it.nombre); b.edtApellido.setText(it.apellido)
                            b.edtCi.setText(it.ci);         b.edtCelular.setText(it.celular)
                        }
                    }
                } }
                launch { vm.events.collect {
                    when (it) {
                        UiEvent.Saved -> findNavController().popBackStack()
                        is UiEvent.Message -> toast(it.text)
                    }
                } }
            }
        }
    }

    // ---------- Cámara ----------
    private fun pedirCamara() {
        val ok = ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
        if (ok) abrirCamara() else reqCamera.launch(Manifest.permission.CAMERA)
    }

    private fun abrirCamara() {
        val file = FileUtils.createImageFile(requireContext()).also { cameraFile = it }
        val uri = FileProvider.getUriForFile(
            requireContext(), "${requireContext().packageName}.fileprovider", file
        )
        takePicture.launch(uri)
    }

    // ---------- GPS ----------
    private fun pedirUbicacion() {
        val ok = ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        if (ok) obtenerUbicacion() else reqLocation.launch(
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
        )
    }

    @SuppressLint("MissingPermission")
    private fun obtenerUbicacion() {
        b.txtGps.text = "Obteniendo ubicación…"
        fused.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
            .addOnSuccessListener { loc ->
                if (loc != null) vm.setUbicacion(loc.latitude, loc.longitude)
                else toast("No se pudo obtener la ubicación. ¿GPS activado?")
            }
            .addOnFailureListener { toast("Error:${it.message}") }
    }

    // ---------- Guardar ----------
    private fun guardar() {
        val nombre = b.edtNombre.text.toString().trim()
        val apellido = b.edtApellido.text.toString().trim()
        val ci = b.edtCi.text.toString().trim()
        val celular = b.edtCelular.text.toString().trim()

        if (nombre.isEmpty()) { b.edtNombre.error = "Obligatorio"; return }
        if (apellido.isEmpty()) { b.edtApellido.error = "Obligatorio"; return }
        if (ci.isEmpty()) { b.edtCi.error = "Obligatorio"; return }
        if (celular.length < 7) { b.edtCelular.error = "Celular inválido"; return }

        vm.guardar(nombre, apellido, ci, celular)
    }

    override fun onDestroyView() { _b = null; super.onDestroyView() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cliente_form, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ClienteFormFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ClienteFormFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}