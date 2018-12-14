package no.kristiania.soj.groupexam.ticket

import com.google.common.base.Throwables
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import no.kristiania.soj.groupexam.ticket.dto.TicketDTO
import no.kristiania.soj.groupexam.ticket.db.TicketRepository
import no.kristiania.soj.groupexam.ticket.dto.TicketConverter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.ConstraintViolationException


@RestController
@RequestMapping(path = ["/api/ticket"], produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
@Validated
class TicketApi {

    @Autowired
    private lateinit var crud: TicketRepository

    @ApiOperation("Simple ticket creation")
    @PostMapping(consumes = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    @ApiResponse(code = 201, message = "The ticketId of created ticket")
    fun createTicket(
            @ApiParam("Information about cinema, hall, row, column and date of the movie. Do not specify ticketId or date of purchase")
            @RequestBody
            dto: TicketDTO

    ): ResponseEntity<Long> {

        if (!(dto.ticketId.isNullOrEmpty())) {
            return ResponseEntity.status(400).build()
        }

        if (dto.purchaseDateTime != null || dto.cinema == null || dto.hall == null || dto.seatRow == null || dto.seatColumn == null || dto.movieTitle == null ||  dto.movieDateTime == null) {
            return ResponseEntity.status(400).build()
        }

        val id: Long?
        try {
            id = crud.createTicket(dto.cinema!!, dto.hall!!, dto.seatRow!!, dto.seatColumn!!, dto.movieTitle!!, dto.movieDateTime!!)
        } catch (e: Exception) {
            if(Throwables.getRootCause(e) is ConstraintViolationException) {
                return ResponseEntity.status(400).build()
            }
            throw e
        }

        return ResponseEntity.status(201).body(id)
    }

    @ApiOperation("Get all tickets")
    @GetMapping
    fun getAll(

    ): ResponseEntity<List<TicketDTO>> {

        val list = crud.findAll()

        return ResponseEntity.ok(TicketConverter.transform(list))
    }

    @ApiOperation("Get a single ticket specified by id")
    @GetMapping(path = ["/{id}"])
    fun getTicket(@ApiParam("The id of the ticket")
                @PathVariable("id")
                pathId: String?

    ): ResponseEntity<TicketDTO> {

        val id: Long
        try {
            id = pathId!!.toLong()
        } catch (e: Exception) {
            /*
                invalid id. But here we return 404 instead of 400,
                as in the API we defined the id as string instead of long
             */
            return ResponseEntity.status(404).build()
        }

        val dto = crud.findById(id).orElse(null) ?: return ResponseEntity.status(404).build()
        return ResponseEntity.ok(TicketConverter.transform(dto))
    }

    @ApiOperation("Update an existing ticket")
    @PutMapping(path = ["/{id}"], consumes = [(MediaType.APPLICATION_JSON_VALUE)])
    fun updateTicket(
            @ApiParam("The id of the ticket")
            @PathVariable("id")
            pathId: String?,
            @ApiParam("The ticket that will replace the old one")
            @RequestBody
            dto: TicketDTO

    ): ResponseEntity<Any> {

        val dtoId: Long
        try {
            dtoId = dto.ticketId!!.toLong()
        } catch (e: Exception) {

            return ResponseEntity.status(404).build()
        }

        if (dto.ticketId != pathId) {

            return ResponseEntity.status(409).build()
        }

        if (!crud.existsById(dtoId)) {

            return ResponseEntity.status(404).build()
        }

        if (dto.cinema == null || dto.hall == null || dto.seatRow == null || dto.seatColumn == null || dto.movieTitle == null || dto.movieDateTime == null) {
            return ResponseEntity.status(400).build()
        }

        try {
            crud.update(dtoId, dto.cinema!!, dto.hall!!, dto.seatRow!!, dto.seatColumn!!, dto. movieTitle!!, dto.movieDateTime!!)
        } catch (e: Exception) {
            if(Throwables.getRootCause(e) is ConstraintViolationException) {
                return ResponseEntity.status(400).build()
            }
            throw e
        }

        return ResponseEntity.status(204).build()
    }

    @ApiOperation("Update the seat")
    @PatchMapping(path = ["/{id}/seat"], consumes = [(MediaType.APPLICATION_JSON_UTF8_VALUE)])
    fun updateSeat(
            @ApiParam("The id of the ticket")
            @PathVariable("id")
            pathId: String?,
            @ApiParam("The new row") seatRow: Int,
            @ApiParam("The new seat") seatColumn: Int

    ): ResponseEntity<Any> {

        val id: Long
        try {
            id = pathId!!.toLong()
        } catch (e: Exception) {
            return ResponseEntity.status(400).build()
        }

        if (!crud.existsById(id)) {
            return ResponseEntity.status(404).build()
        }

        try {
            crud.updateSeat(id, seatRow, seatColumn)
        } catch (e: Exception) {
            if(Throwables.getRootCause(e) is ConstraintViolationException) {
                return ResponseEntity.status(400).build()
            }
            throw e
        }

        return ResponseEntity.status(204).build()
    }

    @ApiOperation("Delete a ticket with the given id")
    @DeleteMapping(path = ["/{id}"])
    fun deleteTicket(@ApiParam("The id of the ticket")
               @PathVariable("id")
               pathId: String?): ResponseEntity<Any> {

        val id: Long
        try {
            id = pathId!!.toLong()
        } catch (e: Exception) {
            return ResponseEntity.status(400).build()
        }

        if (!crud.existsById(id)) {
            return ResponseEntity.status(404).build()
        }

        crud.deleteById(id)
        return ResponseEntity.status(204).build()
    }
}
