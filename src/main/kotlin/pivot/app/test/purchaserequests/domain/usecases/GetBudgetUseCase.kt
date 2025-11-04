package pivot.app.test.purchaserequests.domain.usecases

import org.springframework.stereotype.Service
import pivot.app.test.purchaserequests.domain.objects.BudgetRepository
import pivot.app.test.purchaserequests.domain.objects.PurchaseRequestRepository
import pivot.app.test.purchaserequests.domain.objects.Status

@Service
class GetBudgetUseCase(
    private val budgetRepository: BudgetRepository,
    private val purchaseRequestRepository: PurchaseRequestRepository
) {
    fun get(id: Int): GetBudgetResponse? {
        val budget = budgetRepository.findById(id)
        return budget?.let {
            GetBudgetResponse(
                id = it.id,
                companyId = it.companyId,
                total = it.total,
                currentExpenditure = purchaseRequestRepository
                    .findByCompanyIdAndStatus(it.companyId, Status.APPROVED)
                    .sumOf { it.amount },
            )
        }
    }

}

data class GetBudgetResponse(
    val id: Int,
    val companyId: Int,
    val total: Double,
    val currentExpenditure: Double,
)
