package nl.aldera.newsapp721447.data.model

data class Article(
    val Categories: List<Category>? = null,
    val Feed: Int? = null,
    val Id: Int? = null,
    val Image: String? = null,
    val IsLiked: Boolean? = null,
    val PublishDate: String? = null,
    val Related: List<String>? = null,
    val Summary: String? = null,
    val Title: String? = null,
    val Url: String? = null
)