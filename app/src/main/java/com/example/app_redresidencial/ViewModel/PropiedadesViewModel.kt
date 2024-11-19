package com.example.app_redresidencial.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_redresidencial.dataclases.Propiedades
import com.example.app_redresidencial.dataclases.Usuario
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.random.Random

class PropiedadesViewModel : ViewModel() {
    private val db = Firebase.firestore

    // Estado principal de la UI
    private val _usuarioPropiedad = MutableStateFlow<Triple<Usuario?, Propiedades?, List<Propiedades>>?>(null)
    val usuarioPropiedad: StateFlow<Triple<Usuario?, Propiedades?, List<Propiedades>>?> get() = _usuarioPropiedad

    // Usuario actual
    private val _usuarioActual = MutableStateFlow<Usuario?>(null)
    val usuarioActual: StateFlow<Usuario?> = _usuarioActual

    // Propiedades principales
    val propiedadPrincipal: Propiedades? get() = _usuarioPropiedad.value?.second
    val propiedadesAdicionales: List<Propiedades> get() = _usuarioPropiedad.value?.third ?: emptyList()

    // Código generado
    private val _codigoGenerado = MutableStateFlow<String?>(null)
    val codigoGenerado: StateFlow<String?> get() = _codigoGenerado

    // Usuarios y propiedades cargados
    private val _usuariosConPropiedades = MutableStateFlow<List<Pair<Usuario, List<Propiedades>>>>(emptyList())
    val usuariosConPropiedades: StateFlow<List<Pair<Usuario, List<Propiedades>>>> get() = _usuariosConPropiedades

    private val _cedulaActual = MutableStateFlow<String?>(null)
    val cedulaActual: StateFlow<String?> = _cedulaActual
    fun actualizarCedula(cedula: String) {
        _cedulaActual.value = cedula
        usuarioActual
    }

    // Buscar usuario y propiedades por identificación
    fun buscarPropietario(identificacion: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val usuario = db.collection("usuarios")
                    .document(identificacion)
                    .get().await()
                    .toObject(Usuario::class.java)

                if (usuario != null) {
                    _usuarioActual.value = usuario
                    _codigoGenerado.value = usuario.codigo

                    // Propiedad principal
                    val propiedad = db.collection("propiedades")
                        .document(identificacion)
                        .get().await()
                        .toObject(Propiedades::class.java)

                    // Propiedades adicionales
                    val propiedadesAdicionales = usuario.poderes.mapNotNull { idPoder ->
                        db.collection("propiedades")
                            .document(idPoder)
                            .get().await()
                            .toObject(Propiedades::class.java)
                    }

                    _usuarioPropiedad.value = Triple(usuario, propiedad, propiedadesAdicionales)
                } else {
                    limpiarEstados()
                }
            } catch (e: Exception) {
                Log.e("FirebaseError", "Error al buscar propietario: ${e.message}")
                limpiarEstados()
            }
        }
    }

    private fun limpiarEstados() {
        _usuarioActual.value = null
        _codigoGenerado.value = null
        _usuarioPropiedad.value = Triple(null, null, emptyList())
    }

    // Generar código aleatorio para un usuario
    fun crearCodigoAleatorio(identificacion: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val codigoAleatorio = Random.nextInt(100000, 999999).toString()
                db.collection("usuarios")
                    .document(identificacion)
                    .update("codigo", codigoAleatorio)
                    .await()
                _codigoGenerado.value = codigoAleatorio
            } catch (e: Exception) {
                Log.e("FirebaseError", "Error al generar código: ${e.message}")
            }
        }
    }

    private val _conjuntos = MutableStateFlow<List<String>>(emptyList())
    val conjuntos: StateFlow<List<String>> get() = _conjuntos
    // CONSULTAR CONJUTNO SEGUN ID DE USUARIO
    fun obtenerConjuntosUnicos() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val conjuntoList = db.collection("propiedades")
                    .get()
                    .await()
                    .documents
                    .mapNotNull { it.toObject(Propiedades::class.java)?.conjunto }
                    .distinct()
                _conjuntos.value = conjuntoList
            } catch (e: Exception) {
                Log.e("FirebaseError", "Error al obtener conjuntos: ${e.message}")
            }
        }
    }

    // COLOCAR LA HORA DE INGRESO
    fun actualizarHoraIngreso(identificacion: String){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // CONSEGUIR LA HORA ACTUAL
                val zonaColomnbia = ZoneId.of("America/Bogota")
                val horaActual = LocalDateTime.now(zonaColomnbia).format(DateTimeFormatter.ofPattern("HH:mm"))

                // COLOCAR FECHA EN BASE DE DATOS
                db.collection("usuarios").document(identificacion).update("hora_ingreso", horaActual).await()
            } catch (e:Exception){
                Log.e("FirebaseError", "Error al actualizar hora de ingreso: ${e.message}")
            }
        }
    }

    // Cargar usuarios con sus propiedades
    fun cargarUsuariosConPropiedades() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val usuariosSnapshot = db.collection("usuarios").get().await()
                val usuarios = usuariosSnapshot.documents.mapNotNull { it.toObject(Usuario::class.java) }

                val usuariosConPropiedadesList = usuarios.map { usuario ->
                    val propiedades = db.collection("propiedades")
                        .whereEqualTo("propietario", usuario.identificacion)
                        .get().await()
                        .documents.mapNotNull { it.toObject(Propiedades::class.java) }
                    usuario to propiedades
                }

                _usuariosConPropiedades.value = usuariosConPropiedadesList
            } catch (e: Exception) {
                Log.e("FirebaseError", "Error al cargar usuarios y propiedades: ${e.message}")
            }
        }
    }
}

/*class PropiedadesViewModel : ViewModel() {
    private val db = Firebase.firestore

    private val _usuarioPropiedad = MutableStateFlow<Triple<Usuario?, Propiedades?, List<Propiedades>>?>(null)
    val usuarioPropiedad: StateFlow<Triple<Usuario?, Propiedades?, List<Propiedades>>?> get() = _usuarioPropiedad

    // ACCESO A DATOS ACTUALES
    private val _usuarioActual = MutableStateFlow<Usuario?>(null)
    val usuarioActual: StateFlow<Usuario?> = _usuarioActual
    val propiedadPrincipal: Propiedades? get() = usuarioPropiedad.value?.second
    val propiedadesAdicionales:List<Propiedades> get() = usuarioPropiedad.value?.third ?: emptyList()

    // Nueva propiedad para almacenar el código generado
    private val _codigoGenerado = MutableStateFlow<String?>(null)
    val codigoGenerado: StateFlow<String?> get() = _codigoGenerado

    //PARA LLAMAR TODOS LOS UDUARIOS
    private val _usuariosConPropiedades = MutableStateFlow<List<Pair<Usuario, List<Propiedades>>>>(emptyList())
    val usuariosConPropiedades: StateFlow<List<Pair<Usuario, List<Propiedades>>>> get() = _usuariosConPropiedades


    // CONSULTAR USUARIO
    fun buscarPropietario(identificacion: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val usuarioRef = db.collection("usuarios").document(identificacion)
                val usuario = usuarioRef.get().await().toObject(Usuario::class.java)

                if (usuario != null) {
                    _usuarioActual.value = usuario
                    _codigoGenerado.value = usuario.codigo // CONSULTAR EL CODIGO QUE TIENE ACTUALMENTE
                    val propiedad = db.collection("propiedades").document(identificacion).get().await()
                        .toObject(Propiedades::class.java)

                    /*val propiedadesAdicionales = usuario.poderes.mapNotNull{ idPoderes ->
                        db.collection("propiedades").document(idPoderes).get().await()
                            .toObject(Propiedades::class.java)
                    }*/
                    val propiedadesAdicionales = usuario.poderes.mapNotNull { idPoderes ->
                        db.collection("propiedades")
                            .whereEqualTo("cedula", idPoderes)  // Buscando propiedades con la cedula que coincida con cada elemento de poderes
                            .get().await()
                            .documents
                            .firstOrNull()  // Tomamos el primer documento que coincida
                            ?.toObject(Propiedades::class.java)
                    }

                    _usuarioPropiedad.value = Triple(usuario, propiedad, propiedadesAdicionales)
                } else {
                    _usuarioActual.value = null
                    _usuarioPropiedad.value = Triple(null, null, emptyList())
                    _codigoGenerado.value = null  // Limpiar el código si no se encuentra el usuario
                }
            } catch (e: Exception) {
                Log.e("FirebaseError", "Error al consultar: ${e.message}")
                _codigoGenerado.value = null
            }
        }
    }

    // LE ASIGNA EL CODIGO AL USUARIO
    fun crearCodigoAleatorio(identificacion: String){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Generar un código aleatorio de 6 dígitos solo si no tiene código
                val codigoAleatorio = Random.nextInt(100000, 999999).toString()
                db.collection("usuarios").document(identificacion).update("codigo", codigoAleatorio).await()
                // Actualizar el código generado en el flujo de estado
                _codigoGenerado.value = codigoAleatorio
            }catch (e:Exception){
                Log.e("FirebaseError", "Error al generar el codigo ${e.message}")
                _codigoGenerado.value = null
            }
        }
    }

    private val _conjuntos = MutableStateFlow<List<String>>(emptyList())
    val conjuntos: StateFlow<List<String>> get() = _conjuntos

    // CONSULTAR CONJUTNO SEGUN ID DE USUARIO
    fun obtenerConjuntosUnicos() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val conjuntoList = db.collection("propiedades")
                    .get()
                    .await()
                    .documents
                    .mapNotNull { it.toObject(Propiedades::class.java)?.conjunto }
                    .distinct()
                _conjuntos.value = conjuntoList
            } catch (e: Exception) {
                Log.e("FirebaseError", "Error al obtener conjuntos: ${e.message}")
            }
        }
    }

    // COLOCAR LA HORA DE INGRESO
    fun actualizarHoraIngreso(identificacion: String){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // CONSEGUIR LA HORA ACTUAL
                val zonaColomnbia = ZoneId.of("America/Bogota")
                val horaActual = LocalDateTime.now(zonaColomnbia).format(DateTimeFormatter.ofPattern("HH:mm"))

                // COLOCAR FECHA EN BASE DE DATOS
                db.collection("usuarios").document(identificacion).update("hora_ingreso", horaActual).await()
            } catch (e:Exception){
                Log.e("FirebaseError", "Error al actualizar hora de ingreso: ${e.message}")
            }
        }
    }

    //CONSULTAR TODOS LOS USUARIOS
    fun cargarUsuariosConPropiedades() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val usuariosSnapshot = db.collection("usuarios").get().await()
                val usuarios = usuariosSnapshot.documents.mapNotNull { it.toObject(Usuario::class.java) }

                val usuariosConPropiedadesList = usuarios.map { usuario ->
                    val propiedadesSnapshot = db.collection("propiedades")
                        .whereEqualTo("propietario", usuario.identificacion)
                        .get().await()
                    val propiedades = propiedadesSnapshot.documents.mapNotNull { it.toObject(Propiedades::class.java) }
                    usuario to propiedades
                }

                _usuariosConPropiedades.value = usuariosConPropiedadesList
            } catch (e: Exception) {
                Log.e("FirebaseError", "Error al cargar usuarios y propiedades: ${e.message}")
            }
        }
    }
}*/

