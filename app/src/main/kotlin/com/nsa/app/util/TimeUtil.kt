package com.nsa.app.util

import android.text.format.DateUtils
import java.util.*

object TimeUtil {
    fun formatRelativeTime(timestamp: Long): String {
        val now = System.currentTimeMillis()
        return DateUtils.getRelativeTimeSpanString(
            timestamp,
            now,
            DateUtils.MINUTE_IN_MILLIS,
            DateUtils.FORMAT_ABBREV_RELATIVE
        ).toString()
    }
}
