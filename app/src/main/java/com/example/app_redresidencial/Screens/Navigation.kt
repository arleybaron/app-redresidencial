package com.example.app_redresidencial

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.app_redresidencial.Screens.PantallaCargarPropietarios
import com.example.app_redresidencial.Screens.PantallaCrearVotacion
import com.example.app_redresidencial.Screens.PantallaCronometro
import com.example.app_redresidencial.Screens.PantallaIngresarUsuario
import com.example.app_redresidencial.Screens.PantallaInicio
import com.example.app_redresidencial.Screens.PantallaOrdenDelDia
import com.example.app_redresidencial.Screens.PantallaPropuestas
import com.example.app_redresidencial.Screens.Routes
import com.example.app_redresidencial.Screens.agregarPoder
import com.example.app_redresidencial.Screens.consultarUsuario
import com.example.app_redresidencial.Screens.datosUsuarioPresente
import com.example.app_redresidencial.Screens.home
import com.example.app_redresidencial.Screens.loginAdminScreen
import com.example.app_redresidencial.ViewModel.AgregarPoderViewModel
import com.example.app_redresidencial.ViewModel.PropiedadesViewModel
import com.tuapp.viewmodel.CodigoViewModel

@Composable
fun NavigationApp(
    navController: NavHostController,
    propiedadesViewModel: PropiedadesViewModel,
    agregarPoderViewModel: AgregarPoderViewModel,
    codigoViewModel: CodigoViewModel){
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { home(navController, codigoViewModel) }
        composable("login") { loginAdminScreen(navController) }
        composable("cargar-usuario") { consultarUsuario(propiedadesViewModel, navController) }
        composable("agregar-poder") {
            val cedulaActual by propiedadesViewModel.cedulaActual.collectAsState()
            LaunchedEffect(cedulaActual) {
                println("Cedula Actual: $cedulaActual")
            }
            agregarPoder(agregarPoderViewModel, navController, propiedadesViewModel) }
        composable("datos-usuario") {
            val cedulaActual by propiedadesViewModel.cedulaActual.collectAsState()
            LaunchedEffect(cedulaActual) {
                println("Cedula Actual: $cedulaActual")
            }
            datosUsuarioPresente(navController, propiedadesViewModel) }
        composable(Routes.PantallaInicio.route) { PantallaInicio(navController, propiedadesViewModel) }
        composable(Routes.PantallaIngresarUsuario.route) { PantallaIngresarUsuario(propiedadesViewModel, navController) }
        composable(Routes.PantallaCronometro.route) { PantallaCronometro(navController) }
        composable(Routes.PantallaCrearVotacion.route) { PantallaCrearVotacion(navController) }
        composable(Routes.PantallaCargarPropietarios.route) { PantallaCargarPropietarios(navController) }
        composable(Routes.PantallaPropuestas.route) { PantallaPropuestas(navController) }
        composable(Routes.PantallaOrdenDelDia.route) { PantallaOrdenDelDia(navController) }
    }
}