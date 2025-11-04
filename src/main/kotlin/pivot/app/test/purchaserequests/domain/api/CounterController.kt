package pivot.app.test.purchaserequests.domain.api

import org.springframework.web.bind.annotation.*
import pivot.app.test.purchaserequests.domain.usecases.CreatePurchaseRequestUseCase
import pivot.app.test.purchaserequests.domain.usecases.GetPurchaseRequestUseCase

//@RestController
//@RequestMapping("/")
class Controller(
    private val getPurchaseRequestUseCase: GetPurchaseRequestUseCase,
    private val createPurchaseRequestUseCase: CreatePurchaseRequestUseCase,
    //private val approvePurchaseRequestUseCase: CreatePurchaseRequestUseCase,
    //private val incCounter: IncCounter,
) {

    @PutMapping("/purchase-request/create")
    fun createCounter(): Int {
        TODO()
    }

    @GetMapping("/counter/{id}")
    fun getCounter(@PathVariable id: Int): Int? =
        TODO()

    @PostMapping("/counter/{id}/inc")
    fun incCounter(@PathVariable id: Int): Int {
        TODO()
    }

    @PostMapping("/counter/{id}/dec")
    fun decCounter(@PathVariable id: Int): Int {
        TODO()
    }

}