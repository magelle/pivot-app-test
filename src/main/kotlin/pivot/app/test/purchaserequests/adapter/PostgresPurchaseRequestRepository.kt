package pivot.app.test.purchaserequests.adapter

import com.sivalabs.bookmarks.jooq.tables.PurchaseRequest.PURCHASE_REQUEST
import com.sivalabs.bookmarks.jooq.tables.records.PurchaseRequestRecord
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import pivot.app.test.purchaserequests.domain.objects.PurchaseRequest
import pivot.app.test.purchaserequests.domain.objects.PurchaseRequestRepository
import pivot.app.test.purchaserequests.domain.objects.Status

@Repository
class PostgresPurchaseRequestRepository(private val dsl: DSLContext) : PurchaseRequestRepository {
    override fun findById(id: Int): PurchaseRequest? {
        val result: PurchaseRequestRecord? = dsl.fetchOne(PURCHASE_REQUEST, PURCHASE_REQUEST.ID.eq(id))
        return result?.let { mapToBusinessObject(it) }
    }

    override fun save(purchaseRequest: PurchaseRequest) {
        val pr = PurchaseRequestRecord(
            purchaseRequest.id,
            purchaseRequest.companyId,
            purchaseRequest.description,
            purchaseRequest.amount,
            purchaseRequest.issueDate,
            purchaseRequest.status.toString(),
        )
        if (dsl.fetchExists(PURCHASE_REQUEST, PURCHASE_REQUEST.ID.eq(purchaseRequest.id)))
            dsl.executeUpdate(pr)
        else
            dsl.executeInsert(pr)
    }

    override fun findByCompanyIdAndStatus(
        companyId: Int,
        status: Status
    ): List<PurchaseRequest> {
        val result = dsl.fetch(
            PURCHASE_REQUEST,
            PURCHASE_REQUEST.COMPANYID.eq(companyId).and(
                PURCHASE_REQUEST.STATUS.eq(
                    status.toString()
                )
            )
        )
        return result.map { mapToBusinessObject(it) }
    }

    private fun mapToBusinessObject(result: PurchaseRequestRecord): PurchaseRequest = PurchaseRequest(
        result.id,
        result.companyid,
        result.description,
        result.amount,
        result.issuedate,
        Status.valueOf(result.status)
    )

}