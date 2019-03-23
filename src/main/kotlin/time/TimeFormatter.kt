package time

class TimeFormatter {

    companion object {
        const val second = 1
        const val minuteInSeconds = 60 * second
        const val hourInSeconds = minuteInSeconds * 60
        const val dayInSeconds = hourInSeconds * 24
        const val yearInSeconds = dayInSeconds * 365
    }


    fun timeFormat(input: Int): String {
        if (input == 0) return "none"

        val resultsToFormat = TimeCalculator(input).formatResults()
                .filter { it.second!=0 }

        val unitsAndCountsFormatted = resultsToFormat
                .map { (unit, unitCount) ->
                    formatUnits(unit, unitCount)
                }
        val timeInWords = toStringWithCommasAndAnd(unitsAndCountsFormatted)

        return timeInWords
    }

    fun formatUnits(unitkey: String, unitvalue: Int): String {
        return "$unitvalue $unitkey" + plural(unitvalue)
    }

    private fun toStringWithCommasAndAnd(unitsAndCountsFormatted: List<String>): String {
        val totalCountOfUnitsToDisplay = unitsAndCountsFormatted.size
        val allCountUnitsToDisplayValues = (totalCountOfUnitsToDisplay - 1) downTo 0
        val timeInWords = unitsAndCountsFormatted
                .zip(allCountUnitsToDisplayValues)
                .map { (formattedUnit, countUnitsToDisplay) ->
                    formattedUnit + formatAndOrComma(countUnitsToDisplay)
                }
                .joinToString("")
        return timeInWords
    }

    class TimeCalculator(val input: Int) {

        val periods = listOf(
                Pair("year", yearInSeconds),
                Pair("day", dayInSeconds),
                Pair("hour", hourInSeconds),
                Pair("minute", minuteInSeconds),
                Pair("second", second)
        )

        fun formatResults(): List<Pair<String, Int>> {
            var remainingSeconds = input
            val calculatedPeriods = mutableListOf<Pair<String, Int>>()
            periods.forEach { (periodName,periodSeconds)->
                calculatedPeriods.add(periodName to calculateField(remainingSeconds, periodSeconds))
                remainingSeconds = calculateRemainder(remainingSeconds, periodSeconds)
            }
            return calculatedPeriods.toList()
        }

        private fun calculateField(input: Int, divisor: Int): Int {
            return (input / divisor)
        }

        private fun calculateRemainder(input: Int, divisor: Int): Int {
            return (input % divisor)
        }

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

}
