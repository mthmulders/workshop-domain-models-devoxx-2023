package nl.lego4rent.domain.box

import nl.lego4rent.domain.returns.BoxMarkedIncompleteEvent
import nl.lego4rent.domain.returns.BoxReturnedEvent
import nl.lego4rent.domain.returns.MarkBoxIncompleteCommand
import nl.lego4rent.domain.returns.ReturnBoxCommand
import nl.lego4rent.domain.shared.Command
import nl.lego4rent.domain.shared.Event
import java.lang.IllegalStateException
import java.util.UUID

/**
 * One Lego® set, identified by an internal box number.
 */
class BoxAggregate(
        val boxNumber: UUID,
        private var boxStatus: BoxStatus,
        private var missingParts: Collection<Part> = listOf()
) {
    val status: BoxStatus
        get() = boxStatus


    fun handleCommand(command: Command): List<Event> = when (command) {
        is MarkBoxIncompleteCommand -> markIncomplete(command)
        is ReturnBoxCommand -> returnBox(command)
        is AddPartsToBoxCommand -> addPartsToBoxCommand(command)
        else -> throw IllegalStateException("Box cannot handle a $command")
    }

    private fun addPartsToBoxCommand(command: AddPartsToBoxCommand): List<Event> {
        check(boxStatus == BoxStatus.INCOMPLETE) { "Cannot add parts to box that does not miss pieces" }
        check(boxStatus != BoxStatus.OUT_FOR_RENT) { "Cannot add parts to a box that is out for rent" }



        return listOf(PartsAddedToBoxEvent())
    }

    private fun markIncomplete(command: MarkBoxIncompleteCommand): List<Event> {
        check(boxStatus == BoxStatus.IN_STORE) { "Cannot mark box as incomplete: it is not available for rent" }

        boxStatus = BoxStatus.NOT_AVAILABLE_FOR_RENT

        return listOf(BoxMarkedIncompleteEvent(boxNumber, command.missingPieces))
    }

    private fun returnBox(command: ReturnBoxCommand): List<Event> {
        check(boxStatus == BoxStatus.OUT_FOR_RENT) { "Cannot return a box that was not out for rent" }

        boxStatus = BoxStatus.IN_STORE

        return listOf(BoxReturnedEvent(boxNumber, command.returnedAt))
    }
}