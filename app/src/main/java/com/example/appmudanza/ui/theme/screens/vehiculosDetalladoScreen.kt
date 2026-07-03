package com.example.appmudanza.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.DirectionsCar
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appmudanza.data.entity.Vehicle
import com.example.appmudanza.viewmodel.MudanzaViewModel
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun vehiculosDetalladoScreen(
    vehicle: Vehicle,
    onBack: () -> Unit,
    mudanzaViewModel: MudanzaViewModel = viewModel()
) {
    var showDatePicker by remember { mutableStateOf(false) }
    var origen by remember { mutableStateOf("") }
    var destino by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text(vehicle.plate, color = Color.White) }, // 👈 usamos la matrícula como título
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Outlined.ArrowBack, contentDescription = "Volver", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF1B3A6B))
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF8F9FC))
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Icono de vehículo en vez de iniciales
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color(0xFFDCE8F5), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Outlined.DirectionsCar,
                        contentDescription = null,
                        tint = Color(0xFF1B3A6B),
                        modifier = Modifier.size(48.dp)
                    )
                }
            }

            Text(
                text = vehicle.plate,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1B3A6B),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = vehicle.type,
                fontSize = 14.sp,
                color = Color(0xFF6B7A99)
            )

            Spacer(Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    InfoRow(label = "Matrícula", value = vehicle.plate)
                    Divider(color = Color(0xFFE2E6ED), modifier = Modifier.padding(vertical = 8.dp))
                    InfoRow(label = "Tipo", value = vehicle.type)
                    Divider(color = Color(0xFFE2E6ED), modifier = Modifier.padding(vertical = 8.dp))
                    InfoRow(label = "Capacidad", value = "${vehicle.capacity} m³")
                }
            }

            Spacer(Modifier.height(24.dp))

            Text(
                "Reservar mudanza",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF1B3A6B)
            )
            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = origen,
                onValueChange = { origen = it },
                label = { Text("Origen") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = destino,
                onValueChange = { destino = it },
                label = { Text("Destino") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = { showDatePicker = true },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1B3A6B))
            ) {
                Text("Seleccionar fecha y reservar", fontWeight = FontWeight.Medium)
            }

            if (showDatePicker) {
                val datePickerState = rememberDatePickerState()
                DatePickerDialog(
                    onDismissRequest = { showDatePicker = false },
                    confirmButton = {
                        Button(
                            onClick = {
                                val selectedDate = datePickerState.selectedDateMillis ?: 0L
                                mudanzaViewModel.addMudanza(
                                    origen = origen,
                                    destino = destino,
                                    fecha = selectedDate,
                                    withDriver = false, // 👈 siempre false para vehículos sin conductor
                                    driverName = ""     // 👈 sin conductor
                                )
                                scope.launch {
                                    snackbarHostState.showSnackbar("¡Reserva confirmada!")
                                }
                                showDatePicker = false
                                origen = ""
                                destino = ""
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

            Spacer(Modifier.height(24.dp))
        }
    }
}