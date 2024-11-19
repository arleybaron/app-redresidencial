package com.example.app_redresidencial.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(
    title: String,
    onClickDrawer: () -> Unit
) {
    TopAppBar(
        title = { Text(text = title, color = MaterialTheme.colorScheme.onPrimary) },
        modifier = Modifier.background(MaterialTheme.colorScheme.primary),
        navigationIcon = {
            IconButton(onClick = { onClickDrawer() }) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
            }
        }
    )
}

@Composable
fun BaseScreen(
    title: String,
    navController: NavHostController,
    content: @Composable (PaddingValues) -> Unit
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val orangeColor = Color(0xFFFF9F24)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .fillMaxHeight()
                    .background(orangeColor)
            ) {
                DrawerHeader(orangeColor = orangeColor)
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
                MyTopAppBar(title = title) {
                    scope.launch { drawerState.open() }
                }
            },
            content = content
        )
    }
}

/*package com.example.app_redresidencial.Screens

import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DividerDefaults.color
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(
    title: String,
    onClickDrawer:() -> Unit
){
    TopAppBar(
        title = {Text(text = title, color = MaterialTheme.colorScheme.onPrimary)},
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primary),
        navigationIcon = {
            IconButton(onClick = { onClickDrawer() }) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
            }
        }
    )
}*/
