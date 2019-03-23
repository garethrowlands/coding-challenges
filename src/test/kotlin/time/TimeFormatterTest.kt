package time

import org.junit.Test
import kotlin.test.assertEquals

class TimeFormatterTest {

    val underTest = TimeFormatter()

    @Test
    fun `should return none for an input zero`() {
            assertEquals("none", underTest.timeFormat(0))
    }


    @Test
    fun `should return formatted time for a valid input`() {
        assertEquals("1 second", underTest.timeFormat(1))
        assertEquals("1 minute and 2 seconds", underTest.timeFormat(62))
        assertEquals("1 hour, 1 minute and 2 seconds", underTest.timeFormat(3662))
        assertEquals("3 years and 24 minutes", underTest.timeFormat(94609440))
        assertEquals("3 days, 4 hours and 1 minute", underTest.timeFormat(273660))
    }

}
