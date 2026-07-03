package com.example.appmudanza.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    onGoToMudanza: () -> Unit,
    onGoToVehicleRegistration: () -> Unit,
    onGoToAlquiler: () -> Unit,
    onGoToIncidencias: () -> Unit,
    onGoToSettings: () -> Unit
) {
    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 0.dp,
                modifier = Modifier.clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            ) {
                NavigationBarItem(
                    selected = true,
                    onClick = {},
                    icon = { Icon(Icons.Outlined.Home, contentDescription = "Inicio") },
                    label = { Text("Inicio", fontSize = 10.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF1B3A6B),
                        selectedTextColor = Color(0xFF1B3A6B),
                        indicatorColor = Color(0xFFDCE8F5)
                    )
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onGoToMudanza,
                    icon = { Icon(Icons.Outlined.MoveToInbox, contentDescription = "Mudanzas") },
                    label = { Text("Mudanzas", fontSize = 10.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = Color(0xFF6B7A99),
                        unselectedTextColor = Color(0xFF6B7A99)
                    )
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onGoToIncidencias,
                    icon = { Icon(Icons.Outlined.ReportProblem, contentDescription = "Incidencias") },
                    label = { Text("Incidencias", fontSize = 10.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = Color(0xFF6B7A99),
                        unselectedTextColor = Color(0xFF6B7A99)
                    )
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onGoToSettings,
                    icon = { Icon(Icons.Outlined.ManageAccounts, contentDescription = "Ajustes") },
                    label = { Text("Ajustes", fontSize = 10.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = Color(0xFF6B7A99),
                        unselectedTextColor = Color(0xFF6B7A99)
                    )
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFFFAFAFA), Color(0xFF2D5FA8))
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            "MudanzApp",
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Text(
                            "¿Qué necesitas hoy?",
                            fontSize = 14.sp,
                            color = Color.Black.copy(alpha = 0.7f)
                        )
                    }
                    IconButton(
                        onClick = onGoToSettings,
                        modifier = Modifier
                            .size(42.dp)
                            .background(Color.Black.copy(alpha = 0.15f), CircleShape)
                    ) {
                        Icon(Icons.Outlined.ManageAccounts, contentDescription = "Ajustes", tint = Color.White)
                    }
                }

                Spacer(Modifier.height(32.dp))

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        HomeCard(
                            label = "Alquiler",
                            icon = Icons.Outlined.AccountCircle,
                            onClick = onGoToAlquiler,
                            modifier = Modifier.weight(1f).fillMaxHeight()
                        )
                        HomeCard(
                            label = "Vehículos",
                            icon = Icons.Outlined.LocalShipping,
                            onClick = onGoToVehicleRegistration,
                            modifier = Modifier.weight(1f).fillMaxHeight()
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        HomeCard(
                            label = "Mudanzas",
                            icon = Icons.Outlined.MoveToInbox,
                            onClick = onGoToMudanza,
                            modifier = Modifier.weight(1f).fillMaxHeight()
                        )
                        HomeCard(
                            label = "Incidencias",
                            icon = Icons.Outlined.ReportProblem,
                            onClick = onGoToIncidencias,
                            modifier = Modifier.weight(1f).fillMaxHeight()
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HomeCard(
    label: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Gray.copy(alpha = 0.2f)),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(Color.White.copy(alpha = 0.2f), RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    icon,
                    contentDescription = label,
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
            Spacer(Modifier.height(12.dp))
            Text(
                label,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
        }
    }
}