package pivot.app.test

import com.sivalabs.bookmarks.jooq.tables.Counter.COUNTER
import org.jooq.DSLContext
import org.springframework.stereotype.Repository


@Repository
class CounterRepository(private val dsl: DSLContext) {
    fun findCounterById(id: Int): Counter? {
        val optionalRecord = dsl
            .selectFrom(COUNTER)
            .where(COUNTER.ID.eq(id))
            .fetchOptional()

        if (optionalRecord.isEmpty) return null

        val record = optionalRecord.get()
        val value = record.value

        return value?.let { Counter(it) }
    }

    fun save(id: Int, counter: Counter) {
        if (dsl.fetchExists(COUNTER, COUNTER.ID.eq(id)))
            dsl.update(COUNTER)
                .set(COUNTER.VALUE, counter.value())
                .where(COUNTER.ID.eq(id))
                .execute()
        else
            dsl.insertInto(COUNTER, COUNTER.ID, COUNTER.VALUE)
                .values(id, counter.value())
                .execute()
    }
}
