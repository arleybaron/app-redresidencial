package com.example.app_redresidencial.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.app_redresidencial.ViewModel.PropiedadesViewModel

@Composable
fun PantallaInicio(navController: NavHostController, propiedadesViewModel: PropiedadesViewModel) {
    val usuariosConPropiedades by propiedadesViewModel.usuariosConPropiedades.collectAsState()

    // Cargar datos al iniciar la pantalla
    LaunchedEffect(Unit) {
        propiedadesViewModel.cargarUsuariosConPropiedades()
    }

    BaseScreen(title = "Usuarios y Propiedades", navController = navController) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            if (usuariosConPropiedades.isNotEmpty()) {
                usuariosConPropiedades.forEach { (usuario, propiedades) ->
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "${usuario.nombre} ${usuario.apellido}",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(text = "Identificación: ${usuario.identificacion}")
                                Text(text = "Hora de ingreso: ${usuario.hora_ingreso}")
                                Text(text = "Código: ${usuario.codigo ?: "Sin código"}")

                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Propiedades:",
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 18.sp
                                )
                                if (propiedades.isNotEmpty()) {
                                    propiedades.forEach { propiedad ->
                                        Text(
                                            text = "- ${propiedad.tipo_propiedad}: ${propiedad.descripcion} (${propiedad.conjunto})",
                                            modifier = Modifier.padding(start = 8.dp)
                                        )
                                    }
                                } else {
                                    Text(text = "No tiene propiedades registradas.")
                                }
                            }
                        }
                    }
                }
            } else {
                item {
                    Text(
                        text = "Cargando usuarios y propiedades...",
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}


/*@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PantallaInicio(viewModel: PropiedadesViewModel, navController: NavHostController) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val title = remember { mutableStateOf("Inicio") }
    val orangeColor = Color(0xFFFF9F24)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.8f) // Limita el ancho del menú
                    .fillMaxHeight() // Menú ocupa toda la pantalla
                    .background(orangeColor) // Fondo naranja
            ) {
                DrawerHeader(orangeColor = orangeColor) // Pasa el color naranja
                DrawerBody(
                    items = listOf(
                        MenuItem(id = "inicio", title = "Inicio", contentDescription = "Go to Inicio", icon = Icons.Default.Home),
                        MenuItem(id = "usuario", title = "Consultar Usuario", contentDescription = "Go to Usuario", icon = Icons.Default.Person),
                        MenuItem(id = "votacion", title = "Votación", contentDescription = "Go to Votación", icon = Icons.Default.ThumbUp),
                        MenuItem(id = "propietarios", title = "Carga Masiva", contentDescription = "Go to Propietarios", icon = Icons.Default.AccountBox),
                        MenuItem(id = "cronometro", title = "Cronómetro", contentDescription = "Go to Cronómetro", icon = Icons.Default.PlayArrow),
                        MenuItem(id = "orden", title = "Orden del Día", contentDescription = "Go to Orden", icon = Icons.Default.Check),
                        MenuItem(id = "propuestas", title = "Propuestas", contentDescription = "Go to Propuestas", icon = Icons.Default.AddCircle)
                    ),
                    onItemClick = { menuItem ->
                        scope.launch {
                            drawerState.close()
                            when (menuItem.id) {
                                "inicio" -> navController.navigate(Routes.PantallaInicio.route)
                                "usuario" -> navController.navigate(Routes.PantallaIngresarUsuario.route)
                                "votacion" -> navController.navigate(Routes.PantallaCrearVotacion.route)
                                "propietarios" -> navController.navigate(Routes.PantallaCargarPropietarios.route)
                                "cronometro" -> navController.navigate(Routes.PantallaCronometro.route)
                                "orden" -> navController.navigate(Routes.PantallaOrdenDelDia.route)
                                "propuestas" -> navController.navigate(Routes.PantallaPropuestas.route)
                            }
                        }
                    }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                MyTopAppBar(title = title.value) {
                    scope.launch { drawerState.open() }
                }
            }
        ) { paddingValues ->
            // Aplica el relleno al contenido principal
            Box(modifier = Modifier.padding(paddingValues)) {
                consultarUsuario(viewModel = viewModel, navController = navController)
            }
        }
    }
}*/

