package no.kristiania.soj.groupexam.dto

import no.kristiania.soj.groupexam.db.Movie

object MovieConverter {

    fun transform(entity: Movie): MovieDTO {
        return MovieDTO(
                id = entity.id?.toString(),
                title = entity.title,
                description = entity.description,
                info = entity.info,
                rating = entity.rating,
                releaseDate = entity.releaseDate
        ).apply { id = entity.id?.toString() }
    }
    fun transform(entities: Iterable<Movie>): List<MovieDTO> {
        return entities.map { transform(it) }
    }
}