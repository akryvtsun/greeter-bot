package com.akryvtsun

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.services.sheets.v4.Sheets
import java.io.FileInputStream

fun main() {
    // Path to your service account key file
    val jsonKeyPath = "src/main/resources/ua-greeter-bot.json"

    // Authenticate using the service account
    val credential = GoogleCredential.fromStream(FileInputStream(jsonKeyPath))
        .createScoped(listOf("https://www.googleapis.com/auth/spreadsheets.readonly"))

    // Build the Sheets API client
    val sheetsService = Sheets.Builder(
        com.google.api.client.http.javanet.NetHttpTransport(),
        com.google.api.client.json.jackson2.JacksonFactory(),
        credential
    ).setApplicationName("Kotlin Google Sheets Example")
        .build()

    // Define the spreadsheet ID and range
    val spreadsheetId = "1RZzGebweJ5pGwZY8rLTIQzY2mHze4z44BmOJREq_vCs" // Replace with your spreadsheet ID
    val range = "Form Responses 1!A1:D10" // Adjust the range based on your sheet

    // Read data
    val response = sheetsService.spreadsheets().values()
        .get(spreadsheetId, range)
        .execute()

    val values = response.getValues()

    // Print the retrieved data
    if (values.isNullOrEmpty()) {
        println("No data found.")
    } else {
        for (row in values) {
            println(row) // Each row is a list of cell values
        }
    }
}