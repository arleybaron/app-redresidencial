package com.example.app_redresidencial.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.app_redresidencial.ViewModel.AgregarPoderViewModel
import com.example.app_redresidencial.ViewModel.PropiedadesViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color

/*@Composable
fun agregarPoder(
    agregarPoderViewModel: AgregarPoderViewModel,
    navController: NavHostController,
    propiedadesViewModel: PropiedadesViewModel
) {
    BaseScreen(title = "Agregar Poder", navController = navController) { paddingValues ->
        var poder by remember { mutableStateOf("") }
        var mensaje by remember { mutableStateOf("") }

        //val usuarioActual by propiedadesViewModel.usuarioActual.collectAsState()
        //var propietarioId by remember { mutableStateOf(usuarioActual?.identificacion ?: "") }
        val cedulaActual by propiedadesViewModel.cedulaActual.collectAsState()
        var propietarioId by remember { mutableStateOf(cedulaActual ?: "") }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = propietarioId,
                onValueChange = { propietarioId = it },
                label = { Text("Identificación del propietario") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = poder,
                onValueChange = { poder = it },
                label = { Text("Poder") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    agregarPoderViewModel.agregarPoder(propietarioId, poder)
                    mensaje = "El poder $poder fue agregado con éxito"
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Agregar Poder")
            }

            Button(
                onClick = {
                    navController.navigate("datos-usuario")
                }
            ) {
                Text("Continuar")
            }

            // Mostrar mensaje de éxito
            if (mensaje.isNotEmpty()) {
                Spacer(modifier = Modifier.padding(16.dp))
                Text(
                    text = mensaje,
                    modifier = Modifier.padding(8.dp),
                    color = androidx.compose.ui.graphics.Color.Magenta
                )
            }
        }
    }
}*/

@Composable
fun agregarPoder(
    agregarPoderViewModel: AgregarPoderViewModel,
    navController: NavHostController,
    propiedadesViewModel: PropiedadesViewModel
) {
    val orangeColor = Color(0xFFFF9F24) // Definición del color personalizado

    BaseScreen(title = "Agregar Poder", navController = navController) { paddingValues ->
        var poder by remember { mutableStateOf("") }
        var mensaje by remember { mutableStateOf("") }
        val cedulaActual by propiedadesViewModel.cedulaActual.collectAsState()
        var propietarioId by remember { mutableStateOf(cedulaActual ?: "") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White) // Fondo blanco
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = propietarioId,
                onValueChange = { propietarioId = it },
                label = { Text("Identificación del propietario") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = poder,
                onValueChange = { poder = it },
                label = { Text("Poder") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    agregarPoderViewModel.agregarPoder(propietarioId, poder)
                    mensaje = "El poder \"$poder\" fue agregado con éxito"
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(50.dp)), // Botón completamente redondeado
                colors = ButtonDefaults.buttonColors(containerColor = orangeColor)
            ) {
                Text("Agregar Poder")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    navController.navigate("datos-usuario")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(50.dp)), // Botón completamente redondeado
                colors = ButtonDefaults.buttonColors(containerColor = orangeColor)
            ) {
                Text("Continuar")
            }

            // Mostrar mensaje de éxito
            if (mensaje.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = mensaje,
                    modifier = Modifier.padding(8.dp),
                    color = Color.Magenta
                )
            }
        }
    }
}


/*@Composable
fun agregarPoder(
    agregarPoderViewModel: AgregarPoderViewModel,
    navController: NavHostController,
    propiedadesViewModel: PropiedadesViewModel,
){
    var poder by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }

    val usuarioActual by propiedadesViewModel.usuarioActual.collectAsState()
    var propietarioId by remember { mutableStateOf(usuarioActual?.identificacion ?:"") }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = propietarioId,
            onValueChange = {propietarioId = it},
            label = { Text("Identificación del propietario")},
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = poder,
            onValueChange = {poder = it},
            label = {Text("Poder")},
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        
        Button(
            onClick = {
                agregarPoderViewModel.agregarPoder(propietarioId, poder)
                mensaje = "El poder $poder fue agregado con éxito"
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Agregar Poder")
        }

        Button(
            onClick = {
                navController.navigate("datos-usuario")
            }) {
            Text("Continuar")

        }

        // MENSAJE DE EXITO
        if(mensaje.isNotEmpty()){
            Spacer(modifier = Modifier.padding(16.dp))
            Text(
                text = mensaje,
                modifier = Modifier.padding(8.dp),
                color = androidx.compose.ui.graphics.Color.Magenta
            )
        }
    }
}*/
