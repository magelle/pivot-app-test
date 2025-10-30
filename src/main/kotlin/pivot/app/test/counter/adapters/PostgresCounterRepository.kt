package pivot.app.test.counter.adapters

import com.sivalabs.bookmarks.jooq.tables.Counter.COUNTER
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import pivot.app.test.counter.domain.objects.Counter
import pivot.app.test.counter.domain.objects.CounterRepository

@Repository
class PostgresCounterRepository(private val dsl: DSLContext): CounterRepository {
    override fun findById(id: Int): Counter? {
        val optionalRecord = dsl
            .selectFrom(COUNTER)
            .where(COUNTER.ID.eq(id))
            .fetchOptional()

        if (optionalRecord.isEmpty) return null

        val record = optionalRecord.get()
        val value = record.value

        return value?.let { Counter(it) }
    }

    override fun save(id: Int, counter: Counter) {
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