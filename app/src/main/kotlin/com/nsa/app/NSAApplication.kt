package com.nsa.app

import android.app.Application
import com.nsa.app.data.database.NotificationDatabase
import com.nsa.app.data.repository.NotificationRepository

class NSAApplication : Application() {
    val database by lazy { NotificationDatabase.getDatabase(this) }
    val repository by lazy { NotificationRepository(database.notificationDao()) }
}
