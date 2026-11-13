package com.example.myapplicationmvvmsqlite.utils

import android.widget.Toast
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Extensions {
    fun Long.toFecha(): String =
        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(this))

    fun Double.toMoneda(): String = "Bs %.2f".format(this)   // cambia la moneda a la de tu país

    fun Fragment.toast(msg: String) =
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
}