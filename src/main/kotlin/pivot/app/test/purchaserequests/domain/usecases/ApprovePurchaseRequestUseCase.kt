package pivot.app.test.purchaserequests.domain.usecases

import org.springframework.stereotype.Service
import pivot.app.test.purchaserequests.domain.objects.PurchaseRequestRepository
import pivot.app.test.purchaserequests.domain.objects.Status

@Service
class ApprovePurchaseRequestUseCase(
    private val purchaseRequestRepository: PurchaseRequestRepository
) {
    fun approve(id: Int) {
        val r = purchaseRequestRepository.findById(id)
        r?.let {
            it.status = Status.APPROVED
            purchaseRequestRepository.save(it)
        }
    }

}
