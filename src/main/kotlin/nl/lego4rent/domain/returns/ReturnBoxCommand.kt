package nl.lego4rent.domain.returns

import nl.lego4rent.domain.shared.Command
import java.time.OffsetDateTime

data class ReturnBoxCommand(val returnedAt: OffsetDateTime) : Command
