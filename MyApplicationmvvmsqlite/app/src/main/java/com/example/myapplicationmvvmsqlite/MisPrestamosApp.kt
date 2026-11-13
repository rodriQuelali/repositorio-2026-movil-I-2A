package com.example.myapplicationmvvmsqlite

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.myapplicationmvvmsqlite.data.local.AppDatabase
import com.example.myapplicationmvvmsqlite.data.repository.ClienteRepository
import com.example.myapplicationmvvmsqlite.data.repository.DeudaRepository
import com.example.myapplicationmvvmsqlite.data.repository.PrestamistaRepository
import com.example.myapplicationmvvmsqlite.data.session.SessionManager
import com.example.myapplicationmvvmsqlite.ui.auth.AuthViewModel
import com.example.myapplicationmvvmsqlite.ui.cliente.ClienteViewModel
import com.example.myapplicationmvvmsqlite.ui.deuda.DeudaViewModel

class MisPrestamosApp : Application() {
    private val db by lazy { AppDatabase.getInstance(this) }
    val clienteRepo by lazy { ClienteRepository(db.clienteDao()) }
    val deudaRepo by lazy { DeudaRepository(db.deudaDao()) }
    val prestamistaRepo by lazy { PrestamistaRepository(db.prestamistaDao()) }
    val session by lazy { SessionManager(this) }
}


object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer { val a = app(); AuthViewModel(a.prestamistaRepo, a.session) }
        initializer { val a = app(); ClienteViewModel(a.clienteRepo) }
        initializer { val a = app(); DeudaViewModel(a.deudaRepo, a.session) }
    }
    private fun CreationExtras.app() = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MisPrestamosApp
}