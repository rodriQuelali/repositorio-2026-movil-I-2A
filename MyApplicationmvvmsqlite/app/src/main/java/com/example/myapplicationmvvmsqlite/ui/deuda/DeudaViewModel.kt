package com.example.myapplicationmvvmsqlite.ui.deuda

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplicationmvvmsqlite.data.local.entity.DeudaEntity
import com.example.myapplicationmvvmsqlite.data.local.relation.DeudaConCliente
import com.example.myapplicationmvvmsqlite.data.repository.DeudaRepository
import com.example.myapplicationmvvmsqlite.data.session.SessionManager
import com.example.myapplicationmvvmsqlite.ui.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class FiltroDeuda { TODAS, PENDIENTES, VENCIDAS, COBRADAS }

class DeudaViewModel(
    private val repo: DeudaRepository,
    private val session: SessionManager
) : ViewModel() {

    private val idPrestamista get() = session.prestamistaId
    private val filtro = MutableStateFlow(FiltroDeuda.PENDIENTES)

    val deudas: StateFlow<List<DeudaConCliente>> =
        combine(repo.deudas(idPrestamista), filtro) { lista, f ->
            val hoy = System.currentTimeMillis()
            when (f) {
                FiltroDeuda.TODAS -> lista
                FiltroDeuda.PENDIENTES -> lista.filter { !it.deuda.pagado }
                FiltroDeuda.VENCIDAS -> lista.filter { !it.deuda.pagado && it.deuda.fechaCobro < hoy }
                FiltroDeuda.COBRADAS -> lista.filter { it.deuda.pagado }
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val totalPendiente: StateFlow<Double> = repo.totalPendiente(idPrestamista)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), 0.0)

    private val _events = Channel<UiEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    private val _detalle = MutableStateFlow<DeudaConCliente?>(null)
    val detalle: StateFlow<DeudaConCliente?> = _detalle

    fun setFiltro(f: FiltroDeuda) { filtro.value = f }

    fun cargarDetalle(id: Long) = viewModelScope.launch { _detalle.value = repo.getById(id) }

    fun guardar(idCliente: Long?, monto: Double?, fechaPrestamo: Long?, fechaCobro: Long?) =
        viewModelScope.launch {
            when {
                idCliente == null -> return@launch _events.send(UiEvent.Message("Selecciona un cliente"))
                monto == null || monto <= 0 -> return@launch _events.send(UiEvent.Message("Monto inválido"))
                fechaPrestamo == null || fechaCobro == null ->
                    return@launch _events.send(UiEvent.Message("Selecciona ambas fechas"))
                fechaCobro < fechaPrestamo ->
                    return@launch _events.send(UiEvent.Message("La fecha de cobro no puede ser anterior al préstamo"))
            }
            repo.insertar(
                DeudaEntity(
                    monto = monto!!, fechaPrestamo = fechaPrestamo!!, fechaCobro = fechaCobro!!,
                    idPrestamista = idPrestamista, idCliente = idCliente!!
                )
            )
            _events.send(UiEvent.Saved)
        }

    fun marcarPagada(d: DeudaEntity) = viewModelScope.launch {
        repo.actualizar(d.copy(pagado = true))
        _detalle.value = repo.getById(d.idDeuda)
    }

    fun eliminar(d: DeudaEntity) = viewModelScope.launch {
        repo.eliminar(d)
        _events.send(UiEvent.Saved)
    }
}