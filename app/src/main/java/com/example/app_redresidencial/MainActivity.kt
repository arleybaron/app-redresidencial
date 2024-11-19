package com.example.app_redresidencial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.app_redresidencial.Screens.agregarPoder
import com.example.app_redresidencial.ViewModel.AgregarPoderViewModel
import com.example.app_redresidencial.ViewModel.PropiedadesViewModel
import kotlinx.coroutines.launch
import androidx.compose.material.*
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.app_redresidencial.Screens.DrawerBody
import com.example.app_redresidencial.Screens.DrawerHeader
import com.example.app_redresidencial.Screens.MenuItem
import com.example.app_redresidencial.Screens.MyTopAppBar
import com.example.app_redresidencial.Screens.PantallaCargarPropietarios
import com.example.app_redresidencial.Screens.PantallaCrearVotacion
import com.example.app_redresidencial.Screens.PantallaCronometro
import com.example.app_redresidencial.Screens.PantallaIngresarUsuario
import com.example.app_redresidencial.Screens.PantallaInicio
import com.example.app_redresidencial.Screens.PantallaPropuestas
import com.example.app_redresidencial.Screens.PantallaOrdenDelDia
import com.example.app_redresidencial.Screens.Routes
import com.example.app_redresidencial.Screens.consultarUsuario
import com.example.app_redresidencial.Screens.home
import com.tuapp.viewmodel.CodigoViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val propiedadesViewModel: PropiedadesViewModel = viewModel() // Para consultarUsuario
            val agregarPoderViewModel: AgregarPoderViewModel = viewModel() // Para agregarPoder
            val codigoViewModel: CodigoViewModel = viewModel()
            val navController = rememberNavController()
            NavigationApp(navController = navController, propiedadesViewModel = propiedadesViewModel, agregarPoderViewModel = agregarPoderViewModel, codigoViewModel= codigoViewModel)
            //consultarUsuario(propiedadesViewModel, navController)
        }
    }
}

