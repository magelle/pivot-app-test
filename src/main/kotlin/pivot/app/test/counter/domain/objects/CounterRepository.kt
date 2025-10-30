package pivot.app.test.counter.domain.objects

interface CounterRepository {
    fun findById(id: Int): Counter?
    fun save(id: Int, counter: Counter)
}