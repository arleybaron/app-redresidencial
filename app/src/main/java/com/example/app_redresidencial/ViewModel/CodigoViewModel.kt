package com.tuapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class CodigoViewModel : ViewModel() {
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
}
