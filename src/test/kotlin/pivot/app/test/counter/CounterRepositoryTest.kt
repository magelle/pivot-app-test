package pivot.app.test.counter

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jooq.JooqTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Import
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import pivot.app.test.counter.domain.objects.CounterRepository
import pivot.app.test.counter.domain.objects.Counter
import kotlin.test.assertEquals
import kotlin.test.assertNull

@JooqTest
@Import(CounterRepository::class)
@Testcontainers
class CounterRepositoryTest() {
    @Autowired
    private lateinit var counterRepository: CounterRepository

    @Test
    fun findUserNameById() {
        val counter: Counter? = counterRepository.findById(1)
        assertNull(counter)
    }

    @Test
    fun saveAndFindCounter() {
        val toSave = Counter(5)
        counterRepository.save(2, toSave)
        val loaded = counterRepository.findById(2)
        assertEquals(5, loaded?.value())
    }

    @Test
    fun saveThenUpdatedCounter() {
        counterRepository.save(2, Counter(5))
        counterRepository.save(2, Counter(17))
        val loaded = counterRepository.findById(2)
        assertEquals(17, loaded?.value())
    }

    companion object {
        @Container
        @ServiceConnection
        val postgres: PostgreSQLContainer<*> = PostgreSQLContainer("postgres:latest")
    }
}
