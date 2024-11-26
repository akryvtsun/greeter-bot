import com.akryvtsun.isToday
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate

class MainKtTest {

    @Test
    fun `should be equal to todays date`() {
        val today = LocalDate.now()
        assertThat(isToday(today)).isTrue()
    }

    @Test
    fun `shouldn't be equal to todays date`() {
        val tomorrow = LocalDate.now().plusDays(1)
        assertThat(isToday(tomorrow)).isFalse()
    }
}