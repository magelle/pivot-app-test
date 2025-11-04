package pivot.app.test.purchaserequests.api

import org.springframework.web.bind.annotation.*
import pivot.app.test.purchaserequests.domain.usecases.CreateBudgetCommand
import pivot.app.test.purchaserequests.domain.usecases.CreateBudgetUseCase
import pivot.app.test.purchaserequests.domain.usecases.GetBudgetResponse
import pivot.app.test.purchaserequests.domain.usecases.GetBudgetUseCase

@RestController
@RequestMapping("/budgets")
class BudgetController(
    private val getBudgetUseCase: GetBudgetUseCase,
    private val createBudgetUseCase: CreateBudgetUseCase,
) {

    @PostMapping("/create")
    fun create(@RequestBody createCommand: CreateBudgetCommand): GetBudgetResponse {
        val budget = createBudgetUseCase.create(createCommand)
        return getBudgetUseCase.get(budget.id)!!
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: Int): GetBudgetResponse? =
        getBudgetUseCase.get(id)
}