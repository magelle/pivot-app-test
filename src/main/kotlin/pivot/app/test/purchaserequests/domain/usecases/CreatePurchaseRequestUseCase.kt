package pivot.app.test.purchaserequests.domain.usecases

import org.springframework.stereotype.Service
import pivot.app.test.purchaserequests.domain.objects.*

data class CreatePurchaseRequestCommand(
    val companyId: Int,
    val description: String,
    val amount: Double,
)

@Service
class CreatePurchaseRequestUseCase(
    private val clock: Clock,
    private val idGenerator: IdGenerator,
    private val alertSystem: AlertSystem,
    private val purchaseRequestRepository: PurchaseRequestRepository
) {
    fun create(createPurchaseRequest: CreatePurchaseRequestCommand): PurchaseRequest {
        val id = idGenerator.getNext()
        val time = clock.getNow()
        val pr = PurchaseRequest(
            id = id,
            companyId = createPurchaseRequest.companyId,
            description = createPurchaseRequest.description,
            amount = createPurchaseRequest.amount,
            issueDate = time,
            status = Status.SUBMITTED,
        )
        this.purchaseRequestRepository.save(pr)
        if (pr.amount > 50000) {
            this.alertSystem.send(Alert(id, "Purchase request with id $id exceeds 50 000"))
        }
        return pr
    }
}
