package nl.aldera.newsapp721447.data.mapper

import nl.aldera.newsapp721447.data.model.Article
import nl.aldera.newsapp721447.presentation.viewModels.ui.model.DetailState
import javax.inject.Inject

class DetailStateMapper @Inject constructor(){
    fun map(article : Article) : DetailState {
        return DetailState.Success(article)
    }
}