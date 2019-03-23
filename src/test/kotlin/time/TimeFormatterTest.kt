package time

import org.junit.Test
import kotlin.test.assertEquals

class TimeFormatterTest {

    @Test
    fun `should return none for an input zero`() {
            assertEquals("none", timeFormat(0))
    }


    @Test
    fun `should return formatted time for a valid input`() {
        assertEquals("1 second", timeFormat(1))
        assertEquals("1 minute and 2 seconds", timeFormat(62))
        assertEquals("1 hour, 1 minute and 2 seconds", timeFormat(3662))
        assertEquals("3 years and 24 minutes", timeFormat(94609440))
        assertEquals("3 days, 4 hours and 1 minute", timeFormat(273660))
    }

}
