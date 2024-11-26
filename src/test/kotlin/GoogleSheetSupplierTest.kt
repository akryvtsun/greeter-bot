import com.akryvtsun.GoogleSheetSupplier
import com.akryvtsun.Postcrosser
import org.assertj.core.api.Assertions.assertThat
import java.time.LocalDate
import kotlin.test.Test

class GoogleSheetSupplierTest {

    @Test
    fun `should read test data successfully`() {
        val postcrossers = GoogleSheetSupplier(
            spreadsheetId = "1tBRk9HR2zNU0x7NImiv9Fffhh03ZWfCeNLaRuRi1pqg",
            birthdayRange = "A2:A",
            nameRange = "C2:C",
            usernameRange = "B2:B"
        ).get()

        assertThat(postcrossers)
            .containsExactly(
                Postcrosser(LocalDate.of(1900, 10, 25), "Тетяна", "tantan"),
                Postcrosser(LocalDate.of(1975, 12, 4), "Андрій", "@akryvtsun"),
            )
    }
}