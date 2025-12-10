package com.example.passwordmanager.ui.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.passwordmanager.ui.screens.AddPasswordSheet
import com.example.passwordmanager.ui.screens.HomeScreen
import com.example.passwordmanager.ui.screens.ViewPasswordSheet
import com.example.passwordmanager.ui.viewmodel.PasswordViewModel

object Routes {
    const val HOME = "home"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavHost(vm: PasswordViewModel, modifier: Modifier = Modifier) {

    val navController = rememberNavController()

    // Flags to show bottom sheets
    var showAddSheet by remember { mutableStateOf(false) }
    var showDetailsSheet by remember { mutableStateOf<Int?>(null) }

    NavHost(
        navController = navController,
        startDestination = Routes.HOME,
        modifier = modifier
    ) {

        composable(Routes.HOME) {
            HomeScreen(
                viewModel = vm,
                onOpenAdd = { showAddSheet = true },
                onOpenDetails = { id -> showDetailsSheet = id }
            )
        }
    }

    /** ADD SHEET **/
    if (showAddSheet) {
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

        ModalBottomSheet(
            onDismissRequest = { showAddSheet = false },
            sheetState = sheetState,
            containerColor = Color.White,
            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
        ) {
            AddPasswordSheet(
                onClose = { showAddSheet = false },
                viewModel = vm
            )
        }
    }

    /** DETAILS SHEET **/
    showDetailsSheet?.let { id ->
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

        ModalBottomSheet(
            onDismissRequest = { showDetailsSheet = null },
            sheetState = sheetState,
            containerColor = Color.White,
            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
        ) {
            ViewPasswordSheet(
                itemId = id,
                onClose = { showDetailsSheet = null },
                viewModel = vm
            )
        }
    }
}
