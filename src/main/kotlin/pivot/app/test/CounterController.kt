package pivot.app.test

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/")
class CounterController(
    private val counterRepo: CounterRepository
) {

    @GetMapping("/counter")
    fun getCounter() = this.counterRepo.findCounterById(1) ?: Counter(0)

    @PostMapping("/counter/inc")
    fun incCounter(): Int {
        val counter = this.counterRepo.findCounterById(1) ?: Counter(0)
        counter.inc()
        this.counterRepo.save(1, counter)
        return counter.value()
    }

    @PostMapping("/counter/dec")
    fun decCounter(): Int {
        val counter = this.counterRepo.findCounterById(1) ?: Counter(0)
        counter.dec()
        this.counterRepo.save(1, counter)
        return counter.value()
    }

}
