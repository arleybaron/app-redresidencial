package com.example.app_redresidencial.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.app_redresidencial.ViewModel.PropiedadesViewModel

@Composable

fun PantallaIngresarUsuario(viewModel: PropiedadesViewModel, navController: NavHostController){
    BaseScreen(title = "Inicio", navController = navController) { paddingValues ->
        // Contenido principal de la pantalla
        Box(modifier = Modifier.padding(paddingValues)) {
            consultarUsuario(viewModel = viewModel, navController = navController)
        }
    }
}