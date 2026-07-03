package com.example.appmudanza.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appmudanza.data.entity.Mudanza
import com.example.appmudanza.viewmodel.MudanzaViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MudanzaCard(
    mudanza: Mudanza,
    mudanzaViewModel: MudanzaViewModel,
    snackbarHostState: SnackbarHostState
) {
    var showGestionar by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val fechaFormateada = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        .format(Date(mudanza.fecha))

    val badgeBg = if (mudanza.estado == "activa") Color(0xFFE6F4EA) else Color(0xFFFDECEA)
    val badgeText = if (mudanza.estado == "activa") Color(0xFF2E7D32) else Color(0xFFC62828)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // Ruta origen → destino
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(Color(0xFF1B3A6B), RoundedCornerShape(4.dp))
                )
                Spacer(Modifier.width(6.dp))
                Text(
                    mudanza.origen,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF1B3A6B),
                    fontSize = 14.sp
                )
                Spacer(Modifier.width(6.dp))
                Divider(
                    modifier = Modifier.weight(1f),
                    color = Color(0xFFC8D4E8),
                    thickness = 1.dp
                )
                Spacer(Modifier.width(6.dp))
                Text(
                    mudanza.destino,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF1B3A6B),
                    fontSize = 14.sp
                )
                Spacer(Modifier.width(6.dp))
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(Color(0xFF1B3A6B), RoundedCornerShape(4.dp))
                )
            }

            Spacer(Modifier.height(8.dp))

            Text(
                "$fechaFormateada · ${if (mudanza.withDriver) mudanza.driverName else "Sin conductor"}",
                fontSize = 12.sp,
                color = Color(0xFF6B7A99)
            )

            Spacer(Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .background(badgeBg, RoundedCornerShape(20.dp))
                        .padding(horizontal = 10.dp, vertical = 3.dp)
                ) {
                    Text(
                        mudanza.estado.replaceFirstChar { it.uppercase() },
                        fontSize = 11.sp,
                        color = badgeText,
                        fontWeight = FontWeight.Medium
                    )
                }

                // Botón papelera solo si está cancelada
                if (mudanza.estado == "cancelada") {
                    IconButton(
                        onClick = {
                            mudanzaViewModel.deleteMudanza(mudanza.id)
                            scope.launch {
                                snackbarHostState.showSnackbar("Mudanza eliminada")
                            }
                        },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            Icons.Outlined.Delete,
                            contentDescription = "Eliminar",
                            tint = Color(0xFFC62828),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = { showGestionar = !showGestionar },
                modifier = Modifier.fillMaxWidth().height(44.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1B3A6B))
            ) {
                Text(
                    if (showGestionar) "Cerrar" else "Gestionar reserva",
                    fontSize = 13.sp
                )
            }

            if (showGestionar) {
                Spacer(Modifier.height(12.dp))

                OutlinedButton(
                    onClick = {
                        mudanzaViewModel.cancelarMudanza(mudanza.id)
                        scope.launch {
                            snackbarHostState.showSnackbar("Reserva cancelada")
                        }
                        showGestionar = false
                    },
                    modifier = Modifier.fillMaxWidth().height(44.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFC62828)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFC62828))
                ) {
                    Text("Cancelar reserva", fontSize = 13.sp)
                }

                Spacer(Modifier.height(8.dp))

                Button(
                    onClick = { showDatePicker = true },
                    modifier = Modifier.fillMaxWidth().height(44.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2D5FA8))
                ) {
                    Text("Cambiar fecha", fontSize = 13.sp)
                }

                if (showDatePicker) {
                    val datePickerState = rememberDatePickerState()
                    DatePickerDialog(
                        onDismissRequest = { showDatePicker = false },
                        confirmButton = {
                            Button(
                                onClick = {
                                    val nuevaFecha = datePickerState.selectedDateMillis ?: mudanza.fecha
                                    mudanzaViewModel.cambiarFecha(mudanza.id, nuevaFecha)
                                    scope.launch {
                                        snackbarHostState.showSnackbar("Fecha actualizada correctamente")
                                    }
                                    showDatePicker = false
                                    showGestionar = false
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1B3A6B))
                            ) {
                                Text("Confirmar")
                            }
                        },
                        dismissButton = {
                            OutlinedButton(onClick = { showDatePicker = false }) {
                                Text("Cancelar")
                            }
                        }
                    ) {
                        DatePicker(state = datePickerState)
                    }
                }
            }
        }
    }
}