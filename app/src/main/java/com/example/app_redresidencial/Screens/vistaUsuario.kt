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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tuapp.viewmodel.CodigoViewModel

@Composable
fun vistaUsuario(navController: NavController, codigoViewModel: CodigoViewModel, codigo: String) {
    val usuario by codigoViewModel.usuario.collectAsState()
    val errorMensaje by codigoViewModel.errorMensaje.collectAsState()

    // Llamar al método para cargar los datos del usuario solo si el usuario es nulo
    LaunchedEffect(codigo) {
        if (usuario == null && errorMensaje == null) {  // Solo obtener el usuario si aún no se ha cargado ni el usuario ni el mensaje de error
            codigoViewModel.obtenerUsuario(codigo)
        }
    }

    BaseScreen(title = "Datos del Usuario", navController = navController) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White) // Fondo blanco
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Si hay un error, mostrarlo
            errorMensaje?.let {
                Text(
                    text = it,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red
                )
            }

            // Si el usuario está disponible, mostrar sus datos
            usuario?.let {
                Text(
                    text = "Código: ${it.codigo}",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("Identificación: ${it.identificacion}")
                Text("Nombre y apellido: ${it.nombre} ${it.apellido}")
                Spacer(modifier = Modifier.height(8.dp))
                Text("Hora de Ingreso: ${it.hora_ingreso}")
            } ?: run {
                // Si aún no se ha cargado el usuario ni el error, mostrar un mensaje de carga
                Text("Cargando datos del usuario...", fontSize = 18.sp, color = Color.Gray)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { navController.navigate("home") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(50.dp)), // Botón redondeado
                colors = ButtonDefaults.buttonColors(containerColor = orangeColor)
            ) {
                Text("Volver", color = Color.White)
            }
        }
    }
}
