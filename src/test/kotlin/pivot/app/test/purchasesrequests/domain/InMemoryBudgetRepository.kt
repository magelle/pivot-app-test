package pivot.app.test.purchasesrequests.domain

import pivot.app.test.purchaserequests.domain.objects.Budget
import pivot.app.test.purchaserequests.domain.objects.BudgetRepository

class InMemoryBudgetRepository: BudgetRepository {
    private val budgets = ArrayList<Budget>()

    override fun save(budget: Budget) {
        budgets.add(budget)
    }

    override fun findById(id: Int): Budget? {
        return budgets.find { it.id == id }?.let { return it }
    }

}
