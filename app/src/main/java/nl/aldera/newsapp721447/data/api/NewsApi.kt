package nl.aldera.newsapp721447.data.api

import android.media.session.MediaSession
import nl.aldera.newsapp721447.data.model.AllArticlesContainer
import nl.aldera.newsapp721447.data.model.Article
import nl.aldera.newsapp721447.data.model.RegisterMessage
import nl.aldera.newsapp721447.data.model.Token
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface NewsApi {
    @GET("api/Articles")
    suspend fun getArticles(): Response<AllArticlesContainer>

    @GET("api/Articles/{Id}")
    suspend fun getArticle(
        @Path("Id") Id: Int
    ): Response<AllArticlesContainer>

    @FormUrlEncoded
    @POST("api/Users/register")
    suspend fun registerUser(
        @Field("UserName") UserName : String,
        @Field("Password") Password : String
    ): Response<RegisterMessage>

    @FormUrlEncoded
    @POST("api/Users/login")
    suspend fun loginUser(
        @Field("UserName") UserName : String,
        @Field("Password") Password : String
    ): Response<Token>
}