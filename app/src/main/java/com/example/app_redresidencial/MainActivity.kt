package com.example.app_redresidencial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.app_redresidencial.Screens.agregarPoder
import com.example.app_redresidencial.ViewModel.AgregarPoderViewModel
import com.example.app_redresidencial.ViewModel.PropiedadesViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val propiedadesViewModel: PropiedadesViewModel = viewModel() // Para consultarUsuario
            val agregarPoderViewModel: AgregarPoderViewModel = viewModel() // Para agregarPoder
            val navController = rememberNavController()
            NavigationApp(navController = navController, propiedadesViewModel = propiedadesViewModel, agregarPoderViewModel = agregarPoderViewModel)
            //agregarPoder(viewModel = AgregarPoderViewModel())
        }
    }
}

