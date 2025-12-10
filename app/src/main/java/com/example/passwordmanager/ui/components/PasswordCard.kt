package com.example.passwordmanager.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.passwordmanager.data.repository.PasswordItem
@Composable
fun PasswordCard(
    item: PasswordItem,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(30.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .height(78.dp)                    // card height
            .clickable { onClick() }
            .padding(vertical = 6.dp)         // spacing between cards
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize()                // ⭐ fill height to allow vertical centering
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,   // ⭐ this centers text vertically
            horizontalArrangement = Arrangement.Start
        ) {

            // Account name (big & bold)
            Text(
                text = item.accountType,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(end = 10.dp)
            )

            // masked password
            Text(
                text = "********",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.weight(1f))

            // Arrow icon
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "View",
                tint = Color.Gray,
                modifier = Modifier
                    .size(30.dp)
                    .clickable { onClick() }
            )
        }
    }
}
