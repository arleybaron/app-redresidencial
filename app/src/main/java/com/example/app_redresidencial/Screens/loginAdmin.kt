package com.example.app_redresidencial.Screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.app_redresidencial.R
import com.example.app_redresidencial.dataclases.LoginViewModel

/*@Composable
fun loginAdminScreen(
    navController: NavController,
    viewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
){
    Surface(modifier = Modifier
        .fillMaxSize()
    ) {
       Column(
           horizontalAlignment = Alignment.CenterHorizontally,
           verticalArrangement = Arrangement.Center
       ) {
           Text(text = "Iniciar Sesión")
               UserForm(){
                   email, password ->
                   Log.d("Red Residencial", "Logueando con $email y $password")
                   viewModel.signInWithEmailAndPassword(email, password){
                       navController.navigate("cargar-usuario")
                   }
               }
           Spacer(modifier = Modifier.height(15.dp))
       }
    }
}

@Composable
fun UserForm(
    onDone: (String, String) -> Unit = {email, pwd ->}
){
    val email = rememberSaveable {
        mutableStateOf("")
    }
    val password = rememberSaveable {
        mutableStateOf("")
    }
    val passwordVisible = rememberSaveable {
        mutableStateOf(false)
    }
    val valido = remember(email.value, password.value) {
        email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty()
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmailInput(emailState = email)
        PasswordInput(
            passwordState = password,
            labelId = "Password",
            passwordVisible = passwordVisible
        )
        SubmitButton(
            inputValido = valido
        ){
            onDone(email.value.trim(), password.value.trim())
            keyboardController?.hide()
        }
    }
}

@Composable
fun SubmitButton(
    inputValido: Boolean,
    onClic: ()->Unit
) {
    Button(
        onClick = onClic,
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth(),
        shape = CircleShape,
        enabled = inputValido
        ) {
        Text("Login",
        modifier = Modifier
            .padding(5.dp)
        )
    }
}

@Composable
fun PasswordInput(
    passwordState: MutableState<String>,
    labelId: String,
    passwordVisible: MutableState<Boolean>) {
    val visualTransformation = if(passwordVisible.value)
        VisualTransformation.None
    else PasswordVisualTransformation()
    OutlinedTextField(
        value = passwordState.value,
        onValueChange = {passwordState.value = it},
        label = {Text(text = labelId)},
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        ),
        modifier = Modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        visualTransformation = visualTransformation,
        trailingIcon = {
            if(passwordState.value.isNotBlank()){
                PasswordVisbleIcon(passwordVisible)
            }else null
        }
    )
}

@Composable
fun PasswordVisbleIcon(
    passwordVisible: MutableState<Boolean>
) {
    val image = if(passwordVisible.value)
        Icons.Default.VisibilityOff
    else
        Icons.Default.Visibility
    IconButton(onClick = {
        passwordVisible.value =!passwordVisible.value
    }) {
        Icon(
            imageVector = image,
            contentDescription = "")
    }
}

@Composable
fun EmailInput(
    emailState: MutableState<String>,
    labelId: String = "Email"
) {
    InputField(
        valueState = emailState,
        labelId = labelId,
        keyboardType = KeyboardType.Email
    )
}

@Composable
fun InputField(
    valueState: MutableState<String>,
    labelId: String,
    keyboardType: KeyboardType,
    isSingleLine: Boolean = true
){
    OutlinedTextField(
        value = valueState.value,
        onValueChange = {valueState.value = it},
        label = { Text(text = labelId)},
        singleLine = isSingleLine,
        modifier = Modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            keyboardType =  keyboardType
        )
    )
}*/

@Composable
fun loginAdminScreen(
    navController: NavController,
    viewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val orangeColor = Color(0xFFFF9F24)
    val purpleColor = Color(0xFF7445F5)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(orangeColor)
    ) {
        Column(
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            // Imagen del logo
            Image(
                painter = painterResource(id = R.drawable.logo_bn),
                contentDescription = "Logo Red Residencial",
                modifier = Modifier
                    .padding(top = 100.dp)
                    .size(150.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }

        // Formulario de inicio de sesión
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
                text = "Iniciar Sesión",
                color = purpleColor,
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            UserForm { email, password ->
                viewModel.signInWithEmailAndPassword(email, password) {
                    navController.navigate(Routes.PantallaInicio.route)
                }
            }
        }
    }
}

@Composable
fun UserForm(
    onDone: (String, String) -> Unit = { _, _ -> }
) {
    val email = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val passwordVisible = rememberSaveable { mutableStateOf(false) }
    val valido = remember(email.value, password.value) {
        email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty()
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmailInput(emailState = email)
        PasswordInput(
            passwordState = password,
            labelId = "Contraseña",
            passwordVisible = passwordVisible
        )
        SubmitButton(inputValido = valido) {
            onDone(email.value.trim(), password.value.trim())
        }
    }
}

@Composable
fun SubmitButton(
    inputValido: Boolean,
    onClic: ()->Unit,
) {
    val orangeColor = Color(0xFFFF9F24)
    Button(
        onClick = onClic,
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = orangeColor),
        shape = CircleShape,
        enabled = inputValido
    ) {
        Text("Login",
            modifier = Modifier
                .padding(5.dp)
        )
    }
}

@Composable
fun PasswordInput(
    passwordState: MutableState<String>,
    labelId: String,
    passwordVisible: MutableState<Boolean>) {
    val visualTransformation = if(passwordVisible.value)
        VisualTransformation.None
    else PasswordVisualTransformation()
    OutlinedTextField(
        value = passwordState.value,
        onValueChange = {passwordState.value = it},
        label = {Text(text = labelId)},
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        ),
        modifier = Modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        visualTransformation = visualTransformation,
        trailingIcon = {
            if(passwordState.value.isNotBlank()){
                PasswordVisbleIcon(passwordVisible)
            }else null
        }
    )
}

@Composable
fun PasswordVisbleIcon(
    passwordVisible: MutableState<Boolean>
) {
    val image = if(passwordVisible.value)
        Icons.Default.VisibilityOff
    else
        Icons.Default.Visibility
    IconButton(onClick = {
        passwordVisible.value =!passwordVisible.value
    }) {
        Icon(
            imageVector = image,
            contentDescription = "")
    }
}

@Composable
fun EmailInput(
    emailState: MutableState<String>,
    labelId: String = "Email"
) {
    InputField(
        valueState = emailState,
        labelId = labelId,
        keyboardType = KeyboardType.Email
    )
}

@Composable
fun InputField(
    valueState: MutableState<String>,
    labelId: String,
    keyboardType: KeyboardType,
    isSingleLine: Boolean = true
){
    OutlinedTextField(
        value = valueState.value,
        onValueChange = {valueState.value = it},
        label = { Text(text = labelId)},
        singleLine = isSingleLine,
        modifier = Modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            keyboardType =  keyboardType
        )
    )
}



