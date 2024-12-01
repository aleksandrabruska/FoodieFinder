package aarhus.mobileApp.FoodieFinder.ui.components.login

fun checkPassword(password: String, repeat: String) {
    if (password != repeat)
        throw IllegalArgumentException("passwords must match")

}