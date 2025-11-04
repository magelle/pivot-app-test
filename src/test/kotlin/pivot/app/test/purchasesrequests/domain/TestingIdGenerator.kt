package pivot.app.test.purchasesrequests.domain

import pivot.app.test.purchaserequests.domain.objects.IdGenerator

class TestingIdGenerator: IdGenerator {
    private var nextId: Int = 1

    fun setNextId(next: Int) {
        this.nextId = next
    }

    override fun getNext(): Int {
        return nextId
    }

}
