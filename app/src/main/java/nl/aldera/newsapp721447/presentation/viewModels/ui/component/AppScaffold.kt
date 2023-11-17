package nl.aldera.newsapp721447.presentation.viewModels.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import nl.aldera.newsapp721447.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(
    navController : NavController,
    title: String,
    navigation: NavigationType? = null,
    content: @Composable (PaddingValues) -> Unit
) {

    Scaffold(
        topBar = {
            AppScaffoldTopBar(
                title = title,
                navigation = navigation,
                actions = {
                    IconButton(onClick = {
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Refresh,
                            contentDescription = stringResource(R.string.network_refresh)
                        )
                    }
                }
            )
        },
        content = content,

        bottomBar = {
            AppScaffoldBottomBar(
                title = title,
                actions = {
                    IconButton(onClick = {
                        navController.navigate("articles")
                    }) {
                        Icon(imageVector = Icons.Outlined.Home, contentDescription = "Home")
                    }
                    IconButton(onClick = {
                        navController.navigate("favorites")
                    }) {
                        Icon(imageVector = Icons.Outlined.FavoriteBorder, contentDescription = "Favorites")
                    }
                    IconButton(onClick = {
                        navController.navigate("account")
                    }) {
                        Icon(imageVector = Icons.Outlined.AccountCircle, contentDescription = "Account")
                    }
                }
            )
        }
    )
}

@Composable
fun showPopup() {
    Box {
        Text("hello")
    }
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


@Composable
fun AppScaffoldBar() {
    //
}

@OptIn(ExperimentalMaterial3Api::class)
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
        contentColor = Color.White
    )
}

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
            scrolledContainerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary
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