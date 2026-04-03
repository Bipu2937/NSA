package com.nsa.app.ui.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.nsa.app.R
import com.nsa.app.data.repository.NotificationRepository
import com.nsa.app.util.PermissionUtil

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavHostController,
    repository: NotificationRepository,
    viewModel: SettingsViewModel = viewModel(
        factory = SettingsViewModel.Factory(LocalContext.current, repository)
    )
) {
    val context = LocalContext.current
    val autoDeleteDays by viewModel.autoDeleteDays.collectAsState(initial = 0)
    val captureOngoing by viewModel.captureOngoing.collectAsState(initial = false)
    val isPermissionEnabled = PermissionUtil.isNotificationListenerEnabled(context)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.settings)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            SettingsSection(title = "Permissions") {
                SettingsToggle(
                    title = stringResource(R.string.grant_permission),
                    checked = isPermissionEnabled,
                    onCheckedChange = { PermissionUtil.openNotificationListenerSettings(context) }
                )
            }

            SettingsSection(title = "Capture Settings") {
                SettingsToggle(
                    title = stringResource(R.string.capture_ongoing_title),
                    summary = stringResource(R.string.capture_ongoing_summary),
                    checked = captureOngoing,
                    onCheckedChange = { viewModel.setCaptureOngoing(it) }
                )
            }

            SettingsSection(title = "Maintenance") {
                SettingsClickable(
                    title = stringResource(R.string.clear_all),
                    onClick = { viewModel.clearAllNotifications() }
                )
            }

            SettingsSection(title = "About") {
                Text(
                    text = stringResource(R.string.about_content),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun SettingsSection(title: String, content: @Composable ColumnScope.() -> Unit) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 8.dp)
        )
        content()
        Divider(modifier = Modifier.padding(horizontal = 16.dp))
    }
}

@Composable
fun SettingsToggle(
    title: String,
    summary: String? = null,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    ListItem(
        headlineContent = { Text(title) },
        supportingContent = summary?.let { { Text(it) } },
        trailingContent = {
            Switch(checked = checked, onCheckedChange = onCheckedChange)
        }
    )
}

@Composable
fun SettingsClickable(title: String, onClick: () -> Unit) {
    ListItem(
        headlineContent = { Text(title) },
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick)
    )
}
