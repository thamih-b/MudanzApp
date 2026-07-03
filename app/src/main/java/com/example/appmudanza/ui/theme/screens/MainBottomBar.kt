package com.example.appmudanza.ui.theme.screens

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.filled.*

@Composable
fun MainBottomBar(
    selectedRoute: String,
    onGoToHome: () -> Unit,
    onGoToMudanza: () -> Unit,
    onGoToIncidencias: () -> Unit,
    onGoToSettings: () -> Unit
) {

    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 0.dp,
        modifier = Modifier.clip(
            RoundedCornerShape(
                topStart = 24.dp,
                topEnd = 24.dp
            )
        )
    ) {

        NavigationBarItem(
            selected = selectedRoute == "home",
            onClick = onGoToHome,
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Home,
                    contentDescription = "Inicio"
                )
            },
            label = {
                Text(
                    "Inicio",
                    fontSize = 10.sp
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF1B3A6B),
                selectedTextColor = Color(0xFF1B3A6B),
                indicatorColor = Color(0xFFDCE8F5),
                unselectedIconColor = Color(0xFF6B7A99),
                unselectedTextColor = Color(0xFF6B7A99)
            )
        )

        NavigationBarItem(
            selected = selectedRoute == "mudanza",
            onClick = onGoToMudanza,
            icon = {
                Icon(
                    imageVector = Icons.Outlined.MoveToInbox,
                    contentDescription = "Mudanzas"
                )

            },
            label = {
                Text(
                    "Mudanzas",
                    fontSize = 10.sp
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF1B3A6B),
                selectedTextColor = Color(0xFF1B3A6B),
                indicatorColor = Color(0xFFDCE8F5),
                unselectedIconColor = Color(0xFF6B7A99),
                unselectedTextColor = Color(0xFF6B7A99)
            )
        )

        NavigationBarItem(
            selected = selectedRoute == "incidencias",
            onClick = onGoToIncidencias,
            icon = {
                Icon(
                    imageVector = Icons.Outlined.ReportProblem,
                    contentDescription = "Ajustes"
                )
            },
            label = {
                Text(
                    "Incidencias",
                    fontSize = 10.sp
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF1B3A6B),
                selectedTextColor = Color(0xFF1B3A6B),
                indicatorColor = Color(0xFFDCE8F5),
                unselectedIconColor = Color(0xFF6B7A99),
                unselectedTextColor = Color(0xFF6B7A99)
            )
        )

        NavigationBarItem(
            selected = selectedRoute == "settings",
            onClick = onGoToSettings,
            icon = {
                Icon(
                    imageVector = Icons.Outlined.ManageAccounts,
                    contentDescription = "Ajustes"
                )
            },
            label = {
                Text(
                    "Ajustes",
                    fontSize = 10.sp
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF1B3A6B),
                selectedTextColor = Color(0xFF1B3A6B),
                indicatorColor = Color(0xFFDCE8F5),
                unselectedIconColor = Color(0xFF6B7A99),
                unselectedTextColor = Color(0xFF6B7A99)
            )
        )
    }
}