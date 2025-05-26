package vn.gmi.workzen.core.extensions

fun standardizationNumberPhone(phone: String): String {
    return if (phone.startsWith("0")) {
        phone.replaceFirst("0", "+84")
    } else {
        phone
    }
}