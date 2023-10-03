package nl.lego4rent.domain.shared

import java.time.OffsetDateTime
import java.util.UUID
import kotlin.time.Duration

data class RentalInformation(
        val customerId: UUID,
        val rentalStart: OffsetDateTime,
        val rentalDuration: Duration
)
