package pivot.app.test.purchaserequests.domain.usecases

import pivot.app.test.purchaserequests.domain.objects.PurchaseRequest
import pivot.app.test.purchaserequests.domain.objects.PurchaseRequestRepository

class GetPurchaseRequestUseCase(
    private val purchaseRequestRepository: PurchaseRequestRepository
) {
    fun get(id: Int): PurchaseRequest? {
        return purchaseRequestRepository.findById(id)
    }
}