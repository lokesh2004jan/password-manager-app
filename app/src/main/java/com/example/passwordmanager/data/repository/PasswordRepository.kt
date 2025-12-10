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
                try {
                    PasswordItem(
                        id = e.id,
                        accountType = e.accountType,
                        username = e.username,
                        password = cryptoManager.decrypt(e.encryptedPassword)
                    )
                } catch (ex: Exception) {
                    PasswordItem(
                        id = e.id,
                        accountType = e.accountType,
                        username = e.username,
                        password = "ERROR"
                    )
                }
            }
        }

    suspend fun upsertPassword(item: PasswordItem): Result<Unit> {
        return try {
            val encrypted = cryptoManager.encrypt(item.password)
            dao.upsertPassword(
                PasswordEntity(
                    item.id,
                    item.accountType,
                    item.username,
                    encrypted
                )
            )
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deletePassword(item: PasswordItem): Result<Unit> {
        return try {
            val encrypted = cryptoManager.encrypt(item.password)
            dao.deletePassword(
                PasswordEntity(
                    item.id,
                    item.accountType,
                    item.username,
                    encrypted
                )
            )
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getPasswordById(id: Int): Result<PasswordItem> {
        return try {
            val e = dao.getPasswordById(id) ?: return Result.failure(Exception("Not found"))
            val decrypted = cryptoManager.decrypt(e.encryptedPassword)
            Result.success(
                PasswordItem(
                    e.id,
                    e.accountType,
                    e.username,
                    decrypted
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
