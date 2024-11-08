package com.akryvtsun

import java.time.LocalDate


fun main() {
    val postcrossers = GoogleSheetSupplier(
        spreadsheetId = System.getenv("BOT_SPREADSHEET_ID"),
        birthdayRange = System.getenv("BOT_BIRTHDAY_RANGE"),
        nameRange = System.getenv("BOT_NAME_RANGE"),
        usernameRange = System.getenv("BOT_USERNAME_RANGE")
    ).get()

    println("Size: ${postcrossers.size}")
    println(postcrossers)

    val birthdays = postcrossers.filter { isToday(it.birthday) }
    println(birthdays)
}

fun isToday(date: LocalDate): Boolean {
    val today = LocalDate.now()
    return date.month == today.month && date.dayOfMonth == today.dayOfMonth
}