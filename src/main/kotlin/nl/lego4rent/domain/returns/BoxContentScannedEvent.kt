package nl.lego4rent.domain.returns

import nl.lego4rent.domain.box.BoxStatus
import nl.lego4rent.domain.shared.Event
import nl.lego4rent.domain.shared.LegoSetNumber
import nl.lego4rent.domain.shared.RentalInformation
import java.time.OffsetDateTime
import java.util.*

data class BoxContentScannedEvent(
        val boxNumber: UUID,
        val setNumber: LegoSetNumber,
        val scanningCompletedAt: OffsetDateTime,
        val missingPieces: Collection<Any>, // TODO or is Piece actually a Part
        val lastRentalInfo: RentalInformation,
        val boxStatus: BoxStatus
) : Event
