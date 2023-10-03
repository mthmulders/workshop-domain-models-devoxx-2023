package nl.lego4rent.domain.returns

import nl.lego4rent.domain.box.Part
import nl.lego4rent.domain.shared.Event
import java.util.UUID

data class BoxMarkedIncompleteEvent(
        val boxNumber: UUID,
//        val setNumber: LegoSetNumber,
        val missingPieces: Collection<Part>,
//        val lastRentalInfo: RentalInformation,
//        val boxStatus: BoxStatus
) : Event
