package pivot.app.test.purchasesrequests.domain

import pivot.app.test.purchaserequests.domain.objects.PurchaseRequest
import pivot.app.test.purchaserequests.domain.objects.PurchaseRequestRepository
import pivot.app.test.purchaserequests.domain.objects.Status

class InMemoryPurchaseRequestRepository : PurchaseRequestRepository {
    private val purchaseRequets = ArrayList<PurchaseRequest>()

    override fun findById(id: Int): PurchaseRequest? {
        return purchaseRequets.find { it.id == id }?.let { return it }
    }

    override fun save(purchaseRequest: PurchaseRequest) {
        purchaseRequets.removeIf { it.id == purchaseRequest.id }
        purchaseRequets.add(purchaseRequest)
    }

    override fun findByCompanyIdAndStatus(
        companyId: Int,
        status: Status
    ): List<PurchaseRequest> {
        return purchaseRequets.filter { it.companyId == companyId && it.status == status }
    }

}
