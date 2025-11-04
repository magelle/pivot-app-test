package pivot.app.test.purchasesrequest.domain

import pivot.app.test.purchaserequests.domain.objects.Clock
import java.time.LocalDateTime

class TestingClock: Clock {
    private var now = LocalDateTime.now()

    fun setNow(now: LocalDateTime) {
        this.now = now
    }

    override fun getNow(): LocalDateTime {
        return now
    }
}