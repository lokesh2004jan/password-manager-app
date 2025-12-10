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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.passwordmanager.ui.components.PasswordCard
import com.example.passwordmanager.ui.theme.Blue500
import com.example.passwordmanager.ui.viewmodel.PasswordViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: PasswordViewModel,
    onOpenAdd: () -> Unit,
    onOpenDetails: (Int) -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    // Show errors with Snackbar
    LaunchedEffect(state.error) {
        state.error?.let {
            snackbarHostState.showSnackbar(it)
        }
    }

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(88.dp)
                    .background(Color(0xFFF6F8FE))   // light blue header
            ) {
                Text(
                    "Password Manager",
                    modifier = Modifier.padding(start = 20.dp, top = 50.dp),
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = { onOpenAdd() },
                containerColor = Blue500
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add New", tint = Color.White)
            }
        },

        snackbarHost = { SnackbarHost(snackbarHostState) },

        containerColor = Color(0xFFF6F8FE) // FULL SCREEN background
    ) { padding ->

        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            Divider(color = Color(0xFFE8E8E8))

            Spacer(Modifier.height(12.dp))

            // Show loading
            if (state.isLoading) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            // Empty state
            else if (state.passwords.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No passwords saved yet.\nTap + to add one!", textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                }
            }

            // Password list
            else {
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp) // more spacing between cards
                ) {
                    items(state.passwords) { item ->
                        PasswordCard(item, onClick = { onOpenDetails(item.id) })
                    }
                }
            }
        }
    }
}
