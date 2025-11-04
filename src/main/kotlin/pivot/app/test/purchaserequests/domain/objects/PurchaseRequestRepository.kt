package pivot.app.test.purchaserequests.domain.objects

interface PurchaseRequestRepository {
    fun findById(id: Int): PurchaseRequest?
    fun save(purchaseRequest: PurchaseRequest)
}