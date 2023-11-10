package nl.aldera.newsapp721447.data.model

data class Article(
    val Categories: List<Category>,
    val Feed: Int,
    val Id: Int,
    val Image: String,
    val IsLiked: Boolean,
    val PublishDate: String,
    val Related: List<String>,
    val Summary: String,
    val Title: String,
    val Url: String
)