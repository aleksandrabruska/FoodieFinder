package aarhus.mobileApp.FoodieFinder.domain

import android.util.Log

data class Date(val dateString: String) {

    init {
        if(!validate(dateString)) {
            throw IllegalArgumentException("ILLEGAL DATE")
        }
    }

    companion object {
        private val regex = Regex("[0-9]{2}\\.[0-9]{2}$")

        fun validate(dat: String): Boolean {
            if (!regex.matches(dat))
                return false

            val day: Int = dat.substring(0, 2).toInt()
            val month: Int = dat.substring(3, 5).toInt()

            if(month == 0 || month > 12 || day == 0)
                return false

            val md: Array<Int> = arrayOf(31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)

            if(md[month - 1] <   day) {
                return false
            }

            return true
        }
    }
}
