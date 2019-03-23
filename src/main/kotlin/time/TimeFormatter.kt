package time

class TimeFormatter {

    companion object {
        const val second = 1
        const val minuteInSeconds = 60 * second
        const val hourInSeconds = minuteInSeconds * 60
        const val dayInSeconds = hourInSeconds * 24
        const val yearInSeconds = dayInSeconds * 365

        val periods = listOf(
                Pair("year", yearInSeconds),
                Pair("day", dayInSeconds),
                Pair("hour", hourInSeconds),
                Pair("minute", minuteInSeconds),
                Pair("second", second)
        )
    }


    fun timeFormat(input: Int): String {
        if (input == 0) return "none"

        val resultsToFormat = findTimeUnitsAndMultiples(input)

        val unitsAndCountsFormatted = resultsToFormat
                .map { (unit, unitCount) ->
                    formatUnits(unit, unitCount)
                }
        val timeInWords = toStringWithCommasAndAnd(unitsAndCountsFormatted)

        return timeInWords
    }

    private fun findTimeUnitsAndMultiples(input: Int): List<Pair<String, Int>> {
        var remainingSeconds = input
        val calculatedPeriods = mutableListOf<Pair<String, Int>>()
        periods.forEach { (periodName, periodSeconds) ->
            calculatedPeriods.add(periodName to (remainingSeconds / periodSeconds))
            remainingSeconds %= periodSeconds
        }
        return calculatedPeriods.filter { it.second != 0 }
    }

    fun formatUnits(unitkey: String, unitvalue: Int): String = "$unitvalue $unitkey" + plural(unitvalue)

    private fun toStringWithCommasAndAnd(unitsAndCountsFormatted: List<String>): String = unitsAndCountsFormatted
            .zip((unitsAndCountsFormatted.size - 1) downTo 0)
            .map { (formattedUnit, countUnitsToDisplay) ->
                formattedUnit + formatAndOrComma(countUnitsToDisplay)
            }
            .joinToString("")

    private fun plural(unit: Int): String = if (unit == 1) "" else "s"

    private fun formatAndOrComma(count: Int): String = when (count) {
        0 -> ""
        1 -> " and "
        else -> ", "
    }

}
