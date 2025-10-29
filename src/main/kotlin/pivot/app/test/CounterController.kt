package pivot.app.test

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/")
class CounterController(
    private val counter: Counter
) {

    @GetMapping("/counter")
    fun getCounter() = this.counter.value()

    @PostMapping("/counter/inc")
    fun incCounter(): Int {
        this.counter.inc()
        return this.counter.value()
    }

    @PostMapping("/counter/dec")
    fun decCounter(): Int {
        this.counter.dec()
        return this.counter.value()
    }

}
