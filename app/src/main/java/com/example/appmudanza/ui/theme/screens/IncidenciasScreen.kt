package com.example.appmudanza.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appmudanza.viewmodel.IncidenciaViewModel
import com.example.appmudanza.viewmodel.MudanzaViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IncidenciasScreen(
    onBack: () -> Unit,
    onGoToHome: () -> Unit,
    onGoToMudanza: () -> Unit,
    onGoToIncidencias: () -> Unit,
    onGoToSettings: () -> Unit,
    mudanzaViewModel: MudanzaViewModel = viewModel(),
    incidenciaViewModel: IncidenciaViewModel = viewModel()
) {
    val mudanzas by mudanzaViewModel.mudanzas.collectAsState()
    val incidencias by incidenciaViewModel.incidencias.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },

        topBar = {
            TopAppBar(
                title = { Text("Incidencias", color = Color.White) },

                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.Outlined.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1B3A6B)
                )
            )
        },

        bottomBar = {
            MainBottomBar(
                selectedRoute = "incidencias",
                onGoToHome = onGoToHome,
                onGoToMudanza = onGoToMudanza,
                onGoToIncidencias = onGoToIncidencias,
                onGoToSettings = onGoToSettings
            )
        }

    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF8F9FC))
        ) {
            if (mudanzas.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        "Sin mudanzas registradas",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF6B7A99)
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "Las mudanzas reservadas aparecerán aquí",
                        fontSize = 13.sp,
                        color = Color(0xFF6B7A99)
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(mudanzas) { mudanza ->
                        var showIncidencia by remember { mutableStateOf(false) }
                        var tipoSeleccionado by remember { mutableStateOf("") }
                        val tiposIncidencia = listOf("Queja", "Denuncia", "Otro")
                        val incidenciasMudanza = incidencias.filter { it.mudanzaId == mudanza.id }

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(0.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {

                                // Ruta
                                Text(
                                    text = "${mudanza.origen} → ${mudanza.destino}",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xFF1B3A6B)
                                )
                                Spacer(Modifier.height(4.dp))
                                Text(
                                    text = "Conductor: ${if (mudanza.withDriver) mudanza.driverName else "Sin conductor"}",
                                    fontSize = 13.sp,
                                    color = Color(0xFF6B7A99)
                                )
                                Spacer(Modifier.height(4.dp))

                                // Badge estado
                                val badgeBg = if (mudanza.estado == "activa") Color(0xFFE6F4EA) else Color(0xFFFDECEA)
                                val badgeText = if (mudanza.estado == "activa") Color(0xFF2E7D32) else Color(0xFFC62828)
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

                                Spacer(Modifier.height(12.dp))

                                Button(
                                    onClick = { showIncidencia = !showIncidencia },
                                    modifier = Modifier.fillMaxWidth().height(44.dp),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1B3A6B))
                                ) {
                                    Text(
                                        if (showIncidencia) "Cerrar" else "Abrir incidencia",
                                        fontSize = 13.sp
                                    )
                                }

                                if (showIncidencia) {
                                    Spacer(Modifier.height(12.dp))
                                    Text(
                                        "Tipo de incidencia:",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = Color(0xFF1B3A6B)
                                    )
                                    Spacer(Modifier.height(4.dp))

                                    tiposIncidencia.forEach { tipo ->
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            RadioButton(
                                                selected = tipoSeleccionado == tipo,
                                                onClick = { tipoSeleccionado = tipo },
                                                colors = RadioButtonDefaults.colors(
                                                    selectedColor = Color(0xFF1B3A6B)
                                                )
                                            )
                                            Text(tipo, fontSize = 13.sp, color = Color(0xFF1B3A6B))
                                        }
                                    }

                                    Spacer(Modifier.height(8.dp))

                                    Button(
                                        onClick = {
                                            if (tipoSeleccionado.isNotEmpty()) {
                                                incidenciaViewModel.addIncidencia(
                                                    mudanzaId = mudanza.id,
                                                    tipo = tipoSeleccionado,
                                                    origen = mudanza.origen,
                                                    destino = mudanza.destino,
                                                    fecha = System.currentTimeMillis()
                                                )
                                                scope.launch {
                                                    snackbarHostState.showSnackbar("Incidencia registrada correctamente")
                                                }
                                                showIncidencia = false
                                                tipoSeleccionado = ""
                                            } else {
                                                scope.launch {
                                                    snackbarHostState.showSnackbar("Selecciona un tipo de incidencia")
                                                }
                                            }
                                        },
                                        modifier = Modifier.fillMaxWidth().height(44.dp),
                                        shape = RoundedCornerShape(12.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2D5FA8))
                                    ) {
                                        Text("Confirmar incidencia", fontSize = 13.sp)
                                    }
                                }

                                if (incidenciasMudanza.isNotEmpty()) {
                                    Spacer(Modifier.height(12.dp))
                                    Divider(color = Color(0xFFE2E6ED))
                                    Spacer(Modifier.height(8.dp))
                                    Text(
                                        "Incidencias registradas:",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = Color(0xFF1B3A6B)
                                    )
                                    Spacer(Modifier.height(4.dp))
                                    incidenciasMudanza.forEach { incidencia ->
                                        val fecha = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                                            .format(Date(incidencia.fecha))
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 4.dp)
                                                .background(Color(0xFFF0F4FA), RoundedCornerShape(8.dp))
                                                .padding(horizontal = 12.dp, vertical = 6.dp),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(
                                                incidencia.tipo,
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Medium,
                                                color = Color(0xFF1B3A6B)
                                            )
                                            Text(
                                                fecha,
                                                fontSize = 12.sp,
                                                color = Color(0xFF6B7A99)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}