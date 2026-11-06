package com.example.myapplicationmvvmsqlite.utils

import java.security.MessageDigest
import java.security.SecureRandom

object PasswordUtils {

    fun hash(password: String): String {
        val salt = ByteArray(16).also { SecureRandom().nextBytes(it) }
        return "${salt.toHex()}:${sha256(salt, password)}"
    }

    fun verify(password: String, stored: String): Boolean {
        val (saltHex, hash) = stored.split(":")
        return sha256(saltHex.hexToBytes(), password) == hash
    }

    private fun sha256(salt: ByteArray, password: String): String =
        MessageDigest.getInstance("SHA-256")
            .digest(salt + password.toByteArray())
            .toHex()

    private fun ByteArray.toHex() = joinToString("") { "%02x".format(it) }
    private fun String.hexToBytes() = chunked(2).map { it.toInt(16).toByte() }.toByteArray()
}