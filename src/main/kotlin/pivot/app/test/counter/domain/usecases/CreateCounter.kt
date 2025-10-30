package pivot.app.test.counter.domain.usecases

import org.springframework.stereotype.Service
import pivot.app.test.counter.domain.objects.CounterRepository
import pivot.app.test.counter.domain.objects.Counter
import java.time.LocalDateTime
import java.time.ZoneOffset

@Service
class CreateCounter(private val counterRepo: CounterRepository) {
    fun create(): Int {
        val counter = Counter()
        val id = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC).toInt()
        this.counterRepo.save(id, counter)
        return id
    }
}