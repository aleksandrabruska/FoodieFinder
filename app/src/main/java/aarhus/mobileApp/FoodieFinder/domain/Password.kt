package aarhus.mobileApp.FoodieFinder.domain

data class Password(val password: String) {
    init {
        if(!validateRegex(password)) {
            throw IllegalArgumentException("ILLEGAL PASSWORD")
        }
        if (!validateLength(password)) {
            throw IllegalArgumentException("Password is too short")
        }
    }

    companion object {
        private val regex = Regex("^[0-9A-Za-z!@#$%^&*()\\-_=+\\[\\]{};:'\",.<>?/|]+$")

        fun validateRegex(pass: String): Boolean {
            return regex.matches(pass)
        }

        fun validateLength(pass: String): Boolean {
            return pass.length > 6
        }
    }
}
