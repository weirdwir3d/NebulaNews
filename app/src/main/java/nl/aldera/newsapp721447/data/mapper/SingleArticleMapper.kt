package nl.aldera.newsapp721447.data.mapper

import nl.aldera.newsapp721447.data.model.AllArticlesContainer
import nl.aldera.newsapp721447.data.model.Article
import nl.aldera.newsapp721447.data.model.Category

class SingleArticleMapper {
//    var categories : List<Category> = ["Economics"]

    fun mapOneArticle(result : AllArticlesContainer) : Result<Article> {
        println("------------------------------------------------------------------")
        println(result.Results[0])
        var article = Article(
            Categories = result.Results[0].Categories,
            Id = result.Results[0].Id,
            Title = result.Results[0].Title,
            Feed = result.Results[0].Feed,
            Image = result.Results[0].Image,
            IsLiked = result.Results[0].IsLiked,
            PublishDate = result.Results[0].PublishDate,
            Related = result.Results[0].Related,
            Summary = result.Results[0].Summary,
            Url = result.Results[0].Url)

        return runCatching {
            article
//            result.Results.filterNotNull().map {}
        }
    }
}

//private fun map(entity : Article) {
//    Article(
//        Id = r
//    )
//}