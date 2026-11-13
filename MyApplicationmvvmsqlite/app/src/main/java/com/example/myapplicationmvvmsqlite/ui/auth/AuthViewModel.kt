package com.example.myapplicationmvvmsqlite.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplicationmvvmsqlite.data.local.entity.PrestamistaEntity
import com.example.myapplicationmvvmsqlite.data.repository.PrestamistaRepository
import com.example.myapplicationmvvmsqlite.data.session.SessionManager
import com.example.myapplicationmvvmsqlite.ui.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repo: PrestamistaRepository,
    private val session: SessionManager
) : ViewModel() {

    private val _events = Channel<UiEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    fun isLogged() = session.isLogged()

    fun login(correo: String, pass: String) = viewModelScope.launch {
        val p = repo.login(correo.trim(), pass)
        if (p != null) {
            session.prestamistaId = p.idPrestamista
            _events.send(UiEvent.Saved)
        } else _events.send(UiEvent.Message("Correo o contraseña incorrectos"))
    }

    fun registrar(p: PrestamistaEntity) = viewModelScope.launch {
        repo.registrar(p)
            .onSuccess { _events.send(UiEvent.Saved) }
            .onFailure { _events.send(UiEvent.Message(it.message ?: "Error al registrar")) }
    }

    fun logout() = session.logout()
}