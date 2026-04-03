package com.nsa.app.service

import android.app.Notification
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import com.nsa.app.NSAApplication
import com.nsa.app.data.model.SavedNotification
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class NotificationListener : NotificationListenerService() {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        if (sbn.packageName == packageName) return

        val extras = sbn.notification.extras
        val title = extras.getCharSequence(Notification.EXTRA_TITLE)?.toString()
        val text = extras.getCharSequence(Notification.EXTRA_TEXT)?.toString()
        val bigText = extras.getCharSequence(Notification.EXTRA_BIG_TEXT)?.toString()
        
        // Try to get app name
        val pm = packageManager
        val appName = try {
            pm.getApplicationLabel(pm.getApplicationInfo(sbn.packageName, 0)).toString()
        } catch (e: Exception) {
            sbn.packageName
        }

        val notification = SavedNotification(
            appName = appName,
            packageName = sbn.packageName,
            title = title,
            text = text,
            bigText = bigText,
            timestamp = System.currentTimeMillis(),
            postTime = sbn.postTime,
            isOngoing = sbn.isOngoing,
            category = sbn.notification.category
        )

        scope.launch {
            (application as NSAApplication).repository.insert(notification)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }
}
