package com.example.vpweek6.navigation

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Group
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.vpweek6.ui.screen.ClubProjectsScreen
import com.example.vpweek6.ui.screen.CommunitiesScreen
import com.example.vpweek6.ui.screen.HomeScreen
import com.example.vpweek6.ui.screen.ProjectDetailScreen
import com.example.vpweek6.ui.widgets.AppBlue
import com.example.vpweek6.ui.widgets.AppDark
import com.example.vpweek6.ui.widgets.AppGray
import com.example.vpweek6.ui.widgets.AppLightBlue

private data class BottomNavigationItem(val route: Any, val label: String, val icon: ImageVector)

private val bottomNavigationItems = listOf(
    BottomNavigationItem(DashboardRoute, "Dashboard", Icons.Default.Home),
    BottomNavigationItem(CommunitiesRoute, "Communities", Icons.Default.Group)
)

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val currentBackStackEntry = navController.currentBackStackEntryAsState().value
    val currentDestination = currentBackStackEntry?.destination
    val shouldShowBottomBar =
        bottomNavigationItems.any { currentDestination?.hasRoute(it.route::class) == true }

    Scaffold(
        modifier = modifier,
        bottomBar = {
            if (shouldShowBottomBar) {
                NavigationBar(containerColor = Color.White, tonalElevation = 0.dp) {
                    for (item in bottomNavigationItems) {
                        val isSelected = currentDestination?.hasRoute(item.route::class) == true
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = { Icon(item.icon, contentDescription = item.label) },
                            label = {
                                Text(
                                    item.label,
                                    fontSize = 11.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = AppBlue,
                                selectedTextColor = AppDark,
                                indicatorColor = AppLightBlue,
                                unselectedIconColor = AppGray,
                                unselectedTextColor = AppGray
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = DashboardRoute,
            modifier = Modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
        ) {
            composable<DashboardRoute> {
                HomeScreen()
            }

            composable<CommunitiesRoute> {
                CommunitiesScreen(
                    onOpenCommunityProjects = { communityId ->
                        navController.navigate(ClubProjectsRoute(communityId))
                    }
                )
            }

            composable<ClubProjectsRoute> { backStackEntry ->
                val route: ClubProjectsRoute = backStackEntry.toRoute()
                ClubProjectsScreen(
                    communityId = route.communityId,
                    onBack = { navController.popBackStack() },
                    onProjectClick = { projectId ->
                        navController.navigate(ProjectDetailRoute(projectId))
                    }
                )
            }

            composable<ProjectDetailRoute> { backStackEntry ->
                val route: ProjectDetailRoute = backStackEntry.toRoute()
                ProjectDetailScreen(
                    projectId = route.projectId,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}