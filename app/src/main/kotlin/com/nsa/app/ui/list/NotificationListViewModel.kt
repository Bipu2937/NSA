package com.nsa.app.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.nsa.app.data.model.AppInfo
import com.nsa.app.data.model.SavedNotification
import com.nsa.app.data.repository.NotificationRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NotificationListViewModel(private val repository: NotificationRepository) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedApp = MutableStateFlow<AppInfo?>(null)
    val selectedApp: StateFlow<AppInfo?> = _selectedApp.asStateFlow()

    val distinctApps: StateFlow<List<AppInfo>> = repository.distinctApps
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val notifications: StateFlow<List<SavedNotification>> = combine(
        repository.allNotifications,
        _searchQuery,
        _selectedApp
    ) { all, query, selectedApp ->
        all.filter { notification ->
            val matchesQuery = if (query.isEmpty()) true else {
                notification.title?.contains(query, ignoreCase = true) == true ||
                notification.text?.contains(query, ignoreCase = true) == true ||
                notification.appName.contains(query, ignoreCase = true)
            }
            val matchesApp = if (selectedApp == null) true else {
                notification.packageName == selectedApp.packageName
            }
            matchesQuery && matchesApp
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun onAppSelected(app: AppInfo?) {
        _selectedApp.value = app
    }

    fun deleteNotification(notification: SavedNotification) {
        viewModelScope.launch {
            repository.delete(notification)
        }
    }

    class Factory(private val repository: NotificationRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NotificationListViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return NotificationListViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
