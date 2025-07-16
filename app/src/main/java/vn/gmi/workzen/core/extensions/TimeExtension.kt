package vn.gmi.workzen.core.extensions

import io.realm.kotlin.types.RealmInstant
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun Instant.toRealmInstant(): RealmInstant {
    return RealmInstant.from(this.epochSecond, this.nano)
}
fun Instant.toVietNamTime(): ZonedDateTime {
    return this.atZone(ZoneId.of("Asia/Ho_Chi_Minh"))
}

fun Instant.toVietNamTimeString(): String {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
        .withZone(ZoneId.of("Asia/Ho_Chi_Minh"))
    return formatter.format(this)
}


fun RealmInstant.toInstant(): Instant {
    return Instant.ofEpochSecond(this.epochSeconds, this.nanosecondsOfSecond.toLong())
}

fun RealmInstant.toHourMinute(): String{
    val instant = Instant.ofEpochSecond(this.epochSeconds, this.nanosecondsOfSecond.toLong())
    val localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
    val formatter = DateTimeFormatter.ofPattern("HH:mm")

    return localDateTime.format(formatter)
}
