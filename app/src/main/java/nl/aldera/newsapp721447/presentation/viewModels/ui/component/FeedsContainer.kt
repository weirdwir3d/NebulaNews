package nl.aldera.newsapp721447.presentation.viewModels.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import nl.aldera.newsapp721447.presentation.viewModels.FeedsListViewModel

@Composable
fun FeedsContainer(
    feedsListViewModel: FeedsListViewModel
) {

    var feedsList = feedsListViewModel.getFeedsList()

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(MaterialTheme.colorScheme.secondary)
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp), // Optional: Adjust spacing between items
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(feedsList.size) { feed ->
            feedsListViewModel.getFeed(feed.toDouble())?.let { FeedBox(it, feedsListViewModel) }
        }
    }

}