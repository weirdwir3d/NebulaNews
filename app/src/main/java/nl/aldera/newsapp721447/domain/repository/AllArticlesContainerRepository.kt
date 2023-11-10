package nl.aldera.newsapp721447.domain.repository

import nl.aldera.newsapp721447.RetrofitInstance
import nl.aldera.newsapp721447.data.model.AllArticlesContainer
import retrofit2.Response

class AllArticlesContainerRepository {
    private val newsApi = RetrofitInstance.newsApi

    suspend fun getArticles(): Response<AllArticlesContainer> {
        return newsApi.getArticles()
    }
}