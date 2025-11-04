package pivot.app.test.purchaserequests.domain.objects

import java.time.LocalDateTime

data class PurchaseRequest(
    val id: Int,
    val companyId: Int,
    val description: String,
    val amount: Double,
    val issueDate: LocalDateTime,
    var status: Status,
) {

}