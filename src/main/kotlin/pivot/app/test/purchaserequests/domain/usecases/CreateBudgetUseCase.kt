package pivot.app.test.purchaserequests.domain.usecases

import org.springframework.stereotype.Service
import pivot.app.test.purchaserequests.domain.objects.Budget
import pivot.app.test.purchaserequests.domain.objects.BudgetRepository
import pivot.app.test.purchaserequests.domain.objects.IdGenerator

data class CreateBudgetCommand(val companyId: Int)

@Service
class CreateBudgetUseCase(
    private val idGenerator: IdGenerator,
    private val budgetRepository: BudgetRepository
) {

    fun create(createBudgetCommand: CreateBudgetCommand): Budget {
        val id = idGenerator.getNext()
        val budget = Budget(
            id = id,
            companyId = createBudgetCommand.companyId,
            total = 500_000.0,
        )
        budgetRepository.save(budget)
        return budget
    }
}
