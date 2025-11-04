package pivot.app.test.purchaserequests.api

import org.springframework.web.bind.annotation.*
import pivot.app.test.purchaserequests.domain.objects.PurchaseRequest
import pivot.app.test.purchaserequests.domain.usecases.*

@RestController
@RequestMapping("/")
class Controller(
    private val getPurchaseRequestUseCase: GetPurchaseRequestUseCase,
    private val createPurchaseRequestUseCase: CreatePurchaseRequestUseCase,
    private val approvePurchaseRequestUseCase: ApprovePurchaseRequestUseCase,
    private val declinePurchaseRequestUseCase: DeclinePurchaseRequestUseCase,
) {

    @PostMapping("/purchase-request/create")
    fun create(@RequestBody createCommand: CreatePurchaseRequestCommand): PurchaseRequest {
        return createPurchaseRequestUseCase.create(createCommand)
    }

    @GetMapping("/purchase-request/{id}")
    fun get(@PathVariable id: Int): PurchaseRequest? =
        getPurchaseRequestUseCase.get(id)

    @PutMapping("/purchase-request/{id}/approve")
    fun approve(@PathVariable id: Int): PurchaseRequest? {
        approvePurchaseRequestUseCase.approve(id)
        return getPurchaseRequestUseCase.get(id)
    }

    @PutMapping("/purchase-request/{id}/decline")
    fun decline(@PathVariable id: Int): PurchaseRequest? {
        declinePurchaseRequestUseCase.decline(id)
        return getPurchaseRequestUseCase.get(id)
    }

}