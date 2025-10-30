package pivot.app.test.counter.presentation

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pivot.app.test.counter.domain.objects.Counter
import pivot.app.test.counter.domain.usecases.CreateCounter
import pivot.app.test.counter.domain.usecases.DecCounter
import pivot.app.test.counter.domain.usecases.GetCounter
import pivot.app.test.counter.domain.usecases.IncCounter

@RestController
@RequestMapping("/")
class CounterController(
    private val createCounter: CreateCounter,
    private val getCounter: GetCounter,
    private val incCounter: IncCounter,
    private val decCounter: DecCounter,
) {

    @PutMapping("/counter/create")
    fun createCounter(): Int {
        return this.createCounter.create()
    }

    @GetMapping("/counter/{id}")
    fun getCounter(@PathVariable id: Int): Int? =
        this.getCounter.get(id)?.value()

    @PostMapping("/counter/{id}/inc")
    fun incCounter(@PathVariable id: Int): Int {
        return this.incCounter.inc(id).value()
    }

    @PostMapping("/counter/{id}/dec")
    fun decCounter(@PathVariable id: Int): Int {
        return this.decCounter.dec(id).value()
    }

}