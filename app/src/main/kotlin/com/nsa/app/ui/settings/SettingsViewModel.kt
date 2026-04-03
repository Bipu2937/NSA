package com.nsa.app.ui.settings

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.nsa.app.data.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

private val Context.dataStore by preferencesDataStore(name = "settings")

class SettingsViewModel(
    private val context: Context,
    private val repository: NotificationRepository
) : ViewModel() {

    private val AUTO_DELETE_DAYS = intPreferencesKey("auto_delete_days")
    private val CAPTURE_ONGOING = booleanPreferencesKey("capture_ongoing")

    val autoDeleteDays: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[AUTO_DELETE_DAYS] ?: 0 // 0 means disabled
    }

    val captureOngoing: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[CAPTURE_ONGOING] ?: false
    }

    fun setAutoDeleteDays(days: Int) {
        viewModelScope.launch {
            context.dataStore.edit { preferences ->
                preferences[AUTO_DELETE_DAYS] = days
            }
        }
    }

    fun setCaptureOngoing(enabled: Boolean) {
        viewModelScope.launch {
            context.dataStore.edit { preferences ->
                preferences[CAPTURE_ONGOING] = enabled
            }
        }
    }

    fun clearAllNotifications() {
        viewModelScope.launch {
            repository.deleteAll()
        }
    }

    class Factory(
        private val context: Context,
        private val repository: NotificationRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return SettingsViewModel(context, repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
