package pivot.app.test.purchaserequests.domain.usecases

import pivot.app.test.purchaserequests.domain.objects.PurchaseRequestRepository
import pivot.app.test.purchaserequests.domain.objects.Status

class DeclinePurchaseRequest(private val purchaseRequestRepository: PurchaseRequestRepository) {
    fun decline(id: Int) {
        val r = purchaseRequestRepository.findById(id)
        r?.let {
            it.status = Status.DECLINE
            purchaseRequestRepository.save(it)
        }
    }

}
