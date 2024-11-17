package com.example.app_redresidencial.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AgregarPoderViewModel: ViewModel() {
    private val db = Firebase.firestore

    fun agregarPoder(propietarioId: String, poder:String){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val usuarioRef = db.collection("usuarios").document(propietarioId)

                usuarioRef.get().addOnSuccessListener { document ->
                    if(document.exists()){
                        val poderes = document["poderes"] as? MutableList<String> ?: mutableListOf()
                        poderes.add(poder)
                        usuarioRef.update("poderes", poderes)
                    }else{
                        Log.e("FirebaseError", "usuario no encontrado")
                    }
                }
            }catch (e: Exception){
                Log.e("FirebaseError", "Error al agregar poder: ${e.message}")
            }
        }
    }
}