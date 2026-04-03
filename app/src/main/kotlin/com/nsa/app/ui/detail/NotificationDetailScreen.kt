package com.nsa.app.ui.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.nsa.app.data.repository.NotificationRepository
import com.nsa.app.util.TimeUtil

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationDetailScreen(
    navController: NavHostController,
    repository: NotificationRepository,
    notificationId: Long,
    viewModel: NotificationDetailViewModel = viewModel(
        factory = NotificationDetailViewModel.Factory(repository, notificationId)
    )
) {
    val notification by viewModel.notification.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Notification Detail") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        viewModel.deleteNotification()
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                    }
                }
            )
        }
    ) { padding ->
        notification?.let { n ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = n.appName,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = TimeUtil.formatRelativeTime(n.postTime),
                    style = MaterialTheme.typography.labelMedium
                )
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = n.title ?: "No Title",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = n.text ?: "No Content",
                    style = MaterialTheme.typography.bodyLarge
                )
                
                if (!n.bigText.isNullOrEmpty() && n.bigText != n.text) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "More Details:",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = n.bigText,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
                Divider()
                Spacer(modifier = Modifier.height(24.dp))

                DetailRow(label = "Package Name", value = n.packageName)
                DetailRow(label = "Category", value = n.category ?: "None")
                DetailRow(label = "Is Ongoing", value = n.isOngoing.toString())
                DetailRow(label = "Captured At", value = TimeUtil.formatRelativeTime(n.timestamp))
            }
        } ?: run {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(text = label, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary)
        Text(text = value, style = MaterialTheme.typography.bodyMedium)
    }
}
