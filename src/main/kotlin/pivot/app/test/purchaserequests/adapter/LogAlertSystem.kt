package pivot.app.test.purchaserequests.adapter

import org.springframework.stereotype.Service
import pivot.app.test.purchaserequests.domain.objects.Alert
import pivot.app.test.purchaserequests.domain.objects.AlertSystem

@Service
class LogAlertSystem : AlertSystem {
    override fun send(alert: Alert) {
        println("Alert on purchase request ${alert.id}: ${alert.description}")
    }
}