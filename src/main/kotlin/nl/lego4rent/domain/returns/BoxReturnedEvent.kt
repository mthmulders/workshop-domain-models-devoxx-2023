package nl.lego4rent.domain.returns

import nl.lego4rent.domain.shared.Event
import java.time.OffsetDateTime
import java.util.UUID

data class BoxReturnedEvent(val boxNumber: UUID, val returnedAt: OffsetDateTime) : Event
