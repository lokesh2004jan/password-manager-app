package com.example.passwordmanager.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.passwordmanager.ui.components.PasswordCard
import com.example.passwordmanager.ui.theme.Blue500
import com.example.passwordmanager.ui.theme.HeaderGradientStart
import com.example.passwordmanager.ui.theme.HeaderGradientEnd
import com.example.passwordmanager.ui.viewmodel.PasswordViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: PasswordViewModel,
    onOpenAdd: () -> Unit,
    onOpenDetails: (Int) -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(88.dp)
                    .background(Color(0xFFF6F8FE))
            ) {
                Text(
                    "Password Manager",
                    modifier = Modifier.padding(start = 20.dp, top = 50.dp),
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        },

        floatingActionButton = {
            FloatingActionButton(onClick = { onOpenAdd() }, containerColor = Blue500) {
                Icon(Icons.Default.Add, contentDescription = null, tint = Color.White)
            }
        },

        containerColor = Color(0xFFF3F5FA)
    ) { padding ->
        Column(Modifier.padding(padding)) {

            Divider(color = Color(0xFFE8E8E8))

            Spacer(Modifier.height(12.dp))

            LazyColumn(
                contentPadding = PaddingValues(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(state.passwords) { item ->
                    PasswordCard(item, onClick = { onOpenDetails(item.id) })
                }
            }
        }
    }
}
