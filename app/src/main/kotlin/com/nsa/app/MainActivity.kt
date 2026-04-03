package com.nsa.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.nsa.app.ui.navigation.NavGraph
import com.nsa.app.ui.theme.NSATheme
import com.nsa.app.util.PermissionUtil

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Simple permission check on launch
        if (!PermissionUtil.isNotificationListenerEnabled(this)) {
            PermissionUtil.openNotificationListenerSettings(this)
        }

        val repository = (application as NSAApplication).repository

        setContent {
            NSATheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavGraph(
                        navController = navController,
                        repository = repository
                    )
                }
            }
        }
    }
}
