package com.nsa.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notifications")
data class SavedNotification(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val appName: String,
    val packageName: String,
    val title: String?,
    val text: String?,
    val bigText: String?,
    val timestamp: Long,
    val postTime: Long,
    val isOngoing: Boolean,
    val category: String?
)
