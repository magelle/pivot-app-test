package pivot.app.test.purchaserequests.domain.usecases

import org.springframework.stereotype.Service
import pivot.app.test.purchaserequests.domain.objects.PurchaseRequestRepository
import pivot.app.test.purchaserequests.domain.objects.Status

@Service
class DeclinePurchaseRequestUseCase(
    private val purchaseRequestRepository: PurchaseRequestRepository
) {
    fun decline(id: Int) {
        val r = purchaseRequestRepository.findById(id)
        r?.let {
            it.status = Status.DECLINE
            purchaseRequestRepository.save(it)
        }
    }

}
