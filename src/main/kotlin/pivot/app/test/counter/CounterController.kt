package pivot.app.test.counter

import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.time.ZoneOffset

@RestController
@RequestMapping("/")
class CounterController(
    private val counterRepo: CounterRepository
) {

    @PutMapping("/counter/create")
    fun getCounter(): Int {
        val counter = Counter(0)
        val id = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC).toInt()
        this.counterRepo.save(id, counter)
        return id
    }

    @GetMapping("/counter/{id}")
    fun getCounter(@PathVariable id: Int): Int? =
        this.counterRepo.findCounterById(id)?.value()

    @PostMapping("/counter/{id}/inc")
    fun incCounter(@PathVariable id: Int): Int {
        val counter = this.counterRepo.findCounterById(id) ?: Counter(0)
        counter.inc()
        this.counterRepo.save(id, counter)
        return counter.value()
    }

    @PostMapping("/counter/{id}/dec")
    fun decCounter(@PathVariable id: Int): Int {
        val counter = this.counterRepo.findCounterById(id) ?: Counter(0)
        counter.dec()
        this.counterRepo.save(id, counter)
        return counter.value()
    }

}
