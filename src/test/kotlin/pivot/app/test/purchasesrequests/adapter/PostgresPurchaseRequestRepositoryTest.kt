package pivot.app.test.purchasesrequests.adapter

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jooq.JooqTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Import
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import pivot.app.test.purchaserequests.adapter.PostgresPurchaseRequestRepository
import pivot.app.test.purchaserequests.domain.objects.PurchaseRequest
import pivot.app.test.purchaserequests.domain.objects.Status
import java.time.LocalDateTime
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNull

@JooqTest
@Import(PostgresPurchaseRequestRepository::class)
@Testcontainers
class PostgresPurchaseRequestRepositoryTest {
    @Autowired
    private lateinit var purchaseRequestRepository: PostgresPurchaseRequestRepository

    @Test
    fun findById_whenMissing_returnsNull() {
        val result = purchaseRequestRepository.findById(42)
        assertNull(result)
    }

    @Test
    fun save_then_find_returnsPersistedEntity() {
        val now = LocalDateTime.of(2024, 1, 2, 3, 4, 5)
        val pr = PurchaseRequest(
            id = 1,
            companyId = 77,
            description = "Laptop",
            amount = 1234.56,
            issueDate = now,
            status = Status.SUBMITTED
        )
        purchaseRequestRepository.save(pr)

        val loaded = purchaseRequestRepository.findById(1)
        assertEquals(1, loaded?.id)
        assertEquals(77, loaded?.companyId)
        assertEquals("Laptop", loaded?.description)
        assertEquals(1234.56, loaded?.amount)
        assertEquals(now, loaded?.issueDate)
        assertEquals(Status.SUBMITTED, loaded?.status)
    }

    @Test
    fun save_twice_with_same_id_updates_row() {
        val initial = PurchaseRequest(
            id = 5,
            companyId = 1,
            description = "Mouse",
            amount = 10.0,
            issueDate = LocalDateTime.of(2024, 5, 6, 7, 8, 9),
            status = Status.SUBMITTED
        )
        purchaseRequestRepository.save(initial)

        val updated = initial.copy(
            description = "Ergonomic Mouse",
            amount = 19.99,
            status = Status.APPROVED
        )
        purchaseRequestRepository.save(updated)

        val loaded = purchaseRequestRepository.findById(5)
        assertEquals("Ergonomic Mouse", loaded?.description)
        assertEquals(19.99, loaded?.amount)
        assertEquals(Status.APPROVED, loaded?.status)
    }

    @Test
    fun `find by companyId and status when no matches`() {
        // when
        val results = purchaseRequestRepository.findByCompanyIdAndStatus(999, Status.SUBMITTED)
        // then
        assertEquals(0, results.size)
    }

    @Test
    fun `find by companyId and status returns only matching budgets`() {
        val now = LocalDateTime.of(2024, 6, 1, 12, 0, 0)
        // Prepare different companies and statuses
        val a1 = PurchaseRequest(
            id = 101,
            companyId = 10,
            description = "A1",
            amount = 100.0,
            issueDate = now,
            status = Status.SUBMITTED
        )
        val a2 = a1.copy(id = 102, description = "A2", status = Status.APPROVED)
        val a3 = a1.copy(id = 103, description = "A3", status = Status.SUBMITTED)
        val b1 = a1.copy(id = 201, companyId = 20, description = "B1", status = Status.SUBMITTED)
        // persist
        purchaseRequestRepository.save(a1)
        purchaseRequestRepository.save(a2)
        purchaseRequestRepository.save(a3)
        purchaseRequestRepository.save(b1)

        // when: query for company 10 with SUBMITTED status
        val results = purchaseRequestRepository.findByCompanyIdAndStatus(10, Status.SUBMITTED)

        // then: should return only a1 and a3, in any order
        assertEquals(2, results.size)
        assertContains(results, a1)
        assertContains(results, a3)
    }

    companion object {
        @Container
        @ServiceConnection
        val postgres: PostgreSQLContainer<*> = PostgreSQLContainer("postgres:latest")
    }
}