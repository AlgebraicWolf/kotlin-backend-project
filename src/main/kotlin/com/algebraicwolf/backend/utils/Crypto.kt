package com.algebraicwolf.backend.utils

import java.security.MessageDigest
import java.security.SecureRandom

fun generateSalt(): String {
    val random = SecureRandom()
    val salt = ByteArray(64)
    random.nextBytes(salt)

    return salt.toString()
}

@OptIn(ExperimentalStdlibApi::class)
fun hashPassword(pass: String, salt: String): String {
    val md = MessageDigest.getInstance("SHA-256")
    return md.digest((pass + salt).toByteArray()).toHexString()
}