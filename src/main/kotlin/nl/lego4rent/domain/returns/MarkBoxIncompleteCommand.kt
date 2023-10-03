package nl.lego4rent.domain.returns

import nl.lego4rent.domain.box.Part
import nl.lego4rent.domain.shared.Command

data class MarkBoxIncompleteCommand(
        val missingPieces: Collection<Part>,
) : Command
