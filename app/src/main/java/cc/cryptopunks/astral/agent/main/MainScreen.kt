package cc.cryptopunks.astral.agent.main

import android.net.Uri
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cc.cryptopunks.astral.agent.admin.AdminScreen
import cc.cryptopunks.astral.agent.admin.AdminWrapToggle
import cc.cryptopunks.astral.agent.compose.AstralTheme
import cc.cryptopunks.astral.agent.config.ConfigEditorScreen
import cc.cryptopunks.astral.agent.config.ConfigScreen
import cc.cryptopunks.astral.agent.dashboard.DashboardItem
import cc.cryptopunks.astral.agent.dashboard.DashboardScreen
import cc.cryptopunks.astral.agent.exception.ErrorsScreen
import cc.cryptopunks.astral.agent.js.JsAppsScreen
import cc.cryptopunks.astral.agent.log.LogScreen
import cc.cryptopunks.astral.agent.log.LogWrapToggle

@Composable
fun MainScreen() {
    AstralTheme {
        val mainNavController = rememberNavController()
        val dashboardNavController = rememberNavController()
        NavHost(
            navController = mainNavController,
            startDestination = "dashboard"
        ) {
            composable("dashboard") {
                DashboardScreen(
                    navController = dashboardNavController,
                    actions = { MainServiceToggle() },
                    items = remember {
                        listOf(
                            DashboardItem("log", Icons.Default.List,
                                actions = { LogWrapToggle() }
                            ) {
                                LogScreen()
                            },
                            DashboardItem("config", Icons.Default.Settings) {
                                ConfigScreen { file ->
                                    val route = "config_editor/${Uri.encode(file.absolutePath)}"
                                    mainNavController.navigate(route)
                                }
                            },
                            DashboardItem("admin", Icons.Default.Face,
                                actions = { AdminWrapToggle() }) {
                                AdminScreen()
                            },
                            DashboardItem("apps", Icons.Default.PlayArrow) {
                                JsAppsScreen()
                            },
                        )
                    },
                )
            }
            composable("config_editor/{file}") {
                ConfigEditorScreen(mainNavController)
            }
        }
        ErrorsScreen()
    }
}
