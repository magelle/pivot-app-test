package pivot.app.test.purchaserequests.adapter

import org.springframework.stereotype.Service
import pivot.app.test.purchaserequests.domain.objects.IdGenerator

@Service
class SystemIdGenerator : IdGenerator {
    override fun getNext(): Int = System.currentTimeMillis().toInt()
}