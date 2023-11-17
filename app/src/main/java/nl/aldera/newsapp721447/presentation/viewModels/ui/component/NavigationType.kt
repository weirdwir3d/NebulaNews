package nl.aldera.newsapp721447.presentation.viewModels.ui.component

sealed class NavigationType {
    data class Top(val onClick: () -> Unit): NavigationType()
    data class Back(val onClick: () -> Unit): NavigationType()
}
