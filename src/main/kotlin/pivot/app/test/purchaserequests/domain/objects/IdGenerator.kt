package pivot.app.test.purchaserequests.domain.objects

interface IdGenerator {
    fun getNext(): Int
}