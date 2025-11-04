package pivot.app.test.purchasesrequest.domain

import pivot.app.test.purchaserequests.domain.objects.PurchaseRequest
import pivot.app.test.purchaserequests.domain.objects.PurchaseRequestRepository

class InMemoryPurchaseRequestRepository : PurchaseRequestRepository {
    private val purchaseRequets = ArrayList<PurchaseRequest>()

    override fun findById(id: Int): PurchaseRequest? {
        return purchaseRequets.find { it.id == id }?.let { return it }
    }

    override fun save(purchaseRequest: PurchaseRequest) {
        purchaseRequets.removeIf { it.id == purchaseRequest.id }
        purchaseRequets.add(purchaseRequest)
    }

}
