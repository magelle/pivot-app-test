package pivot.app.test.purchaserequests.domain.objects

import java.time.LocalDateTime

interface Clock {
    fun getNow(): LocalDateTime
}