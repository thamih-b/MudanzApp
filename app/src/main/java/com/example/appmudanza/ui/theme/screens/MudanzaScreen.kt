package com.example.appmudanza.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.appmudanza.viewmodel.MudanzaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MudanzaScreen(
    onBack: () -> Unit,
    onGoToHome: () -> Unit,
    onGoToMudanza: () -> Unit,
    onGoToIncidencias: () -> Unit,
    onGoToSettings: () -> Unit,
    mudanzaViewModel: MudanzaViewModel = viewModel()
) {
    val mudanzas by mudanzaViewModel.mudanzas.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Gestión de Mudanzas", color = Color.White) },
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
                selectedRoute = "mudanza",
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
                        "Sin mudanzas reservadas",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF6B7A99)
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "Reserva una mudanza desde el detalle de un conductor",
                        fontSize = 13.sp,
                        color = Color(0xFF6B7A99)
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(mudanzas) { mudanza ->
                        MudanzaCard(
                            mudanza = mudanza,
                            mudanzaViewModel = mudanzaViewModel,
                            snackbarHostState = snackbarHostState
                        )
                    }
                }
            }
        }
    }
}