package time

class TimeFormatter {

    companion object {
        const val second = 1
        const val minuteInSeconds = 60 * second
        const val hourInSeconds = minuteInSeconds * 60
        const val dayInSeconds = hourInSeconds * 24
        const val yearInSeconds = dayInSeconds * 365

        var remainingSeconds = 0
        var countUnitsToDisplay = 0
    }


    fun timeFormat(input: Int): String {
        if (input == 0) return "none"

        fun resetVariables(input: Int) {
            remainingSeconds = input
            countUnitsToDisplay = 0
        }

        resetVariables(input)

        val resultsToFormat = formatResults()

        countUnitsToDisplay = resultsToFormat.count { it.value != 0 }
        if (countUnitsToDisplay == 0) return "none"

        val timeInWords = resultsToFormat
                .map { formatUnits(it.key, it.value) }
                .joinToString("")

        return timeInWords
    }


    private fun formatResults(): Map<String, Int> {
        return mapOf(
                Pair("year", calculateUnit(yearInSeconds)),
                Pair("day", calculateUnit(dayInSeconds)),
                Pair("hour", calculateUnit(hourInSeconds)),
                Pair("minute", calculateUnit(minuteInSeconds)),
                Pair("second", calculateUnit(second))
        )
    }

    private fun formatUnits(unitkey: String, unitvalue: Int): String {
        if (unitvalue == 0) return ""
        countUnitsToDisplay -= 1
        return "$unitvalue $unitkey" + plural(unitvalue) + formatAndOrComma(countUnitsToDisplay)
    }

    private fun plural(unit: Int): String {
        if (unit == 1) return ""
        return "s"
    }

    private fun formatAndOrComma(count: Int): String {
        return when {
            count == 0 -> ""
            count == 1 -> " and "
            else -> ", "
        }
    }

    private fun calculateUnit(unitInSeconds: Int): Int {
        val calculatedUnits = calculateField(remainingSeconds, unitInSeconds)
        remainingSeconds = calculateRemainder(remainingSeconds, unitInSeconds)
        return calculatedUnits
    }

    private fun calculateField(input: Int, divisor: Int): Int {
        return (input / divisor)
    }

    private fun calculateRemainder(input: Int, divisor: Int): Int {
        return (input % divisor)
    }

}
