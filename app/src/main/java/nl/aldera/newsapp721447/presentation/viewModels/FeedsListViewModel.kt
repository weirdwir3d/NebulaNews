package nl.aldera.newsapp721447.presentation.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import nl.aldera.newsapp721447.data.api.NewsApi
import nl.aldera.newsapp721447.data.model.Feed
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

class FeedsListViewModel : ViewModel() {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://inhollandbackend.azurewebsites.net/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
    private val api = retrofit.create<NewsApi>()

    private var selectedFeedMutableState = MutableStateFlow<Int>(-1)
    val selectedFeedState : StateFlow<Int> = selectedFeedMutableState

    private val _feedsList = mutableListOf<Feed>()

    private val mutableFeedsList = MutableStateFlow<List<Feed>>(emptyList())
    val feedsList: StateFlow<List<Feed>> = mutableFeedsList

    init {
        viewModelScope.launch {
            var responseBody = fetchFeeds().body().toString()
            val feedList: List<Feed> = responseBody.toFeedList()
            Log.d("feeds", feedList.toString())
            for (feed in feedList) {
                addFeed(feed)
            }
            Log.d("feeds after filtering", _feedsList.toString())
        }
    }

    fun addSelectedFeedState(feedId : Int) {
        selectedFeedMutableState.tryEmit(feedId)
    }

    fun getFeed(Id : Double) : Feed? {
        return _feedsList.find { it.Id == Id }
    }

    fun String.toFeedList(): List<Feed> {
        return Gson().fromJson(this, Array<Feed>::class.java).toList()
    }

    fun getFeedsList() : List<Feed> {
        return _feedsList
    }

    fun getSize() : Int {
        return _feedsList.size
    }

    fun contains(feed : Feed) : Boolean {
        return _feedsList.contains(feed)
    }
    fun addFeed(feed : Feed) : Boolean {
        if (!_feedsList.contains(feed)) {
            _feedsList.add(feed)
            mutableFeedsList.tryEmit(_feedsList.toList())
            return true
        }
        return false
    }

    fun clearList() {
        mutableFeedsList.value = emptyList()
    }


    suspend fun fetchFeeds(): Response<List<Any>> {
        return api.getFeeds()
//            .map(articleContainerMapper::mapList).flatten()
    }

}