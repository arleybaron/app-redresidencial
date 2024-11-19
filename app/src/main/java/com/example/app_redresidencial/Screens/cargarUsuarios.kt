package com.example.app_redresidencial.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.app_redresidencial.ViewModel.PropiedadesViewModel

val orangeColor = Color(0xFFFF9F24)
@Composable
fun consultarUsuario(viewModel: PropiedadesViewModel, navController: NavHostController) {
    var idBusqueda by remember { mutableStateOf("") }
    var conjuntoSeleccionado by remember { mutableStateOf("") }
    var isDropdownExpanded by remember { mutableStateOf(false) }
    var mensajeError by remember { mutableStateOf("") }
    var usuarioActivo by remember { mutableStateOf(false) }

    val usuarioPropiedad by viewModel.usuarioPropiedad.collectAsState()
    val conjuntos by viewModel.conjuntos.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.obtenerConjuntosUnicos()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // Fondo blanco
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Buscar Propietario",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = "Seleccione el Conjunto",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Box {
                Button(
                    onClick = { isDropdownExpanded = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                        .clip(RoundedCornerShape(50.dp)), // Botón completamente redondeado
                    colors = ButtonDefaults.buttonColors(containerColor = orangeColor)
                ) {
                    Text(text = if (conjuntoSeleccionado.isEmpty()) "Seleccionar Conjunto" else conjuntoSeleccionado)
                }
                DropdownMenu(
                    expanded = isDropdownExpanded,
                    onDismissRequest = { isDropdownExpanded = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    conjuntos.forEach { conjunto ->
                        DropdownMenuItem(
                            text = { Text(conjunto, color = MaterialTheme.colorScheme.onSurface) },
                            onClick = {
                                conjuntoSeleccionado = conjunto
                                isDropdownExpanded = false
                            })
                    }
                }
            }
            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = idBusqueda,
                onValueChange = { idBusqueda = it },
                label = { Text("Ingrese la Identificación") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {
                    if (conjuntoSeleccionado.isNotEmpty() && idBusqueda.isNotEmpty()) {
                        viewModel.actualizarCedula(idBusqueda)
                        viewModel.buscarPropietario(idBusqueda)
                        mensajeError = ""
                    } else {
                        mensajeError = "¡Por favor, complete todos los campos!"
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(50.dp)), // Botón completamente redondeado
                colors = ButtonDefaults.buttonColors(containerColor = orangeColor)
            ) {
                Text("Consultar")
            }
            Spacer(Modifier.height(24.dp))

            // Mostrar datos del usuario y sus propiedades
            usuarioPropiedad?.let { (usuario, propiedadPrincipal, propiedadesAdicionales) ->
                if (usuario != null) {
                    if (propiedadPrincipal?.conjunto == conjuntoSeleccionado) {
                        Text(
                            text = "Usuario Activo",
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(top = 16.dp),
                        )
                        Text(
                            text = "\n ${usuario.nombre} ${usuario.apellido}, Hora de ingreso: ${usuario.hora_ingreso}"
                        )
                        usuarioActivo = true
                        viewModel.actualizarHoraIngreso(idBusqueda)

                        propiedadPrincipal?.let { propiedad ->
                            Text("\nPropiedad Principal: ${propiedad.tipo_propiedad}, ${propiedad.descripcion}, ${propiedad.conjunto}")
                        } ?: Text(
                            "No se encontró la propiedad principal para este usuario.",
                            color = MaterialTheme.colorScheme.error
                        )
                        if (propiedadesAdicionales.isNotEmpty()) {
                            Text("\nPropiedades Adicionales:")
                            propiedadesAdicionales.forEachIndexed { index, propiedad ->
                                Text("\nPropiedad Adicional ${index + 1}: ${propiedad.tipo_propiedad}, ${propiedad.descripcion}, ${propiedad.conjunto}")
                            }
                        } else {
                            Text("No se encontraron propiedades adicionales")
                        }

                    } else {
                        mensajeError = "El usuario no pertenece al conjunto seleccionado"
                        usuarioActivo = false
                    }
                } else {
                    mensajeError = "El usuario no existe"
                    usuarioActivo = false
                }

                Text(
                    text = mensajeError,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            if (usuarioActivo) {
                Spacer(Modifier.height(16.dp))
                Button(
                    onClick = {
                        if (usuarioActivo) {
                            navController.navigate("agregar-poder") {
                                viewModel.crearCodigoAleatorio(idBusqueda)
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(50.dp)), // Botón completamente redondeado
                    colors = ButtonDefaults.buttonColors(containerColor = orangeColor)
                ) {
                    Text("Registrar")
                }
            }
        }
    }
}
/*@Composable
fun consultarUsuario(viewModel: PropiedadesViewModel, navController: NavHostController) {
    var idBusqueda by remember { mutableStateOf("") }
    var conjuntoSeleccionado by remember { mutableStateOf("") }
    var isDropdownExpanded by remember { mutableStateOf(false) }
    var mensajeError by remember { mutableStateOf("") }
    var usuarioActivo by remember { mutableStateOf(false) }

    val usuarioPropiedad by viewModel.usuarioPropiedad.collectAsState()
    val conjuntos by viewModel.conjuntos.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.obtenerConjuntosUnicos()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Buscar Propietario",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = "Seleccione el Conjunto",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Box {
                Button(
                    onClick = { isDropdownExpanded = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {
                    Text(text = if (conjuntoSeleccionado.isEmpty()) "Seleccionar Conjunto" else conjuntoSeleccionado)
                }
                DropdownMenu(
                    expanded = isDropdownExpanded,
                    onDismissRequest = { isDropdownExpanded = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    conjuntos.forEach { conjunto ->
                        DropdownMenuItem(
                            text = { Text(conjunto, color = MaterialTheme.colorScheme.onSurface) },
                            onClick = {
                                conjuntoSeleccionado = conjunto
                                isDropdownExpanded = false
                            })
                    }
                }
            }
            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = idBusqueda,
                onValueChange = { idBusqueda = it },
                label = { Text("Ingrese la Identificación") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {
                    if (conjuntoSeleccionado.isNotEmpty() && idBusqueda.isNotEmpty()) {
                        viewModel.actualizarCedula(idBusqueda) // Actualiza la cédula en el ViewModel
                        viewModel.buscarPropietario(idBusqueda)
                        mensajeError = ""
                    } else {
                        mensajeError = "¡Por favor, complete todos los campos!"
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.secondary)
            ) {
                Text("Consultar")
            }
            Spacer(Modifier.height(24.dp))

            // Mostrar datos del usuario y sus propiedades
            usuarioPropiedad?.let { (usuario, propiedadPrincipal, propiedadesAdicionales) ->
                // SI EL USUARIO EXISTE
                if (usuario != null){
                    if (propiedadPrincipal?.conjunto == conjuntoSeleccionado) {
                        //Si el usuario pertenece al conjunto seleccionado
                        Text(
                            text = "Usuario Activo",
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(top = 16.dp),
                        )
                        Text(
                            text = "\n ${usuario.nombre} ${usuario.apellido}, Hora de ingreso: ${usuario.hora_ingreso}"
                        )
                        usuarioActivo = true
                        viewModel.actualizarHoraIngreso(idBusqueda)

                        propiedadPrincipal?.let { propiedad ->
                            Text("\nPropiedad Principal: ${propiedad.tipo_propiedad}, ${propiedad.descripcion}, ${propiedad.conjunto}")
                        } ?: Text(
                            "No se encontró la propiedad principal para este usuario.",
                            color = MaterialTheme.colorScheme.error
                        )
                        if (propiedadesAdicionales.isNotEmpty()){
                            Text("\nPropiedades Adicionales:")
                            propiedadesAdicionales?.forEachIndexed { index, propiedad->
                                Text("\nPropiedad Adicional ${index + 1}: ${propiedad.tipo_propiedad}, ${propiedad.descripcion}, ${propiedad.conjunto}")
                            }
                        }else{
                            //EL USUARIO NO TIENE PODERES
                            Text("No se encontraron propiedades adicionales")
                        }

                    } else {
                        //EL USUARIO NO PERTENECE AL CONJUNTO
                        mensajeError = "El usuario no pertence al conjunto seleccionado"
                        usuarioActivo = false
                    }
                }else{
                    //USUARIO NO EXISTE EN LA BASE DE DATOS
                    mensajeError = "El usuario no existe"
                    usuarioActivo = false
                }

                Text(
                    text = mensajeError,
                    color= MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            if(usuarioActivo){
                Spacer(Modifier.height(16.dp))
                Button(
                    onClick = {
                        if (usuarioActivo){
                            navController.navigate("agregar-poder"
                                   /* "$idBusqueda"*/){
                                viewModel.crearCodigoAleatorio(idBusqueda)
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.medium)
                        .background(MaterialTheme.colorScheme.primary)
                ) {
                    Text("Registrar")
                }
            }
        }
    }
}*/



