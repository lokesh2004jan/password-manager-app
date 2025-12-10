package com.example.passwordmanager.data.repository

import com.example.passwordmanager.crypto.CryptoManager
import com.example.passwordmanager.data.dao.PasswordDao
import com.example.passwordmanager.data.entity.PasswordEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

data class PasswordItem(
    val id: Int = 0,
    val accountType: String,
    val username: String,
    val password: String
)

class PasswordRepository(
    private val dao: PasswordDao,
    private val cryptoManager: CryptoManager
) {
    fun getAllPasswords(): Flow<List<PasswordItem>> =
        dao.getAllPasswords().map { list ->
            list.map { e ->
                PasswordItem(
                    id = e.id,
                    accountType = e.accountType,
                    username = e.username,
                    password = cryptoManager.decrypt(e.encryptedPassword)
                )
            }
        }

    suspend fun upsertPassword(item: PasswordItem) {
        val encrypted = cryptoManager.encrypt(item.password)
        dao.upsertPassword(PasswordEntity(item.id, item.accountType, item.username, encrypted))
    }

    suspend fun deletePassword(item: PasswordItem) {
        val encrypted = cryptoManager.encrypt(item.password)
        dao.deletePassword(PasswordEntity(item.id, item.accountType, item.username, encrypted))
    }

    suspend fun getPasswordById(id: Int): PasswordItem? {
        val e = dao.getPasswordById(id) ?: return null
        return PasswordItem(e.id, e.accountType, e.username, cryptoManager.decrypt(e.encryptedPassword))
    }
}
