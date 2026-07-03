package com.example.appmudanza.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appmudanza.viewmodel.VehicleViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VehicleRegistrationScreen(
    onBack: () -> Unit,
    vehicleViewModel: VehicleViewModel = viewModel()
) {
    val vehicles by vehicleViewModel.vehicles.collectAsState()
    var selectedTab by remember { mutableStateOf(0) }
    val tabTitles = listOf("Con Conductor", "Sin Conductor")

    var plate by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var capacity by remember { mutableStateOf("") }
    var driver by remember { mutableStateOf("") }
    var licenseType by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var valoration by remember { mutableStateOf("") }

    var plateNoDriver by remember { mutableStateOf("") }
    var typeNoDriver by remember { mutableStateOf("") }
    var capacityNoDriver by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registro de Vehículos", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Outlined.ArrowBack, contentDescription = null, tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF1B3A6B))
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color(0xFFF8F9FC))
        ) {
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = Color(0xFF1B3A6B),
                contentColor = Color.White
            ) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = {
                            Text(
                                title,
                                color = if (selectedTab == index) Color.White else Color.White.copy(alpha = 0.6f)
                            )
                        }
                    )
                }
            }

            when (selectedTab) {
                0 -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        item {
                            Text(
                                "Nuevo vehículo con conductor",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color(0xFF1B3A6B),
                                fontWeight = FontWeight.Medium
                            )
                            Spacer(Modifier.height(8.dp))
                            OutlinedTextField(value = driver, onValueChange = { driver = it },
                                label = { Text("Conductor") }, modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp))
                            Spacer(Modifier.height(8.dp))
                            OutlinedTextField(value = description, onValueChange = { description = it },
                                label = { Text("Descripción") }, modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp))
                            Spacer(Modifier.height(8.dp))
                            OutlinedTextField(value = plate, onValueChange = { plate = it },
                                label = { Text("Matrícula") }, modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp))
                            Spacer(Modifier.height(8.dp))
                            OutlinedTextField(value = type, onValueChange = { type = it },
                                label = { Text("Tipo") }, modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp))
                            Spacer(Modifier.height(8.dp))
                            OutlinedTextField(value = capacity, onValueChange = { capacity = it },
                                label = { Text("Capacidad") }, modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
                            Spacer(Modifier.height(8.dp))
                            OutlinedTextField(value = licenseType, onValueChange = { licenseType = it },
                                label = { Text("Tipo de Carnet") }, modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp))
                            Spacer(Modifier.height(8.dp))
                            OutlinedTextField(value = valoration, onValueChange = { valoration = it },
                                label = { Text("Valoración (0-5)") }, modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal))
                            Spacer(Modifier.height(16.dp))
                            Button(
                                onClick = {
                                    vehicleViewModel.addVehicle(
                                        plate, type, capacity.toIntOrNull() ?: 0,
                                        driver, licenseType = licenseType, withDriver = true,
                                        description, valoration = valoration.toFloatOrNull() ?: 0f
                                    )
                                    plate = ""; type = ""; capacity = ""; driver = ""
                                    licenseType = ""; description = ""; valoration = ""
                                },
                                modifier = Modifier.fillMaxWidth().height(50.dp),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1B3A6B))
                            ) {
                                Text("Guardar vehículo con Conductor", fontWeight = FontWeight.Medium)
                            }
                            Spacer(Modifier.height(24.dp))
                            Text(
                                "Vehículos con Conductor Registrados",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color(0xFF1B3A6B),
                                fontWeight = FontWeight.Medium
                            )
                            Spacer(Modifier.height(8.dp))
                        }

                        items(vehicles.filter { it.withDriver }) { vehicle ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                elevation = CardDefaults.cardElevation(0.dp)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(vehicle.driver, fontWeight = FontWeight.Medium,
                                        color = Color(0xFF1B3A6B), fontSize = 15.sp)
                                    Spacer(Modifier.height(4.dp))
                                    Text("Matrícula: ${vehicle.plate}", fontSize = 13.sp, color = Color(0xFF6B7A99))
                                    Text("Tipo: ${vehicle.type}", fontSize = 13.sp, color = Color(0xFF6B7A99))
                                    Text("Capacidad: ${vehicle.capacity}", fontSize = 13.sp, color = Color(0xFF6B7A99))
                                    Text("Carnet: ${vehicle.licenseType}", fontSize = 13.sp, color = Color(0xFF6B7A99))
                                    Text("Valoración: ${vehicle.valoration}/5", fontSize = 13.sp, color = Color(0xFF6B7A99))
                                }
                            }
                        }
                        item { Spacer(Modifier.height(20.dp)) }
                    }
                }

                1 -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        item {
                            Text(
                                "Nuevo vehículo sin conductor",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color(0xFF1B3A6B),
                                fontWeight = FontWeight.Medium
                            )
                            Spacer(Modifier.height(8.dp))
                            OutlinedTextField(value = plateNoDriver, onValueChange = { plateNoDriver = it },
                                label = { Text("Matrícula") }, modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp))
                            Spacer(Modifier.height(8.dp))
                            OutlinedTextField(value = typeNoDriver, onValueChange = { typeNoDriver = it },
                                label = { Text("Tipo") }, modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp))
                            Spacer(Modifier.height(8.dp))
                            OutlinedTextField(value = capacityNoDriver, onValueChange = { capacityNoDriver = it },
                                label = { Text("Capacidad") }, modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
                            Spacer(Modifier.height(16.dp))
                            Button(
                                onClick = {
                                    vehicleViewModel.addVehicle(
                                        plateNoDriver, typeNoDriver,
                                        capacityNoDriver.toIntOrNull() ?: 0,
                                        driver = "", licenseType = "", withDriver = false
                                    )
                                    plateNoDriver = ""; typeNoDriver = ""; capacityNoDriver = ""
                                },
                                modifier = Modifier.fillMaxWidth().height(50.dp),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1B3A6B))
                            ) {
                                Text("Guardar Vehículo sin Conductor", fontWeight = FontWeight.Medium)
                            }
                            Spacer(Modifier.height(24.dp))
                            Text(
                                "Vehículos sin Conductor Registrados",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color(0xFF1B3A6B),
                                fontWeight = FontWeight.Medium
                            )
                            Spacer(Modifier.height(8.dp))
                        }

                        items(vehicles.filter { !it.withDriver }) { vehicle ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                elevation = CardDefaults.cardElevation(0.dp)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(vehicle.plate, fontWeight = FontWeight.Medium,
                                        color = Color(0xFF1B3A6B), fontSize = 15.sp)
                                    Spacer(Modifier.height(4.dp))
                                    Text("Tipo: ${vehicle.type}", fontSize = 13.sp, color = Color(0xFF6B7A99))
                                    Text("Capacidad: ${vehicle.capacity}", fontSize = 13.sp, color = Color(0xFF6B7A99))
                                }
                            }
                        }
                        item { Spacer(Modifier.height(20.dp)) }
                    }
                }
            }
        }
    }
}