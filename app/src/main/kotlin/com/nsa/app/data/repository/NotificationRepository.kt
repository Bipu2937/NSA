package com.nsa.app.data.repository

import com.nsa.app.data.dao.NotificationDao
import com.nsa.app.data.model.AppInfo
import com.nsa.app.data.model.SavedNotification
import kotlinx.coroutines.flow.Flow

class NotificationRepository(private val notificationDao: NotificationDao) {
    val allNotifications: Flow<List<SavedNotification>> = notificationDao.getAll()
    val distinctApps: Flow<List<AppInfo>> = notificationDao.getDistinctApps()

    suspend fun insert(notification: SavedNotification) {
        notificationDao.insert(notification)
    }

    suspend fun getById(id: Long): SavedNotification? {
        return notificationDao.getById(id)
    }

    fun search(query: String): Flow<List<SavedNotification>> {
        return notificationDao.search(query)
    }

    fun getByPackage(packageName: String): Flow<List<SavedNotification>> {
        return notificationDao.getByPackage(packageName)
    }

    suspend fun deleteOlderThan(timestamp: Long) {
        notificationDao.deleteOlderThan(timestamp)
    }

    suspend fun deleteAll() {
        notificationDao.deleteAll()
    }

    suspend fun delete(notification: SavedNotification) {
        notificationDao.delete(notification)
    }
}
