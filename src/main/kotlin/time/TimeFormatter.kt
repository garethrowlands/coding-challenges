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

        fun formatUnits(unitkey: String, unitvalue: Int, countUnitsToDisplay: Int): String {
            return "$unitvalue $unitkey" + plural(unitvalue) + formatAndOrComma(countUnitsToDisplay)
        }

        val totalCountOfUnitsToDisplay = resultsToFormat.size
        val allCountUnitsToDisplayValues = (totalCountOfUnitsToDisplay-1) downTo 0
        val unitsAndCounts = resultsToFormat
                .toList()
                .zip(allCountUnitsToDisplayValues)
        val timeInWords = unitsAndCounts
                .map { (unit,countUnitsToDisplay)->
                    formatUnits(unit.first, unit.second, countUnitsToDisplay)
                }
                .joinToString("")

        return timeInWords
    }

    class TimeCalculator(input: Int) {

        var remainingSeconds = input

        fun formatResults(): List<Pair<String, Int>> {
            return listOf(
                    Pair("year", calculateUnit(yearInSeconds)),
                    Pair("day", calculateUnit(dayInSeconds)),
                    Pair("hour", calculateUnit(hourInSeconds)),
                    Pair("minute", calculateUnit(minuteInSeconds)),
                    Pair("second", calculateUnit(second))
            )
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
