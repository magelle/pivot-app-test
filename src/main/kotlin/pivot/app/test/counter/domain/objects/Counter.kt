package pivot.app.test.counter.domain.objects

class Counter(
    private var counter: Int = 0
) {


    fun inc() {
        this.counter = this.counter.inc()
    }

    fun dec() {
        this.counter = this.counter.dec()
    }

    fun value() = this.counter
}