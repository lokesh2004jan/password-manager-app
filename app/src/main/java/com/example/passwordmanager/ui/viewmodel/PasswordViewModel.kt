package com.example.passwordmanager.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passwordmanager.data.repository.PasswordItem
import com.example.passwordmanager.data.repository.PasswordRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class PasswordUiState(
    val passwords: List<PasswordItem> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)

class PasswordViewModel(private val repository: PasswordRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(PasswordUiState())
    val uiState: StateFlow<PasswordUiState> = _uiState.asStateFlow()

    init {
        observe()
    }

    private fun observe() {
        repository.getAllPasswords()
            .onStart { _uiState.update { it.copy(isLoading = true) } }
            .catch { e -> _uiState.update { it.copy(isLoading = false, error = e.message) } }
            .onEach { list ->
                _uiState.update {
                    it.copy(
                        passwords = list,
                        isLoading = false,
                        error = null
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    fun savePassword(item: PasswordItem, onDone: () -> Unit = {}) {
        viewModelScope.launch {
            val result = repository.upsertPassword(item)

            result.onSuccess {
                onDone()
            }

            result.onFailure { e ->
                _uiState.update {
                    it.copy(error = e.message ?: "Something went wrong")
                }
            }
        }
    }

    fun deletePassword(item: PasswordItem) {
        viewModelScope.launch {
            val result = repository.deletePassword(item)

            result.onFailure { e ->
                _uiState.update {
                    it.copy(error = e.message ?: "Failed to delete password")
                }
            }
        }
    }

    suspend fun getPasswordById(id: Int): PasswordItem? {
        val result = repository.getPasswordById(id)
        result.onFailure { e ->
            _uiState.update { it.copy(error = e.message ?: "Unable to load details") }
        }
        return result.getOrNull()
    }
}