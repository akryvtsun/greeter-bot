package com.akryvtsun

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.services.sheets.v4.Sheets
import com.google.common.base.Supplier
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.time.LocalDate
import java.time.format.DateTimeFormatterBuilder
import java.time.format.SignStyle
import java.time.temporal.ChronoField

typealias PostcrossersProvider = Supplier<List<Postcrosser>>

class GoogleSheetSupplier(
    val spreadsheetId: String,
    val birthdayRange: String,
    val nameRange: String,
    val usernameRange: String
): PostcrossersProvider {

    override fun get(): List<Postcrosser> {
        // Authenticate using the service account
        val textCredential = System.getProperty("GOOGLE_SHEETS_CRED")
        val credential = GoogleCredential.fromStream(stringToInputStream(textCredential))
            .createScoped(listOf("https://www.googleapis.com/auth/spreadsheets.readonly"))

        // Build the Sheets API client
        val sheetsService = Sheets.Builder(
            com.google.api.client.http.javanet.NetHttpTransport(),
            com.google.api.client.json.jackson2.JacksonFactory(),
            credential
        )
            .setApplicationName("Kotlin Google Sheets Example")
            .build()

        val response = sheetsService.spreadsheets().values()
            .batchGet(spreadsheetId)
            .setRanges(listOf(birthdayRange, nameRange, usernameRange))
            .execute()

        val ranges = response.valueRanges

        val dates = ranges[0].getValues().flatten()
        val names = ranges[1].getValues().flatten()
        val usernames = ranges[2].getValues().flatten()

        val formatter = DateTimeFormatterBuilder()
            .appendValue(ChronoField.DAY_OF_MONTH, 1, 2, SignStyle.NORMAL) // Day
            .appendLiteral('.') // Literal '.'
            .appendValue(ChronoField.MONTH_OF_YEAR, 1, 2, SignStyle.NORMAL) // Month
            .optionalStart() // Optional part begins
            .appendLiteral('.') // Literal '.' before year
            .appendValue(ChronoField.YEAR, 4) // Year
            .optionalEnd() // Optional part ends
            .parseDefaulting(ChronoField.YEAR, 1900) // Default year to 1900
            .toFormatter()

        return dates.indices
            .map { i ->
                val birthday = LocalDate.parse(dates[i] as String, formatter)
                val name = names[i] as String
                val username = usernames[i] as String
                Postcrosser(birthday, name, username)
            }
            .toList()
    }

    private fun stringToInputStream(input: String): InputStream {
        return ByteArrayInputStream(input.toByteArray(Charsets.UTF_8))
    }
}