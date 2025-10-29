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

        return value?.let { Counter(it.toInt()) }
    }

    fun save(counter: Counter): Int {
        val record =
            dsl.insertInto(COUNTER, COUNTER.VALUE)
                .values(counter.value().toLong())
                .returning(COUNTER.ID)
                .fetchOne()
        val id = record?.id
        return id ?: throw IllegalStateException("Failed to insert counter")
    }
}
