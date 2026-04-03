package com.nsa.app.data.dao

import androidx.room.*
import com.nsa.app.data.model.AppInfo
import com.nsa.app.data.model.SavedNotification
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(notification: SavedNotification)

    @Query("SELECT * FROM notifications ORDER BY postTime DESC")
    fun getAll(): Flow<List<SavedNotification>>

    @Query("SELECT * FROM notifications WHERE id = :id")
    suspend fun getById(id: Long): SavedNotification?

    @Query("SELECT * FROM notifications WHERE (title LIKE '%' || :query || '%' OR text LIKE '%' || :query || '%') ORDER BY postTime DESC")
    fun search(query: String): Flow<List<SavedNotification>>

    @Query("SELECT * FROM notifications WHERE packageName = :packageName ORDER BY postTime DESC")
    fun getByPackage(packageName: String): Flow<List<SavedNotification>>

    @Query("SELECT DISTINCT packageName, appName FROM notifications ORDER BY appName ASC")
    fun getDistinctApps(): Flow<List<AppInfo>>

    @Query("DELETE FROM notifications WHERE timestamp < :timestamp")
    suspend fun deleteOlderThan(timestamp: Long)

    @Query("DELETE FROM notifications")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(notification: SavedNotification)
}
