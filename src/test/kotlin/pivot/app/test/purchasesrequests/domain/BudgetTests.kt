package pivot.app.test.purchasesrequests.domain

import org.junit.Test
import pivot.app.test.purchaserequests.domain.objects.PurchaseRequest
import pivot.app.test.purchaserequests.domain.objects.Status
import pivot.app.test.purchaserequests.domain.usecases.CreateBudgetCommand
import pivot.app.test.purchaserequests.domain.usecases.CreateBudgetUseCase
import pivot.app.test.purchaserequests.domain.usecases.GetBudgetResponse
import pivot.app.test.purchaserequests.domain.usecases.GetBudgetUseCase
import java.time.LocalDateTime
import kotlin.test.assertEquals

class BudgetTests {

    val idGenerator = TestingIdGenerator()
    val purchaseRequestRepository = InMemoryPurchaseRequestRepository()
    val budgetRepository = InMemoryBudgetRepository()
    val createBudgetUseCase = CreateBudgetUseCase(idGenerator, budgetRepository)
    val getBudgetUseCase = GetBudgetUseCase(budgetRepository, purchaseRequestRepository)

    @Test
    fun `should create a budget `() {
        idGenerator.setNextId(24)
        val createBudgetCommand = CreateBudgetCommand(companyId = 12)

        createBudgetUseCase.create(createBudgetCommand)

        val saved = getBudgetUseCase.get(24)

        assertEquals(
            GetBudgetResponse(
                id = 24,
                companyId = 12,
                total = 500000.0,
                currentExpenditure = .0,
            ), saved
        )
    }

    @Test
    fun `should aggregate current expenditure`() {
        idGenerator.setNextId(35)

        purchaseRequestRepository.save(
            PurchaseRequest(
                id = 1,
                companyId = 12,
                description = "My purchase request",
                amount = 10.00,
                issueDate = LocalDateTime.now(),
                status = Status.APPROVED,
            )
        )
        purchaseRequestRepository.save(
            PurchaseRequest(
                id = 2,
                companyId = 12,
                description = "My purchase request",
                amount = 20.00,
                issueDate = LocalDateTime.now(),
                status = Status.APPROVED,
            )
        )

        createBudgetUseCase.create(CreateBudgetCommand(companyId = 12))

        val saved = getBudgetUseCase.get(35)

        assertEquals(30.0, saved?.currentExpenditure)
    }

    @Test
    fun `should only aggregate request of the company as the current expenditure`() {
        idGenerator.setNextId(36)

        purchaseRequestRepository.save(
            PurchaseRequest(
                id = 1,
                companyId = 12,
                description = "My purchase request",
                amount = 10.00,
                issueDate = LocalDateTime.now(),
                status = Status.APPROVED,
            )
        )
        purchaseRequestRepository.save(
            PurchaseRequest(
                id = 2,
                companyId = 20,
                description = "My purchase request",
                amount = 20.00,
                issueDate = LocalDateTime.now(),
                status = Status.APPROVED,
            )
        )

        createBudgetUseCase.create(CreateBudgetCommand(companyId = 12))

        val saved = getBudgetUseCase.get(36)

        assertEquals(10.0, saved?.currentExpenditure)
    }

    @Test
    fun `should only aggregate approved requests as the current expenditure`() {
        idGenerator.setNextId(37)

        purchaseRequestRepository.save(
            PurchaseRequest(
                id = 1,
                companyId = 12,
                description = "My purchase request",
                amount = 10.00,
                issueDate = LocalDateTime.now(),
                status = Status.APPROVED,
            )
        )
        purchaseRequestRepository.save(
            PurchaseRequest(
                id = 2,
                companyId = 12,
                description = "My purchase request",
                amount = 20.00,
                issueDate = LocalDateTime.now(),
                status = Status.SUBMITTED,
            )
        )
        purchaseRequestRepository.save(
            PurchaseRequest(
                id = 2,
                companyId = 12,
                description = "My purchase request",
                amount = 20.00,
                issueDate = LocalDateTime.now(),
                status = Status.DECLINE,
            )
        )

        createBudgetUseCase.create(CreateBudgetCommand(companyId = 12))

        val saved = getBudgetUseCase.get(37)

        assertEquals(10.0, saved?.currentExpenditure)
    }
}