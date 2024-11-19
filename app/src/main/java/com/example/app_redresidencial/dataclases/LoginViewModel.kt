package com.example.app_redresidencial.dataclases

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch

class LoginViewModel:ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    private val _loading = MutableLiveData(false)

    fun signInWithEmailAndPassword(email: String, password:String, home: ()-> Unit)
    = viewModelScope.launch {
        try {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        Log.d("Red Residencial", "Logueado")
                        home()
                    }else{
                        Log.d("Red Residencial", "Logueo: ${task.result.toString()}")
                    }
                }
        }catch (ex:Exception){
            Log.d("Red Residencial", "logueo: ${ex.message}")
        }
    }
}