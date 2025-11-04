package pivot.app.test.purchasesrequest.domain

import org.junit.jupiter.api.assertNull
import pivot.app.test.purchaserequests.domain.objects.Alert
import pivot.app.test.purchaserequests.domain.objects.AlertSystem
import kotlin.test.assertEquals

class TestingAlertSystem : AlertSystem {
    var alert: Alert? = null

    override fun send(alert: Alert) {
        this.alert = alert
    }

    fun assertAlertWasReceived(alert: Alert) {
        assertEquals(alert, this.alert)
    }

    fun assertNoAlertWasReceived() {
        assertNull(this.alert)
    }
}