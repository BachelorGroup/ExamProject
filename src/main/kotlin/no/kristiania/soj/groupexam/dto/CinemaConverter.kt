package no.kristiania.soj.groupexam.dto

import no.kristiania.soj.groupexam.db.Cinema

object CinemaConverter {

    fun transform(entity: Cinema): CinemaDTO {
        return CinemaDTO(
                id = entity.id,
                name = entity.name,
                movies = entity.movies,
                halls = entity.halls
        ).apply { id = entity.id }
    }

    fun transform(entities: Iterable<Cinema>): List<CinemaDTO> {
        return entities.map { transform(it) }
    }
}