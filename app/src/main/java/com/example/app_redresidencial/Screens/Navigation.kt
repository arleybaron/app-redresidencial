package com.example.app_redresidencial

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.app_redresidencial.Screens.agregarPoder
import com.example.app_redresidencial.Screens.consultarUsuario
import com.example.app_redresidencial.Screens.datosUsuarioPresente
import com.example.app_redresidencial.ViewModel.AgregarPoderViewModel
import com.example.app_redresidencial.ViewModel.PropiedadesViewModel

@Composable
fun NavigationApp(
    navController: NavHostController,
    propiedadesViewModel: PropiedadesViewModel,
    agregarPoderViewModel: AgregarPoderViewModel){
    NavHost(navController = navController, startDestination = "cargar-usuario") {
        composable("cargar-usuario") { consultarUsuario(propiedadesViewModel, navController) }
        composable("agregar-poder") { agregarPoder(agregarPoderViewModel, navController, propiedadesViewModel) }
        composable("datos-usuario") { datosUsuarioPresente(navController, propiedadesViewModel) }
    }
}