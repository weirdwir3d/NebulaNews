package nl.aldera.newsapp721447.presentation.viewModels.ui.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import nl.aldera.newsapp721447.data.model.Feed
import nl.aldera.newsapp721447.presentation.viewModels.FeedsListViewModel
import nl.aldera.newsapp721447.presentation.viewModels.ui.model.HomePageState

@Composable
fun FeedBox(
    feed : Feed,
    feedsListViewModel: FeedsListViewModel
) {

    val selectedFeedState by feedsListViewModel.selectedFeedState.collectAsState()
    var backgroundColor = MaterialTheme.colorScheme.secondary
    var textColor = MaterialTheme.colorScheme.primary

    when (val selectedFeedState = selectedFeedState) {
        feed.Id.toInt() -> {
            backgroundColor = MaterialTheme.colorScheme.primary
            textColor = MaterialTheme.colorScheme.secondary
        }
    }

    Row(
        modifier = Modifier
            .width(IntrinsicSize.Min)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(16.dp)
            )
            .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp))
            .padding(8.dp)
            .clickable {
                feedsListViewModel.addSelectedFeedState(feed.Id.toInt())
//                Log.d("selected feed", selectedFeedState.value)
            }
    ) {
        Text(text = feed.Name,
            color = textColor,
            style = MaterialTheme.typography.titleMedium
        )
    }

}
