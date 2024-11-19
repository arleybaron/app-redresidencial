package com.tuapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_redresidencial.dataclases.Usuario
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

/*class CodigoViewModel : ViewModel() {
    private val db = Firebase.firestore

    // Estado del código encontrado
    private val _codigoValido = MutableStateFlow(false)
    val codigoValido: StateFlow<Boolean> get() = _codigoValido

    private val _usuarioAsociado = MutableStateFlow<String?>(null)
    val usuarioAsociado: StateFlow<String?> get() = _usuarioAsociado

    // Verificar si el código existe en Firebase
    fun verificarCodigo(codigo: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val snapshot = db.collection("usuarios")
                    .whereEqualTo("codigo", codigo)
                    .get()
                    .await()

                if (!snapshot.isEmpty) {
                    val usuario = snapshot.documents.first().getString("identificacion")
                    _codigoValido.value = true
                    _usuarioAsociado.value = usuario
                } else {
                    _codigoValido.value = false
                    _usuarioAsociado.value = null
                }
            } catch (e: Exception) {
                Log.e("FirebaseError", "Error al verificar el código: ${e.message}")
                _codigoValido.value = false
            }
        }
    }
}*/

class CodigoViewModel : ViewModel() {
    private val db = Firebase.firestore

    private val _codigoValido = MutableStateFlow(false)
    val codigoValido: StateFlow<Boolean> get() = _codigoValido

    private val _usuarioAsociado = MutableStateFlow<String?>(null)
    val usuarioAsociado: StateFlow<String?> get() = _usuarioAsociado

    // Estado para el usuario encontrado
    private val _usuario = MutableStateFlow<Usuario?>(null)
    val usuario: StateFlow<Usuario?> get() = _usuario

    // Estado para el mensaje de error en caso de no encontrar el usuario
    private val _errorMensaje = MutableStateFlow<String?>(null)
    val errorMensaje: StateFlow<String?> get() = _errorMensaje

    // Verificar si el código existe en Firebase
    fun verificarCodigo(codigo: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val snapshot = db.collection("usuarios")
                    .whereEqualTo("codigo", codigo)
                    .get()
                    .await()

                if (!snapshot.isEmpty) {
                    val usuario = snapshot.documents.first().getString("identificacion")
                    _codigoValido.value = true
                    _usuarioAsociado.value = usuario
                } else {
                    _codigoValido.value = false
                    _usuarioAsociado.value = null
                }
            } catch (e: Exception) {
                Log.e("FirebaseError", "Error al verificar el código: ${e.message}")
                _codigoValido.value = false
            }
        }
    }

    // Obtener los datos del usuario por código
    fun obtenerUsuario(codigo: String) {
        // Asegurarse de que solo se realiza la consulta una vez por código
        if (_usuario.value != null || _errorMensaje.value != null) return

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val snapshot = db.collection("usuarios")
                    .whereEqualTo("codigo", codigo)
                    .get()
                    .await()

                if (!snapshot.isEmpty) {
                    val document = snapshot.documents.first()
                    val usuario = Usuario(
                        codigo = document.getString("codigo") ?: "",
                        identificacion = document.getString("identificacion") ?: "",
                        nombre = document.getString("nombre") ?: "",
                        apellido = document.getString("apellido") ?: "",
                        hora_ingreso = document.getString("hora_ingreso") ?: "",
                    )
                    _usuario.value = usuario
                } else {
                    // Si no se encuentra el usuario
                    _usuario.value = null
                    _errorMensaje.value = "Usuario no encontrado"
                }
            } catch (e: Exception) {
                Log.e("FirebaseError", "Error al obtener el usuario: ${e.message}")
                // Si ocurre un error en la consulta
                _usuario.value = null
                _errorMensaje.value = "Error al obtener el usuario"
            }
        }
    }
}

