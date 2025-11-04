package pivot.app.test.purchaserequests.domain.usecases

import pivot.app.test.purchaserequests.domain.objects.PurchaseRequest
import pivot.app.test.purchaserequests.domain.objects.PurchaseRequestRepository
import pivot.app.test.purchaserequests.domain.objects.Status

class ApprovePurchaseRequest(private val purchaseRequestRepository: PurchaseRequestRepository) {
    fun approve(id: Int) {
        val r = purchaseRequestRepository.findById(id)
        r?.let {
            it.status = Status.APPROVED
            purchaseRequestRepository.save(it)
        }
    }

}
