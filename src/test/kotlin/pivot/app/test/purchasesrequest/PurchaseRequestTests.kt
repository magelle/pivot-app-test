package pivot.app.test.purchasesrequest

import org.junit.Test
import pivot.app.test.purchaserequests.domain.objects.Alert
import pivot.app.test.purchaserequests.domain.objects.PurchaseRequest
import pivot.app.test.purchaserequests.domain.objects.Status
import pivot.app.test.purchaserequests.domain.usecases.CreatePurchaseRequestCommand
import pivot.app.test.purchaserequests.domain.usecases.CreatePurchaseRequestUseCase
import pivot.app.test.purchaserequests.domain.usecases.GetPurchaseRequestUseCase
import java.time.LocalDateTime
import kotlin.test.assertEquals

class PurchaseRequestTests {

    val clock = TestingClock()
    val idGenerator = TestingIdGenerator()
    val alertSystem = TestingAlertSystem()
    val purchaseRequestRepository = InMemoryPurchaseRequestRepository()
    val createPurchaseRequest = CreatePurchaseRequestUseCase(clock, idGenerator, alertSystem, purchaseRequestRepository)

    val getPurchaseRequest = GetPurchaseRequestUseCase(purchaseRequestRepository)

    @Test
    fun `should receive a purchase request`() {
        val issueDate = LocalDateTime.now()
        clock.setNow(issueDate)
        idGenerator.setNextId(12)
        val createPurchaseRequestCommand = CreatePurchaseRequestCommand(
            companyId = 10,
            description = "My purchase request",
            amount = 10.00
        )

        val id = createPurchaseRequest.create(createPurchaseRequestCommand)

        val createdPurchaseRequest = getPurchaseRequest.get(id)

        assertEquals(
            PurchaseRequest(
                id = 12,
                companyId = 10,
                description = "My purchase request",
                amount = 10.00,
                status = Status.SUBMITTED,
                issueDate = issueDate
            ), createdPurchaseRequest
        )
    }

    @Test
    fun `should send an alert when purchase amount exceeds 50 000`() {
        val issueDate = LocalDateTime.now()
        clock.setNow(issueDate)
        idGenerator.setNextId(12)
        val createPurchaseRequestCommand = CreatePurchaseRequestCommand(
            companyId = 10,
            description = "My purchase request",
            amount = 50000.01
        )

        createPurchaseRequest.create(createPurchaseRequestCommand)

        alertSystem.assertAlertWasReceived(Alert(id = 12, description = "Purchase request with id 12 exceeds 50 000"))
    }

    @Test
    fun `should not send an alert when purchase amount is below 50 000`() {
        val issueDate = LocalDateTime.now()
        clock.setNow(issueDate)
        idGenerator.setNextId(12)
        val createPurchaseRequestCommand = CreatePurchaseRequestCommand(
            companyId = 10,
            description = "My purchase request",
            amount = 49999.99
        )

        createPurchaseRequest.create(createPurchaseRequestCommand)

        alertSystem.assertNoAlertWasReceived()
    }

}