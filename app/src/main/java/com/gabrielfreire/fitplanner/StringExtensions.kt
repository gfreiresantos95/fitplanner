package com.gabrielfreire.fitplanner

fun String.isEmailValid(): Boolean {
    val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
    return emailRegex.toRegex().matches(this)
}

fun String.isPhoneNumberValid(): Boolean {
    val phoneRegex = "^\\d{10}$"
    return phoneRegex.toRegex().matches(this)
}

fun String.isPasswordValid(): Boolean {
    val passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$"
    return passwordRegex.toRegex().matches(this)
}