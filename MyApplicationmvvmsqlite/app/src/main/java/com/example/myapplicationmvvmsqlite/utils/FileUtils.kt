package com.example.myapplicationmvvmsqlite.utils

import android.content.Context
import android.net.Uri
import java.io.File

object FileUtils {
    fun createImageFile(context: Context): File {
        val dir = File(context.filesDir, "photos").apply { mkdirs() }
        return File(dir, "CLI_${System.currentTimeMillis()}.jpg")
    }

    /** Copia la imagen elegida en galería al almacenamiento de la app (persistente). */
    fun copyUriToInternal(context: Context, uri: Uri): String {
        val file = createImageFile(context)
        context.contentResolver.openInputStream(uri)?.use { input ->
            file.outputStream().use { input.copyTo(it) }
        }
        return file.absolutePath
    }
}