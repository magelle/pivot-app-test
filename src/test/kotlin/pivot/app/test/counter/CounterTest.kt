package pivot.app.test.counter

import org.junit.Test
import kotlin.test.assertEquals

class CounterTest {

    @Test
    fun `Increment counter should add one`() {
        val counter = Counter()

        counter.inc()

        assertEquals(1, counter.value())
    }

    @Test
    fun `Decrement counter should remove one`() {
        val counter = Counter(10)

        counter.dec()

        assertEquals(9, counter.value())
    }

}