package com.example.passwordmanager.crypto


import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import kotlin.random.Random

class CryptoManager {
    // Demo key: 32 bytes (256-bit) - do NOT hardcode in production
    private val secretKeyString = "A1b2C3d4E5f6G7h8I9j0K1l2M3n4O5p6"
    private val algorithm = "AES/CBC/PKCS5Padding"
    private val rawKey: SecretKey = SecretKeySpec(secretKeyString.toByteArray(), "AES")

    fun encrypt(plainText: String): String {
        if (plainText.isEmpty()) return ""
        val cipher = Cipher.getInstance(algorithm)
        val iv = ByteArray(16).also { Random.nextBytes(it) }
        val ivSpec = IvParameterSpec(iv)
        cipher.init(Cipher.ENCRYPT_MODE, rawKey, ivSpec)
        val cipherBytes = cipher.doFinal(plainText.toByteArray(Charsets.UTF_8))
        val combined = iv + cipherBytes
        return Base64.encodeToString(combined, Base64.NO_WRAP)
    }

    fun decrypt(cipherText: String): String {
        if (cipherText.isEmpty()) return ""
        val combined = Base64.decode(cipherText, Base64.NO_WRAP)
        val iv = combined.copyOfRange(0, 16)
        val cipherBytes = combined.copyOfRange(16, combined.size)
        val cipher = Cipher.getInstance(algorithm)
        cipher.init(Cipher.DECRYPT_MODE, rawKey, IvParameterSpec(iv))
        val plainBytes = cipher.doFinal(cipherBytes)
        return String(plainBytes, Charsets.UTF_8)
    }
}
