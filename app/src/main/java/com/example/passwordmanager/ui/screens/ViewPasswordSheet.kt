package com.example.passwordmanager.ui.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.passwordmanager.data.repository.PasswordItem
import com.example.passwordmanager.ui.components.AppButton
import com.example.passwordmanager.ui.theme.AccentBlue
import com.example.passwordmanager.ui.theme.Danger
import com.example.passwordmanager.ui.viewmodel.PasswordViewModel
import kotlinx.coroutines.launch
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewPasswordSheet(
    itemId: Int,
    onClose: () -> Unit,
    viewModel: PasswordViewModel
) {
    var item by remember { mutableStateOf<PasswordItem?>(null) }
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(itemId) {
        item = viewModel.getPasswordById(itemId)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()     // ⬅️ full width, correct height from bottom
            .padding(horizontal = 20.dp)
    ) {

        Box(
            modifier = Modifier
                .padding(top = 10.dp, bottom = 20.dp)
                .size(56.dp, 6.dp)
                .background(
                    color = androidx.compose.ui.graphics.Color(0xFFE6E9EE),
                    shape = RoundedCornerShape(6.dp)
                )
                .align(Alignment.CenterHorizontally)
        )
        Text(
            "Account Details",
            color = AccentBlue,
            style = MaterialTheme.typography.titleLarge,     // Bigger title
            modifier = Modifier.padding(bottom = 16.dp)
        )


// ⭐ LABEL: ACCOUNT TYPE (bold)
        Text(
            "Account Type",
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
        )
        Text(
            item?.accountType ?: "",
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(Modifier.height(16.dp))


// ⭐ LABEL: USERNAME (bold)
        Text(
            "Username / Email",
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
        )
        Text(
            item?.username ?: "",
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(Modifier.height(16.dp))


// ⭐ LABEL: PASSWORD (bold)
        Text(
            "Password",
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (visible) item?.password ?: "" else "********",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
            )

            IconButton(onClick = { visible = !visible }) {
                Icon(
                    imageVector = if (visible) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                    contentDescription = "Toggle Password"
                )
            }
        }



        Spacer(Modifier.height(20.dp))

        Row(Modifier.fillMaxWidth()) {

            Button(
                onClick = { onClose() },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(26.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = androidx.compose.ui.graphics.Color.Black,
                    contentColor = androidx.compose.ui.graphics.Color.White
                )
            ) {
                Text("Edit")
            }


            Spacer(Modifier.width(12.dp))

            Button(
                onClick = {
                    item?.let { viewModel.deletePassword(it) }
                    onClose()
                },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Danger),
                shape = RoundedCornerShape(26.dp)
            ) {
                Text("Delete")
            }
        }

        Spacer(Modifier.height(20.dp))
    }
}
