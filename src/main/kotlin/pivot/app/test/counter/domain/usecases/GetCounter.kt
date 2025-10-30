package pivot.app.test.counter.domain.usecases

import org.springframework.stereotype.Service
import pivot.app.test.counter.domain.objects.CounterRepository
import pivot.app.test.counter.domain.objects.Counter

@Service
class GetCounter(private val counterRepo: CounterRepository) {
    fun get(id: Int): Counter? = this.counterRepo.findById(id)
}