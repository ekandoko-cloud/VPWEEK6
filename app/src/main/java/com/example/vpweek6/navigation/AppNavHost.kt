package com.example.vpweek6.navigation

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
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

private data class BottomNavigationItem(
    val route: Any, val label: String, val icon: ImageVector
)

private val bottomNavigationItems = listOf(
    BottomNavigationItem(route = DashboardRoute, label = "Dashboard", icon = Icons.Default.Home),
    BottomNavigationItem(
        route = CommunitiesRoute,
        label = "Communities",
        icon = Icons.Default.Person
    )
)

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier, navController: NavHostController = rememberNavController()
) {
    val currentBackStackEntry = navController.currentBackStackEntryAsState().value
    val currentDestination = currentBackStackEntry?.destination
    val shouldShowBottomBar = bottomNavigationItems.any { bottomNavigationItem ->
        currentDestination?.hasRoute(bottomNavigationItem.route::class) == true
    }

    Scaffold(
        modifier = modifier, bottomBar = {
            if (shouldShowBottomBar) {
                NavigationBar {
                    for (bottomNavigationItem in bottomNavigationItems) {
                        NavigationBarItem(
                            selected = currentDestination?.hasRoute(bottomNavigationItem.route::class) == true,
                            onClick = {
                                navController.navigate(bottomNavigationItem.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                Icon(
                                    bottomNavigationItem.icon,
                                    contentDescription = bottomNavigationItem.label
                                )
                            },
                            label = { Text(bottomNavigationItem.label) })
                    }
                }
            }
        }) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = DashboardRoute,
            modifier = Modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
        ) {
            composable<DashboardRoute> { HomeScreen() }

            composable<CommunitiesRoute> {
                CommunitiesScreen(
                    onOpenCommunityProjects = { communityId ->
                        navController.navigate(ClubProjectsRoute(communityId))
                    })
            }

            composable<ClubProjectsRoute> { backStackEntry ->
                val route: ClubProjectsRoute = backStackEntry.toRoute()
                ClubProjectsScreen(
                    communityId = route.communityId,
                    onBack = { navController.popBackStack() },
                    onProjectClick = { projectId ->
                        navController.navigate(ProjectDetailRoute(projectId))
                    })
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
