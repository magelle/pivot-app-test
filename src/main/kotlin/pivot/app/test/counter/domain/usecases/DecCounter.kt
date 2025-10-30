package pivot.app.test.counter.domain.usecases

import org.springframework.stereotype.Service
import pivot.app.test.counter.domain.objects.CounterRepository
import pivot.app.test.counter.domain.objects.Counter

@Service
class DecCounter(private val counterRepo: CounterRepository) {
    fun dec(id: Int): Counter {
        val counter = this.counterRepo.findById(id) ?: Counter(0)
        counter.dec()
        this.counterRepo.save(id, counter)
        return counter
    }
}