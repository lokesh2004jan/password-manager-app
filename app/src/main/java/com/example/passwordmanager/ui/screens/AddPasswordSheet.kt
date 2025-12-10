package com.example.passwordmanager.ui.screens

import androidx.compose.foundation.background



import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.passwordmanager.ui.components.AppButton
import com.example.passwordmanager.ui.components.AppTextField
import com.example.passwordmanager.data.repository.PasswordItem
import com.example.passwordmanager.ui.viewmodel.PasswordViewModel
import kotlinx.coroutines.launch
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPasswordSheet(
    onClose: () -> Unit,
    viewModel: PasswordViewModel
) {
    var account by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()   // ⬅️ Makes sheet stick to bottom
            .padding(horizontal = 20.dp)
    ) {

        // Drag handle – centered & Figma style
        Box(
            modifier = Modifier
                .padding(top = 10.dp, bottom = 20.dp)
                .size(width = 56.dp, height = 6.dp)
                .background(
                    color = androidx.compose.ui.graphics.Color(0xFFE6E9EE),
                    shape = RoundedCornerShape(6.dp)
                )
                .align(Alignment.CenterHorizontally)
        )

        Text("Add New Account", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(12.dp))

        AppTextField(account, { account = it }, "Account Name")
        Spacer(Modifier.height(12.dp))

        AppTextField(username, { username = it }, "Username / Email")
        Spacer(Modifier.height(12.dp))

        AppTextField(password, { password = it }, "Password")
        Spacer(Modifier.height(20.dp))

        Button(
            onClick = {
                if (account.isNotBlank() && username.isNotBlank() && password.isNotBlank()) {
                    viewModel.savePassword(
                        PasswordItem(accountType = account, username = username, password = password)
                    ) { onClose() }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(26.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = androidx.compose.ui.graphics.Color.Black,
                contentColor = androidx.compose.ui.graphics.Color.White
            )
        ) {
            Text("Add New Account")
        }


        Spacer(Modifier.height(20.dp))
    }
}
