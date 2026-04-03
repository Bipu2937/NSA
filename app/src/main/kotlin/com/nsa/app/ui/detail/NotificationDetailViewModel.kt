package com.nsa.app.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.nsa.app.data.model.SavedNotification
import com.nsa.app.data.repository.NotificationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NotificationDetailViewModel(
    private val repository: NotificationRepository,
    private val notificationId: Long
) : ViewModel() {

    private val _notification = MutableStateFlow<SavedNotification?>(null)
    val notification: StateFlow<SavedNotification?> = _notification.asStateFlow()

    init {
        loadNotification()
    }

    private fun loadNotification() {
        viewModelScope.launch {
            _notification.value = repository.getById(notificationId)
        }
    }

    fun deleteNotification() {
        viewModelScope.launch {
            _notification.value?.let {
                repository.delete(it)
            }
        }
    }

    class Factory(
        private val repository: NotificationRepository,
        private val notificationId: Long
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NotificationDetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return NotificationDetailViewModel(repository, notificationId) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
