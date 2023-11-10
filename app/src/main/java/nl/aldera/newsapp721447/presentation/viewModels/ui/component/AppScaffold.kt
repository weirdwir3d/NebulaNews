package nl.aldera.newsapp721447.presentation.viewModels.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.BottomAppBarDefaults.containerColor
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import nl.aldera.newsapp721447.R
import nl.aldera.newsapp721447.presentation.viewModels.PopupViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(
    title: String,
    content: @Composable (PaddingValues) -> Unit
) {

    Scaffold(
        topBar = {
            AppScaffoldTopBar(
                title = title,
                actions = {
                    IconButton(onClick = {
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Refresh,
                            contentDescription = stringResource(R.string.network_refresh)
                        )
                    }

                    IconButton(onClick = {
                        //TODO: fix this
//                        showPopup()
                    }) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = stringResource(R.string.user_account)
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
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Outlined.Home, contentDescription = "Home")
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Outlined.FavoriteBorder, contentDescription = "Favorites")
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
    actions: @Composable RowScope.() -> Unit
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
            AppScaffoldTopBarNavigation(navigation = null)
        }
    )
}

@Composable
fun AppScaffoldTopBarNavigation(
    navigation: String?
//    navigation: NavigationType?
) {
//    when (navigation) {
//        is NavigationType.Top -> {
//            IconButton(navigation.onClick) {
//                Icon(
//                    painter = painterResource(R.drawable.ic_home),
//                    contentDescription = stringResource(R.string.navigate_up_description)
//                )
//            }
//        }
//        is NavigationType.Back -> {
//            IconButton(navigation.onClick) {
//                Icon(
//                    painter = painterResource(R.drawable.ic_back),
//                    contentDescription = stringResource(R.string.navigate_up_description)
//                )
//            }
//        }
//        else -> { /* NO-OP */ }
//    }
}