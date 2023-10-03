package nl.lego4rent.domain.returns

import nl.lego4rent.domain.shared.Command

data class MarkBoxIncompleteCommand(
        val missingPieces: Collection<Any>, // TODO or is Piece actually a Part
) : Command
