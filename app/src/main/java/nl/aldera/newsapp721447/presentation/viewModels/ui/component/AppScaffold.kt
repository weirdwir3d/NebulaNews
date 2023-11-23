package nl.aldera.newsapp721447.presentation.viewModels.ui.component

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import nl.aldera.newsapp721447.R
import nl.aldera.newsapp721447.data.model.SharedPreferencesManager


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(
    navController : NavController,
    title: String,
    navigation: NavigationType? = null,
    content: @Composable (PaddingValues) -> Unit,
    context : Context
) {

    var isDarkMode by remember { mutableStateOf(SharedPreferencesManager.isDarkMode()) }
    val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    val items = listOf(
        Screen.Home,
        Screen.Favorites,
        Screen.Account
    )

    Scaffold(
        topBar = {
            AppScaffoldTopBar(
                title = title,
                navigation = navigation,
                actions = {
                    IconButton(onClick = {
                        isDarkMode = toggleDarkMode(sharedPreferences)
                    }) {
                        val iconResource = if (!isDarkMode) R.drawable.dark_mode else R.drawable.light_mode
                        val mode: Painter = painterResource(id = iconResource)
                        Image(painter = mode, contentDescription = null)
                    }
                }

            )
        },
        content = content,

        bottomBar = {
            BottomNavigation(
                backgroundColor = MaterialTheme.colorScheme.primary
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { screen ->
                    BottomNavigationItem(
                        icon = {
                               when (screen) {
                                   is Screen.Home -> Icon(Icons.Filled.Home, contentDescription = null, tint = MaterialTheme.colorScheme.secondary)
                                   is Screen.Favorites -> Icon(Icons.Filled.Favorite, contentDescription = null, tint = MaterialTheme.colorScheme.secondary)
                                   is Screen.Account -> Icon(Icons.Filled.AccountCircle, contentDescription = null, tint = MaterialTheme.colorScheme.secondary)
                               }

                        },
                        label = { Text(stringResource(screen.resourceId), color = MaterialTheme.colorScheme.secondary) },
                        selectedContentColor = MaterialTheme.colorScheme.tertiary,
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        },
                    )
                }
            }
        }
//        {
//            AppScaffoldBottomBar(
//                title = title,
//                actions = {
//                    Row(horizontalArrangement = Arrangement.SpaceEvenly,
//                        modifier = Modifier.fillMaxWidth()) {
//                        IconButton(onClick = {
//                            navController.navigate("articles")
//                        }) {
//                            Icon(imageVector = Icons.Filled.Home, contentDescription = stringResource(R.string.homepage_title))
//                        }
//                        IconButton(onClick = {
//                            navController.navigate("favorites")
//                        }) {
//                            Icon(imageVector = Icons.Filled.Favorite, contentDescription = stringResource(R.string.favourites_page_title))
//                        }
//                        IconButton(onClick = {
//                            navController.navigate("account")
//                        }) {
//                            Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = stringResource(R.string.account_page_title))
//                        }
//                    }
//                }
//            )
//        }
    )
}

//@OptIn(ExperimentalMaterial3Api::class)
//fun scaff() {
//    Scaffold(
//        bottomBar = {
//            BottomNavigation {
//                val navBackStackEntry by navController.currentBackStackEntryAsState()
//                val currentDestination = navBackStackEntry?.destination
//                items.forEach { screen ->
//                    BottomNavigationItem(
//                        icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
//                        label = { Text(stringResource(screen.resourceId)) },
//                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
//                        onClick = {
//                            navController.navigate(screen.route) {
//                                // Pop up to the start destination of the graph to
//                                // avoid building up a large stack of destinations
//                                // on the back stack as users select items
//                                popUpTo(navController.graph.findStartDestination().id) {
//                                    saveState = true
//                                }
//                                // Avoid multiple copies of the same destination when
//                                // reselecting the same item
//                                launchSingleTop = true
//                                // Restore state when reselecting a previously selected item
//                                restoreState = true
//                            }
//                        }
//                    )
//                }
//            }
//        }
//    ) { innerPadding ->
//        NavHost(navController, startDestination = Screen.Profile.route, Modifier.padding(innerPadding)) {
//            composable(Screen.Profile.route) { Profile(navController) }
//            composable(Screen.FriendsList.route) { FriendsList(navController) }
//        }
//    }
//}




@Composable
fun AppScaffoldBottomBar(
    title: String,
//    navigation: NavigationType?,
    actions: @Composable RowScope.() -> Unit
) {

    BottomAppBar(
        modifier = Modifier
            .fillMaxHeight(0.1f),
        actions = actions,

        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.secondary
    )
}

fun toggleDarkMode(sharedPreferences: SharedPreferences) : Boolean {
    Log.d("darkmode", "toggling")
    Log.d("darkmode", "SharedPreferencesManager.isDarkMode(): " + SharedPreferencesManager.isDarkMode().toString())
    val editor = sharedPreferences.edit().putBoolean("isDarkMode", !SharedPreferencesManager.isDarkMode())
    editor.apply()
    Log.d("darkmode", "SharedPreferencesManager.isDarkMode(): " + SharedPreferencesManager.isDarkMode().toString())
    return SharedPreferencesManager.isDarkMode()
}

//@Composable
//fun MyBottomNavigation() {
//    BottomNavigation {
//        BottomNavigationItem(
//            icon = Icons.Default.Home,
//            label = { Text("Home") },
//            onClick = { /* Do something when the Home item is clicked. */ }
//        )
//
//        BottomNavigationItem(
//            icon = Icons.Default.Search,
//            label = { Text("Search") },
//            onClick = { /* Do something when the Search item is clicked. */ }
//        )
//    }
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffoldTopBar(
    title: String,
//    navigation: NavigationType?,
    actions: @Composable() (RowScope.() -> Unit),
    navigation: NavigationType?
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        actions = actions,
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            scrolledContainerColor = MaterialTheme.colorScheme.secondary,
            titleContentColor = MaterialTheme.colorScheme.secondary,
            navigationIconContentColor = MaterialTheme.colorScheme.secondary,
            actionIconContentColor = MaterialTheme.colorScheme.secondary
        ),
        navigationIcon = {
            AppScaffoldTopBarNavigation(navigation)
        }
    )
}

@Composable
fun AppScaffoldTopBarNavigation(
    navigation: NavigationType?
) {
    when (navigation) {
        is NavigationType.Top -> {
            IconButton(navigation.onClick) {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = stringResource(R.string.navigate_up)
                )
            }
        }
        is NavigationType.Back -> {
            IconButton(navigation.onClick) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.navigate_back)
                )
            }
        }
        else -> { /* NO-OP */ }
    }
}