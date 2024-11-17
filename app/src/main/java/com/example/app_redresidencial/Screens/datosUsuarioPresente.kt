package com.example.app_redresidencial.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.app_redresidencial.ViewModel.PropiedadesViewModel

@Composable
fun datosUsuarioPresente(navController: NavHostController, propiedadesViewModel: PropiedadesViewModel) {
    // Simulación de datos cargados
    val usuarioActual by propiedadesViewModel.usuarioActual.collectAsState()
    val propiedadPrincipal = propiedadesViewModel.propiedadPrincipal
    val propiedadesAdicionales = propiedadesViewModel.propiedadesAdicionales


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        usuarioActual?.let {
            Text(
                text = "CÓDIGO: ${it.codigo}",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Código de usuario para acceder",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text("Identificación: ${it.identificacion}")
            Text("Nombre y apellido: ${it.nombre} ${it.apellido}")
            Text("Conjunto: ${it.conjunto}")
            Text(
                text = "Propiedad Principal: ${propiedadPrincipal?.tipo_propiedad}, " +
                        "${propiedadPrincipal?.descripcion}"
            )
            Spacer(modifier = Modifier.height(8.dp))
            if (propiedadesAdicionales.isNotEmpty()) {
                Text("Propiedades Adicionales:")
                propiedadesAdicionales.forEachIndexed { index, propiedad ->
                    Text(
                        text = "${index + 1}: ${propiedad.tipo_propiedad}, ${propiedad.descripcion}"
                    )
                }
            } else {
                Text("No tiene propiedades adicionales")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text("Hora de ingreso: ${it.hora_ingreso}")
        } ?: run {
            Text("Cargando datos del usuario...")
        }

        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = { navController.navigate("cargar-usuario") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Imprimir")
        }
    }
}

