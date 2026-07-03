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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.filled.*
import androidx.compose.foundation.clickable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    onGoToHome: () -> Unit,
    onGoToMudanza: () -> Unit,
    onGoToIncidencias: () -> Unit,
    onGoToSettings: () -> Unit,
    onLogout: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ajustes", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Outlined.ArrowBack, contentDescription = "Volver", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF1B3A6B))
            )
        },
        bottomBar = {
            MainBottomBar(
                selectedRoute = "settings",
                onGoToHome = onGoToHome,
                onGoToMudanza = onGoToMudanza,
                onGoToIncidencias = onGoToIncidencias,
                onGoToSettings = onGoToSettings
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF8F9FC))
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .background(Color(0xFFDCE8F5), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Outlined.Person,
                            contentDescription = null,
                            tint = Color(0xFF1B3A6B),
                            modifier = Modifier.size(40.dp)
                        )
                    }

                    Spacer(Modifier.height(12.dp))

                    Text(
                        "Mi cuenta",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF1B3A6B)
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            Text(
                "Cuenta",
                fontSize = 12.sp,
                color = Color(0xFF6B7A99),
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 8.dp)
            )


                    SettingsItem(
                        icon = Icons.Outlined.ArrowBack,
                        label = "Cerrar sesión",
                        textColor = Color(0xFFC62828),
                        onClick = onLogout
                    )
                }
            }
        }


@Composable
fun SettingsItem(
    icon: ImageVector,
    label: String,
    textColor: Color = Color(0xFF1B3A6B),
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            icon,
            contentDescription = label,
            tint = textColor,
            modifier = Modifier.size(22.dp)
        )

        Spacer(Modifier.width(14.dp))

        Text(
            label,
            fontSize = 14.sp,
            color = textColor,
            modifier = Modifier.weight(1f)
        )

        Icon(
            Icons.Outlined.ArrowForward,
            contentDescription = null,
            tint = Color(0xFFB0BEC5),
            modifier = Modifier.size(18.dp)
        )
    }
}