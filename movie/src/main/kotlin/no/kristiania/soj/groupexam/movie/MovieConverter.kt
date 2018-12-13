package no.kristiania.soj.groupexam.movie

class MovieConverter {

    companion object {
        fun transform(entity: MovieEntity): MovieDTO {
            return MovieDTO(
                    id = entity.id?.toString(),
                    title = entity.title,
                    director = entity.director,
                    description = entity.description,
                    info = entity.info,
                    rating = entity.rating,
                    releaseDate = entity.releaseDate
            )
        }

        fun transform(entities: Iterable<MovieEntity>): List<MovieDTO> {
            return entities.map { transform(it) }
        }
    }
}