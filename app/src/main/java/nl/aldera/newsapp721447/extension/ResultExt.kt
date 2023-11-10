package nl.aldera.newsapp721447.extension

fun <T> Result<Result<T>>.flatten(): Result<T> {
    return getOrElse { Result.failure(it) }
}