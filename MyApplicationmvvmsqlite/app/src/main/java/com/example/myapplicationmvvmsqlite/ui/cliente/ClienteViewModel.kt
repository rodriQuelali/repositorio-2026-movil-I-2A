package com.example.myapplicationmvvmsqlite.ui.cliente

import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplicationmvvmsqlite.data.local.entity.ClienteEntity
import com.example.myapplicationmvvmsqlite.data.repository.ClienteRepository
import com.example.myapplicationmvvmsqlite.ui.UiEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ClienteDraft(
    val id: Long = 0,
    val fotoPath: String? = null,
    val latitud: Double? = null,
    val longitud: Double? = null
)

@OptIn(ExperimentalCoroutinesApi::class)
class ClienteViewModel(private val repo: ClienteRepository) : ViewModel() {

    // --- Lista con búsqueda ---
    private val query = MutableStateFlow("")
    val clientes: StateFlow<List<ClienteEntity>> = query
        .flatMapLatest { repo.buscar(it.trim()) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun buscar(texto: String) { query.value = texto }

    // --- Borrador del formulario (sobrevive a rotación) ---
    private val _draft = MutableStateFlow(ClienteDraft())
    val draft: StateFlow<ClienteDraft> = _draft

    private val _events = Channel<UiEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    private val _clienteCargado = MutableStateFlow<ClienteEntity?>(null)
    val clienteCargado: StateFlow<ClienteEntity?> = _clienteCargado

    fun setFoto(path: String?) { _draft.update { it.copy(fotoPath = path) } }
    fun setUbicacion(lat: Double, lng: Double) {
        _draft.update { it.copy(latitud = lat, longitud = lng) }
    }

    fun cargar(id: Long) = viewModelScope.launch {
        if (id == -1L || _draft.value.id == id) return@launch
        repo.getById(id)?.let { c ->
            _clienteCargado.value = c
            _draft.value = ClienteDraft(c.idCliente, c.fotoPath, c.latitud, c.longitud)
        }
    }

    fun limpiar() { _draft.value = ClienteDraft(); _clienteCargado.value = null }

    fun guardar(nombre: String, apellido: String, ci: String, celular: String) =
        viewModelScope.launch {
            val d = _draft.value
            val c = ClienteEntity(d.id, nombre, apellido, ci, celular, d.fotoPath, d.latitud, d.longitud)
            try {
                if (d.id == 0L) repo.insert(c) else repo.actualizar(c)
                limpiar()
                _events.send(UiEvent.Saved)
            } catch (e: SQLiteConstraintException) {
                _events.send(UiEvent.Message("Ya existe un cliente con ese CI"))
            }
        }

    fun eliminar(c: ClienteEntity) = viewModelScope.launch {
        try { repo.eliminar(c) }
        catch (e: SQLiteConstraintException) {
            _events.send(UiEvent.Message("No se puede eliminar: el cliente tiene deudas registradas"))
        }
    }
}