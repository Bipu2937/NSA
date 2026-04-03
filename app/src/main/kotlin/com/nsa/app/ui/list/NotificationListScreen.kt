package com.nsa.app.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.nsa.app.R
import com.nsa.app.data.model.AppInfo
import com.nsa.app.data.model.SavedNotification
import com.nsa.app.data.repository.NotificationRepository
import com.nsa.app.ui.navigation.Screen
import com.nsa.app.util.TimeUtil

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationListScreen(
    navController: NavHostController,
    repository: NotificationRepository,
    viewModel: NotificationListViewModel = viewModel(factory = NotificationListViewModel.Factory(repository))
) {
    val notifications by viewModel.notifications.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val distinctApps by viewModel.distinctApps.collectAsState()
    val selectedApp by viewModel.selectedApp.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.app_name)) },
                actions = {
                    IconButton(onClick = { navController.navigate(Screen.Settings.route) }) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.onSearchQueryChanged(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text(stringResource(R.string.search_notifications)) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                singleLine = true
            )

            AppFilterChips(
                apps = distinctApps,
                selectedApp = selectedApp,
                onAppSelected = { viewModel.onAppSelected(it) }
            )

            if (notifications.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                    Text(stringResource(R.string.no_notifications))
                }
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(notifications, key = { it.id }) { notification ->
                        NotificationItem(
                            notification = notification,
                            onClick = {
                                navController.navigate(Screen.Detail.createRoute(notification.id))
                            }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppFilterChips(
    apps: List<AppInfo>,
    selectedApp: AppInfo?,
    onAppSelected: (AppInfo?) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            FilterChip(
                selected = selectedApp == null,
                onClick = { onAppSelected(null) },
                label = { Text("All") }
            )
        }
        items(apps) { app ->
            FilterChip(
                selected = selectedApp == app,
                onClick = { onAppSelected(app) },
                label = { Text(app.appName) }
            )
        }
    }
}

@Composable
fun NotificationItem(
    notification: SavedNotification,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = notification.appName,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = TimeUtil.formatRelativeTime(notification.postTime),
                    style = MaterialTheme.typography.labelSmall
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = notification.title ?: "No Title",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = notification.text ?: "No Content",
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
