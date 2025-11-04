package pivot.app.test.purchaserequests.adapter

import org.springframework.stereotype.Service
import pivot.app.test.purchaserequests.domain.objects.Clock
import java.time.LocalDateTime

@Service
class SystemClock : Clock {
    override fun getNow(): LocalDateTime = LocalDateTime.now()
}