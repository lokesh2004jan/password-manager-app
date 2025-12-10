package com.example.passwordmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.passwordmanager.crypto.CryptoManager
import com.example.passwordmanager.data.db.AppDatabase
import com.example.passwordmanager.data.repository.PasswordRepository
import com.example.passwordmanager.ui.navigation.AppNavHost
import com.example.passwordmanager.ui.theme.PasswordManagerTheme
import com.example.passwordmanager.ui.viewmodel.PasswordViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = AppDatabase.getInstance(this)
        val repo = PasswordRepository(db.passwordDao(), CryptoManager())

        val factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return PasswordViewModel(repo) as T
            }
        }

        setContent {
            val vm: PasswordViewModel = viewModel(factory = factory)
            PasswordManagerTheme {
                AppNavHost(vm = vm)
            }
        }
    }
}
