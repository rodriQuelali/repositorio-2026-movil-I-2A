package com.example.myapplicationrecirclerviewbotnimagen

data class ItemModel(
    val id: String, // Identificador único del item
    val imageUrl: String?, // URL de la imagen (o Int si es un drawable, o String para ruta de archivo)
    val imagePlaceholder: Int = R.drawable.ic_launcher_foreground, // Un placeholder mientras carga la imagen
    val buttonText: String,
    var isButtonEnabled: Boolean, // Estado del botón (mutable si puede cambiar por interacciones)
    var isImageVisible: Boolean = true // Podrías tener un estado para la visibilidad de la imagen
)
