package no.kristiania.soj.groupexam.dto

import no.kristiania.soj.groupexam.db.TicketEntity

class TicketConverter {

    companion object {

        fun transform(entity: TicketEntity): TicketDTO {
            return TicketDTO(
                    ticketId = entity.id?.toString(),
                    cinema = entity.cinema,
                    hall = entity.hall,
                    seatRow = entity.seatRow,
                    seatColumn = entity.seatColumn,
                    movieTitle = entity.movieTitle,
                    movieDateTime = entity.movieDateTime,
                    purchaseDateTime = entity.purchaseDateTime
            )
        }

        fun transform(entities: Iterable<TicketEntity>): List<TicketDTO> {
            return entities.map { transform(it) }
        }
    }
}