package nl.lego4rent.domain.box

import assertk.assertFailure
import assertk.assertThat
import assertk.assertions.containsOnly
import assertk.assertions.isEqualTo
import assertk.assertions.messageContains
import nl.lego4rent.domain.returns.BoxMarkedIncompleteEvent
import nl.lego4rent.domain.returns.BoxReturnedEvent
import nl.lego4rent.domain.returns.MarkBoxIncompleteCommand
import nl.lego4rent.domain.returns.ReturnBoxCommand
import org.junit.jupiter.api.Test
import java.time.OffsetDateTime
import java.util.UUID

class BoxAggregateTest {
    @Test
    fun `when Box is in store, marking it incomplete should return an event`() {
        val missingPieces = listOf<Any>()
        val box = BoxAggregate(UUID.randomUUID(), BoxStatus.IN_STORE)

        val result = box.handleCommand(MarkBoxIncompleteCommand(missingPieces))

        assertThat(result).containsOnly(BoxMarkedIncompleteEvent(box.boxNumber, missingPieces))
    }

    @Test
    fun `when Box is in store, marking it incomplete should make it not available for rent`() {
        val missingPieces = listOf<Any>()
        val box = BoxAggregate(UUID.randomUUID(), BoxStatus.IN_STORE)

        box.handleCommand(MarkBoxIncompleteCommand(missingPieces))

        assertThat(box.status).isEqualTo(BoxStatus.NOT_AVAILABLE_FOR_RENT)
    }

    @Test
    fun `when Box is not in store, marking it incomplete should throw a failure`() {
        val box = BoxAggregate(UUID.randomUUID(), BoxStatus.NOT_AVAILABLE_FOR_RENT)

        assertFailure { box.handleCommand(MarkBoxIncompleteCommand(listOf())) }
                .messageContains("Cannot mark box as incomplete: it is not available for rent")
    }

    @Test
    fun `when Box is out for rent, returning it should return an event`() {
        val returnTimestamp = OffsetDateTime.now()
        val box = BoxAggregate(UUID.randomUUID(), BoxStatus.OUT_FOR_RENT)

        val result = box.handleCommand(ReturnBoxCommand(returnTimestamp))

        assertThat(result).containsOnly(BoxReturnedEvent(box.boxNumber, returnTimestamp))
    }

    @Test
    fun `when Box is out for rent, returning it should mark it as in store`() {
        val returnTimestamp = OffsetDateTime.now()
        val box = BoxAggregate(UUID.randomUUID(), BoxStatus.OUT_FOR_RENT)

        box.handleCommand(ReturnBoxCommand(returnTimestamp))

        assertThat(box.status).isEqualTo(BoxStatus.IN_STORE)
    }

    @Test
    fun `when Box is not out for rent, returning it should throw a failure`() {
        val returnTimestamp = OffsetDateTime.now()
        val box = BoxAggregate(UUID.randomUUID(), BoxStatus.IN_STORE)

        assertFailure { box.handleCommand(ReturnBoxCommand(returnTimestamp)) }
                .messageContains("Cannot return a box that was not out for rent")
    }

    @Test
    fun `when Box is incomplete, adding Parts should return an event`() {
        val box = BoxAggregate(UUID.randomUUID(), BoxStatus.INCOMPLETE)

        val result = box.handleCommand(AddPartsToBoxCommand())

        assertThat(result).containsOnly(PartsAddedToBoxEvent())
    }
}