package com.example.appmudanza.ui.theme.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appmudanza.R
import com.example.appmudanza.viewmodel.VehicleViewModel

@Composable
fun ConductorCard(
    title: String,
    description: String,
    imagesRes: Int,
    valoration: Float,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(0.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(0.5.dp, Color(0xFFE2E6ED)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .background(Color(0xFFDCE8F5), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = title.take(2).uppercase(),
                    color = Color(0xFF1B3A6B),
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.sp
                )
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF1B3A6B),
                    fontSize = 15.sp
                )
                Spacer(Modifier.height(2.dp))
                Text(text = description, fontSize = 13.sp, color = Color(0xFF6B7A99))
                Spacer(Modifier.height(2.dp))
                Text(
                    text = "Valoración: $valoration / 5",
                    fontSize = 12.sp,
                    color = Color(0xFF6B7A99)
                )
            }
            Icon(
                Icons.Outlined.ArrowForward,
                contentDescription = null,
                tint = Color(0xFFB0BEC5),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlquilerScreen(
    onBack: () -> Unit,
    vehicleViewModel: VehicleViewModel = viewModel(),
    onConductorClick: (Int) -> Unit,
    onVehicleClick: (Int) -> Unit,
) {
    val vehicles by vehicleViewModel.vehicles.collectAsState()
    var filterCapacity by remember { mutableStateOf("") }
    var selectedTab by remember { mutableStateOf(0) }
    val tabTitles = listOf("Con Conductor", "Sin Conductor")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Alquiler de Vehículos", color = Color.White) },
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
                    val filteredVehicles = vehicles
                        .filter { it.withDriver }
                        .filter { vehicle -> vehicle.capacity >= (filterCapacity.toIntOrNull() ?: 0) }

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        item {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    "Filtrar por capacidad",
                                    style = MaterialTheme.typography.titleSmall,
                                    color = Color(0xFF1B3A6B),
                                    fontWeight = FontWeight.Medium
                                )
                                Spacer(Modifier.height(8.dp))
                                OutlinedTextField(
                                    value = filterCapacity,
                                    onValueChange = { filterCapacity = it },
                                    label = { Text("Cantidad a transportar") },
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp),
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                                )
                            }
                        }
                        itemsIndexed(filteredVehicles) { index, vehicle ->
                            ConductorCard(
                                title = vehicle.driver,
                                description = vehicle.type,
                                imagesRes = R.drawable.ic_launcher_foreground,
                                valoration = vehicle.valoration,
                                onClick = { onConductorClick(index) }
                            )
                        }
                        item { Spacer(Modifier.height(16.dp)) }
                    }
                }

                1 -> {
                    val filteredVehicles = vehicles
                        .filter { !it.withDriver }
                        .filter { vehicle -> vehicle.capacity >= (filterCapacity.toIntOrNull() ?: 0) }

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        item {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    "Filtrar por capacidad",
                                    style = MaterialTheme.typography.titleSmall,
                                    color = Color(0xFF1B3A6B),
                                    fontWeight = FontWeight.Medium
                                )
                                Spacer(Modifier.height(8.dp))
                                OutlinedTextField(
                                    value = filterCapacity,
                                    onValueChange = { filterCapacity = it },
                                    label = { Text("Cantidad a transportar") },
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp),
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                                )
                            }
                        }
                        itemsIndexed(filteredVehicles) { index, vehicle ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 6.dp)
                                    .clickable {onVehicleClick(index)},

                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                border = BorderStroke(0.5.dp, Color(0xFFE2E6ED)),
                                elevation = CardDefaults.cardElevation(0.dp)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(
                                        vehicle.plate,
                                        fontWeight = FontWeight.Medium,
                                        color = Color(0xFF1B3A6B),
                                        fontSize = 15.sp
                                    )
                                    Spacer(Modifier.height(4.dp))
                                    Text("Tipo: ${vehicle.type}", fontSize = 13.sp, color = Color(0xFF6B7A99))
                                    Text("Capacidad: ${vehicle.capacity}", fontSize = 13.sp, color = Color(0xFF6B7A99))
                                }
                            }
                        }
                        item { Spacer(Modifier.height(16.dp)) }
                    }
                }
            }
        }
    }
}