package pivot.app.test.purchasesrequest

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jooq.JooqTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Import
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import pivot.app.test.purchaserequests.domain.adapter.PurchaseRequestRepository
import pivot.app.test.purchaserequests.domain.objects.PurchaseRequest
import pivot.app.test.purchaserequests.domain.objects.Status
import java.time.LocalDateTime
import kotlin.test.assertEquals
import kotlin.test.assertNull

@JooqTest
@Import(PurchaseRequestRepository::class)
@Testcontainers
class PostgresPurchaseRequestRepositoryTest {
    @Autowired
    private lateinit var purchaseRequestRepository: PurchaseRequestRepository

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

    companion object {
        @Container
        @ServiceConnection
        val postgres: PostgreSQLContainer<*> = PostgreSQLContainer("postgres:latest")
    }
}
