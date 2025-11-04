package pivot.app.test.purchaserequests.adapter

import com.sivalabs.bookmarks.jooq.Tables.BUDGET
import com.sivalabs.bookmarks.jooq.tables.records.BudgetRecord
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import pivot.app.test.purchaserequests.domain.objects.Budget
import pivot.app.test.purchaserequests.domain.objects.BudgetRepository

@Repository
class PostgresBudgetRepository(private val dsl: DSLContext) : BudgetRepository {

    override fun findById(id: Int): Budget? {
        val result: BudgetRecord? = dsl.fetchOne(BUDGET, BUDGET.ID.eq(id))
        return result?.let {
            Budget(
                id = it.id,
                companyId = it.companyid,
                total = it.total,
            )
        }
    }

    override fun save(budget: Budget) {
        val pr = BudgetRecord(
            budget.id,
            budget.companyId,
            budget.total,
        )
        if (dsl.fetchExists(BUDGET, BUDGET.ID.eq(budget.id)))
            dsl.executeUpdate(pr)
        else
            dsl.executeInsert(pr)
    }

}