package com.example.app_redresidencial.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.app_redresidencial.R
import com.tuapp.viewmodel.CodigoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun home(navController: NavController, codigoViewModel: CodigoViewModel) {
    val code = remember { androidx.compose.runtime.mutableStateOf("") }
    val codigoValido by codigoViewModel.codigoValido.collectAsState()
    val usuarioAsociado by codigoViewModel.usuarioAsociado.collectAsState()
    val orangeColor = Color(0xFFFF9F24)
    val purpleColor = Color(0xFF7445F5)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(orangeColor)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp)
                .shadow(8.dp, shape = RoundedCornerShape(16.dp))
                .background(Color.White, shape = RoundedCornerShape(16.dp))
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Por favor ingrese su código",
                color = purpleColor,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            OutlinedTextField(
                value = code.value,
                onValueChange = { newCode ->
                    if (newCode.length <= 6 && newCode.all { it.isDigit() }) {
                        code.value = newCode
                    }
                },
                label = { Text("Código de 6 dígitos", color = purpleColor) },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = purpleColor,
                    unfocusedBorderColor = purpleColor,
                    containerColor = Color.White,
                    cursorColor = purpleColor
                ),
                textStyle = TextStyle(color = purpleColor),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )

            Button(
                onClick = {
                    codigoViewModel.verificarCodigo(code.value)
                },
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = orangeColor)
            ) {
                Text(text = "Acceder", color = Color.White)
            }

            if (codigoValido) {
                navController.navigate("datos-usuario") // Navegar a la nueva pantalla
            }
        }

        TextButton(
            onClick = { navController.navigate("login") },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 30.dp)
        ) {
            Text(text = "Ingresar como administrador", color = Color.White)
        }

        Image(
            painter = painterResource(id = R.drawable.logo_bn),
            contentDescription = "Logo Red Residencial",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 120.dp)
        )
    }
}


/*@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun home(navController: NavController) {
    val code = remember { androidx.compose.runtime.mutableStateOf("") }
    val orangeColor = Color(0xFFFF9F24)
    val purpleColor = Color(0xFF7445F5)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(orangeColor)
    ) {
        // Caja blanca con sombra
        Column(
            modifier = Modifier
                .align(Alignment.Center) // Centra la caja en la pantalla
                .padding(16.dp)
                .shadow(8.dp, shape = RoundedCornerShape(16.dp)) // Sombra con bordes redondeados
                .background(Color.White, shape = RoundedCornerShape(16.dp))
                .padding(24.dp), // Espaciado interno
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Título en la caja
            Text(
                text = "Por favor ingrese su código",
                color = purpleColor,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            // Campo de texto para ingresar el código
            OutlinedTextField(
                value = code.value,
                onValueChange = { newCode ->
                    if (newCode.length <= 6 && newCode.all { it.isDigit() }) {
                        code.value = newCode
                    }
                },
                label = { Text("Código de 6 dígitos", color = purpleColor) },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = purpleColor,
                    unfocusedBorderColor = purpleColor,
                    containerColor = Color.White,
                    cursorColor = purpleColor
                ),
                textStyle = TextStyle(color = purpleColor),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
            )

            // Botón "Acceder"
            Button(
                onClick = {
                    // Lógica para acceder
                },
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = orangeColor)
            ) {
                Text(text = "Acceder", color = Color.White)
            }
        }

        // Botón "Ingresar como administrador" al final
        TextButton(
            onClick = {
                navController.navigate("login")
                //navController.navigate(Routes.PantallaInicio.route)
                //navController.navigate("agregar-poder")
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 30.dp)
        ) {
            Text(text = "Ingresar como administrador", color = Color.White)
        }

        Image(
            painter = painterResource(id = R.drawable.logo_bn),
            contentDescription = "Logo Red Residencial",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 120.dp)
        )
   */


