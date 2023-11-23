package nl.aldera.newsapp721447.presentation.viewModels.ui.component

import androidx.annotation.StringRes
import nl.aldera.newsapp721447.R

sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    object Home : Screen("articles", R.string.homepage_title)
    object Favorites : Screen("favorites", R.string.favourites_page_title)
    object Account : Screen("account", R.string.account_page_title)
}

