    package com.example.app_redresidencial.dataclases

/*data class Usuario(
    var identificacion: String = "", // se usara para unir con propiedades
    val nombre: String = "",
    val apellido: String = "",
    var hora_ingreso: String = "",
    var poderes: String = "" //Aqui se unira el id de otra porpiedad perteneciente a otro propietrario
) {
    fun toMap(): Map<String, String> {
        return mapOf(
            "identificacion" to identificacion,
            "nombre" to nombre,
            "apellido" to apellido,
            "hora_ingreso" to hora_ingreso,
            "poderes" to poderes
        )
    }
}*/
data class Usuario(
    var identificacion: String = "", // se usara para unir con propiedades
    val nombre: String = "",
    val apellido: String = "",
    var hora_ingreso: String = "",
    var poderes: List<String> = emptyList(), // Aquí se unirá el id de otra propiedad perteneciente a otro propietario
    var codigo: String? = null // Nuevo atributo para almacenar el código
) {
    fun toMap(): Map<String, Any> { // Cambié el tipo a Any para permitir null en código
        return mapOf(
            "identificacion" to identificacion,
            "nombre" to nombre,
            "apellido" to apellido,
            "hora_ingreso" to hora_ingreso,
            "poderes" to poderes,
            "codigo" to (codigo ?: "") // Si es null, guardamos un valor vacío
        )
    }
}
