package pivot.app.test

import org.jooq.DSLContext
import org.springframework.stereotype.Repository


@Repository
class CounterRepository(private val dsl: DSLContext) {
    fun findCounterById(id: Long): Counter? {
        val optionalRecord = dsl.resultQuery("select * from counter where id = ?", id)
            .fetchOptional()
        if (optionalRecord.isEmpty) return null

        val record = optionalRecord.get()
        val value = record.get("value", Long::class.java)

        return value?.let { Counter(it.toInt()) }
    }

    fun save(counter: Counter): Long {
        val record = dsl.resultQuery("insert into counter (value) values (?) returning id", counter.value())
            .fetchOne()
        val id = record?.get("id", Long::class.java)
        return id ?: throw IllegalStateException("Failed to insert counter")
    }
}
