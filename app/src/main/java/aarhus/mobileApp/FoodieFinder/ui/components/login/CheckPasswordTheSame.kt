package aarhus.mobileApp.FoodieFinder.ui.components.login

fun checkPassword(password: String, repeat: String) : String? {
    if (password == repeat)
        return null
    return "Password must match"

}