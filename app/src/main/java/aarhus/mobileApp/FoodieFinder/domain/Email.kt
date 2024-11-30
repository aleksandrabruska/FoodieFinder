package aarhus.mobileApp.FoodieFinder.domain


data class Email(val email: String) {

    init {
        if(!validate(email)) {
            throw IllegalArgumentException("ILLEGAL EMAIL")
        }
    }

    companion object {
        private val regex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")

        fun validate(em: String): Boolean {
            return regex.matches(em)
        }
    }
}
