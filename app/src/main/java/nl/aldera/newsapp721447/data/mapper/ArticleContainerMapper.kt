package nl.aldera.newsapp721447.data.mapper

import nl.aldera.newsapp721447.data.model.AllArticlesContainer
import nl.aldera.newsapp721447.data.model.AllArticlesContainerEntity
import nl.aldera.newsapp721447.data.model.Article

//class ArticleContainerMapper {
//    fun map(entity: AllArticlesContainerEntity): Result<AllArticlesContainer> {
//        return runCatching {
//            AllArticlesContainer(
//                nextId = entity.nextId!!,
//                results = entity.results!!,
////                rating = entity.rating?.let(ratingMapper::map)?.getOrThrow()
//            )
//        }
//    }
//
//    fun mapList(entities: AllArticlesContainerEntity): Result<AllArticlesContainerEntity> {
//        return runCatching {
//            entities.map { map(it).getOrThrow() }
//        }
//    }
//}