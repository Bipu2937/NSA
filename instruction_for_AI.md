Context
Build a complete Android app from scratch that captures all device notifications via NotificationListenerService, persists them in a Room database, and provides a Jetpack Compose UI to browse, search, and manage saved notifications. The repository is empty — every file must be created.
Environment

Available: Gradle 8.14.3, Java 21 (OpenJDK)
Missing: Android SDK — must be installed first
Branch: claude/android-notification-saver-SyVEv

Tech Stack

Kotlin + Jetpack Compose + Material 3
Room (via KSP) for local persistence
MVVM with Repository pattern, manual DI
Kotlin Coroutines + Flow for async/reactive data
DataStore for user preferences
Min SDK 26 / Target SDK 34


Implementation Plan
Phase 1: Environment Setup

Download Android SDK command-line tools and install SDK platform 34 + build-tools
Create local.properties with sdk.dir path

Phase 2: Project Skeleton (~8 files)
Create in order:

.gitignore — standard Android ignores
gradle.properties — JVM args, AndroidX flags
gradle/wrapper/gradle-wrapper.properties — point to Gradle 8.4 distribution
gradlew + gradlew.bat — wrapper scripts (generate via gradle wrapper)
settings.gradle.kts — plugin repos, include(":app")
build.gradle.kts (root) — AGP 8.2.0 + Kotlin 1.9.20 plugins (apply false)
app/build.gradle.kts — android config, all dependencies (Compose BOM 2023.10.01, Room 2.6.0, Navigation 2.7.5, KSP 1.9.20-1.0.14)

Verify: ./gradlew tasks succeeds
Phase 3: Android Resources & Manifest (~3 files)

app/src/main/AndroidManifest.xml

MainActivity (launcher), NotificationListener service with BIND_NOTIFICATION_LISTENER_SERVICE
NSAApplication class reference


app/src/main/res/values/strings.xml — app name + UI strings
app/src/main/res/values/themes.xml — Material3.DayNight.NoActionBar

Phase 4: Data Layer (~5 files)
Base package: com/nsa/app/

data/model/SavedNotification.kt — Room @Entity with id, appName, packageName, title, text, bigText, timestamp, postTime, isOngoing, category
data/model/AppInfo.kt — simple data class (packageName, appName)
data/dao/NotificationDao.kt — @Dao with: insert, getAll (Flow), getById, search, getByPackage, getDistinctApps, deleteOlderThan, deleteAll
data/database/NotificationDatabase.kt — @Database singleton with Room.databaseBuilder
data/repository/NotificationRepository.kt — wraps DAO, exposes Flows

Phase 5: App & Service Layer (~2 files)

NSAApplication.kt — extends Application, lazy-inits database + repository (manual DI)
service/NotificationListener.kt — extends NotificationListenerService

onNotificationPosted: extract title/text/bigText/packageName/appName/postTime/category, save via repository on IO dispatcher
Filters out own package notifications
Uses CoroutineScope with SupervisorJob, cancelled in onDestroy



Phase 6: Utilities (~2 files)

util/PermissionUtil.kt — isNotificationListenerEnabled() + openNotificationListenerSettings()
util/TimeUtil.kt — formatRelativeTime() for "2m ago", "1h ago", "Yesterday" etc.

Phase 7: ViewModels (~3 files)

ui/list/NotificationListViewModel.kt — manages notification list, search query, app filter; combines Flows
ui/detail/NotificationDetailViewModel.kt — single notification by ID
ui/settings/SettingsViewModel.kt — DataStore preferences (autoDeleteDays, captureOngoing)

Phase 8: UI Composables (~7 files)

ui/theme/Color.kt — Material 3 colors
ui/theme/Type.kt — Typography
ui/theme/Theme.kt — NSATheme with dynamic color support
ui/navigation/NavGraph.kt — NavHost with List, Detail/{id}, Settings routes
ui/list/NotificationListScreen.kt — search bar, app filter chips, LazyColumn of notification cards, swipe-to-delete, empty state
ui/detail/NotificationDetailScreen.kt — full notification content with delete action
ui/settings/SettingsScreen.kt — notification access toggle, auto-delete config, clear all, about
MainActivity.kt — setContent with theme + nav graph, permission check on launch

Total: ~30 files
Phase 9: Build & Commit

Run ./gradlew assembleDebug to verify APK builds
Commit all files and push to branch


Key Files (most critical to get right)
FileWhyapp/build.gradle.ktsWrong dependency versions = build fails. KSP must match Kotlin exactly.AndroidManifest.xmlService must have correct permission + intent-filter or it won't activateservice/NotificationListener.ktCore capture logic; notification extras vary by Android versiondata/dao/NotificationDao.ktAll data flows through here; Flow queries power reactive UIui/list/NotificationListScreen.ktPrimary user-facing screen
