package vn.gmi.workzen.core.extensions

import io.realm.kotlin.types.RealmInstant
import java.time.Instant

fun Instant.toRealmInstant(): RealmInstant {
    return RealmInstant.from(this.epochSecond, this.nano)
}


fun RealmInstant.toInstant(): Instant {
    return Instant.ofEpochSecond(this.epochSeconds, this.nanosecondsOfSecond.toLong())
}