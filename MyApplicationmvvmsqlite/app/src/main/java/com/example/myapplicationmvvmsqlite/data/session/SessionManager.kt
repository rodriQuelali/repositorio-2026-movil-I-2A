package com.example.myapplicationmvvmsqlite.data.session

import android.content.Context

class SessionManager(context: Context) {
    private val prefs = context.getSharedPreferences("session", Context.MODE_PRIVATE)

    var prestamistaId: Long
        get() = prefs.getLong("prestamistaId", -1L)
        set(value) = prefs.edit().putLong("prestamistaId", value).apply()

    fun isLogged() = prestamistaId != -1L
    fun logout() = prefs.edit().clear().apply()
}