package com.example.app_redresidencial.Screens

import android.content.Intent
import android.os.Build
import android.os.Environment
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat.startActivityForResult
import com.example.app_redresidencial.dataclases.Usuario
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import org.apache.poi.sl.usermodel.Sheet
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

/*@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun subirMasivo(){
    val listaUsuarios by remember { mutableStateOf(mutableListOf<Usuario>()) }
    var nombre by remember { mutableStateOf("") }
    var identificacion by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var hora_ingreso by remember { mutableStateOf("") }
    var poderes by remember { mutableStateOf("") }

    val permission = rememberMultiplePermissionsState(
        permissions = listOf(
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        )
    )

    LaunchedEffect(key1 = Unit) {
        permission.launchMultiplePermissionRequest()
    }

    leerExcel(listaUsuarios)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 20.dp,
                start = 8.dp,
                end = 8.dp,
                bottom = 8.dp
            )
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp, 4.dp),
            value = nombre,
            onValueChange = {
                nombre = it
            },
            label = {
                Text(text = "Identificación")
            }
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp, 4.dp),
            value = apellido,
            onValueChange = {
                apellido = it
            },
            label = {
                Text(text = "Nombre")
            }
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp, 4.dp),
            value = identificacion,
            onValueChange = {
                identificacion = it
            },
            label = {
                Text(text = "Apellido")
            }
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp, 4.dp),
            value = hora_ingreso,
            onValueChange = {
                hora_ingreso = it
            },
            label = {
                Text(text = "Hora de ingreso")
            }
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp, 4.dp),
            value = poderes,
            onValueChange = {
                poderes = it
            },
            label = {
                Text(text = "Poderes")
            }
        )

        Button(
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.CenterHorizontally),
            onClick = {
                val usuario = Usuario(
                    identificacion = identificacion,
                    nombre = nombre,
                    apellido = apellido,
                    hora_ingreso = hora_ingreso,
                    poderes = poderes
                )

                listaUsuarios.add(usuario)

                identificacion = ""
                nombre = ""
                apellido = ""
                hora_ingreso = ""
                poderes = ""
            }
        ) {
            Text(text = "Agregar Registro")
        }

        Button(
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.CenterHorizontally),
            onClick = {
                crearExcel(listaUsuarios)
            }
        ) {
            Text(text = "Crear Excel")
        }

        LazyColumn(
            modifier = Modifier
                .padding(8.dp)

        ) {
            items(listaUsuarios){
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                    ) {
                        Text(text = "Identificacion:  ${it.identificacion}")
                        Text(text = "Nombre :  ${it.nombre}")
                        Text(text = "Apellido:  ${it.apellido}")
                        Text(text = "Hora de ingreso:  ${it.hora_ingreso}")
                        Text(text = "Poderes:  ${it.poderes}")

                    }
                }
            }
        }
    }
}

fun crearExcel(listaRegistros: MutableList<Usuario>){
    val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
    val fileName = "Registros.xlsx"
    val workbook = XSSFWorkbook()
    val sheet = workbook.createSheet("usuarios")

    // Crear fila de encabezado
    val headerRow = sheet.createRow(0)
    headerRow.createCell(0).setCellValue("IDENTIFICACION")
    headerRow.createCell(1).setCellValue("NOMBRE")
    headerRow.createCell(2).setCellValue("APELLIDO")
    headerRow.createCell(3).setCellValue("HORA_INGRESO")
    headerRow.createCell(4).setCellValue("PODERES")

    //Crear Registros

    for (index in listaRegistros.indices){
        val row = sheet.createRow(index+1)
        row.createCell(0).setCellValue(listaRegistros[index].identificacion)
        row.createCell(1).setCellValue(listaRegistros[index].nombre)
        row.createCell(2).setCellValue(listaRegistros[index].apellido)
        row.createCell(3).setCellValue(listaRegistros[index].hora_ingreso)
        row.createCell(4).setCellValue(listaRegistros[index].poderes)
    }

    try {
        val fileOutputStream = FileOutputStream(
            File(path, fileName)
        )
        workbook.write(fileOutputStream)
        fileOutputStream.close()
        workbook.close()
    } catch (e: IOException){
        e.printStackTrace()
    }
}

fun leerExcel(listaRegistros: MutableList<Usuario>) {
    val fileName = "Registros.xlsx"
    val path = "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)}/$fileName"
    val file = File(path)

    if (!file.exists()) {
        println("El archivo no existe en la ruta especificada.")
        return
    }

    var fileInputStream: FileInputStream? = null
    try {
        fileInputStream = FileInputStream(file)
        val workbook = WorkbookFactory.create(fileInputStream)
        val sheet = workbook.getSheetAt(0)

        val lista = mutableListOf<String>()
        for (row in sheet) {
            for (cell in row) {
                val cellValue: String = when (cell.cellType) {
                    CellType.STRING -> cell.stringCellValue
                    CellType.NUMERIC -> cell.numericCellValue.toString()
                    else -> ""
                }
                lista.add(cellValue)
            }
        }

        for (i in lista.indices step 5) {
            if (i + 4 < lista.size) {
                listaRegistros.add(
                    Usuario(
                        identificacion = lista[i],
                        nombre = lista[i + 1],
                        apellido = lista[i + 2],
                        hora_ingreso = lista[i + 3],
                        poderes = lista[i + 4]
                    )
                )
            } else {
                println("Advertencia: Registro incompleto en los datos de Excel.")
            }
        }

        workbook.close()
    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        fileInputStream?.close()
    }
}*/