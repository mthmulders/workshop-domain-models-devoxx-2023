package nl.lego4rent.domain.box

/**
 * One part of a Lego® set, identified by a part number. A [BoxAggregate] can have multiple items with the
 * same [PartNumber].
 */
data class Part(val partNumber: PartNumber) {
}
