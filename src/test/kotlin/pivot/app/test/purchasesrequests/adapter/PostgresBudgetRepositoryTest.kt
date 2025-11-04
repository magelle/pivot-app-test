package pivot.app.test.purchasesrequests.adapter

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jooq.JooqTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Import
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import pivot.app.test.purchaserequests.adapter.PostgresBudgetRepository
import pivot.app.test.purchaserequests.domain.objects.Budget
import kotlin.test.assertEquals
import kotlin.test.assertNull

@JooqTest
@Import(PostgresBudgetRepository::class)
@Testcontainers
class PostgresBudgetRepositoryTest {
    @Autowired
    private lateinit var budgetRepository: PostgresBudgetRepository

    @Test
    fun findById_whenMissing_returnsNull() {
        val result = budgetRepository.findById(4242)
        assertNull(result)
    }

    @Test
    fun `should save a budget`() {
        val budget = Budget(
            id = 1,
            companyId = 77,
            total = 500_000.0,
        )
        budgetRepository.save(budget)

        val loaded = budgetRepository.findById(1)
        assertEquals(budget, loaded)
    }

    @Test
    fun `save twice should update`() {
        val initial = Budget(
            id = 5,
            companyId = 10,
            total = 1000.0,
        )
        budgetRepository.save(initial)

        val updated = Budget(
            id = 5,
            companyId = 11, // change company id
            total = 2000.5, // and total
        )
        budgetRepository.save(updated)

        val loaded = budgetRepository.findById(5)
        assertEquals(updated, loaded)
    }

    companion object {
        @Container
        @ServiceConnection
        val postgres: PostgreSQLContainer<*> = PostgreSQLContainer("postgres:latest")
    }
}
