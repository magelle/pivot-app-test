package pivot.app.test.purchaserequests.domain.objects

interface BudgetRepository {
    fun save(budget: Budget)
    fun findById(id: Int): Budget?
}