package zzame.commerce

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import zzame.commerce.cart.entity.Cart
import zzame.commerce.order.entity.Order
import zzame.commerce.order.repository.OrderRepository
import zzame.commerce.product.entity.Product
import zzame.commerce.product.entity.Seller
import zzame.commerce.product.repository.ProductRepository
import zzame.commerce.product.repository.SellerRepository
import zzame.commerce.settlement.repository.SettlementRepository
import zzame.commerce.settlement.service.SettlementService
import java.time.LocalDate
import java.time.LocalDateTime

@SpringBootTest
@Transactional
class SettlementServiceTests {

    @Autowired
    lateinit var settlementService: SettlementService

    @Autowired
    lateinit var settlementRepository: SettlementRepository

    @Autowired
    lateinit var sellerRepository: SellerRepository

    @Autowired
    lateinit var productRepository: ProductRepository

    @Autowired
    lateinit var orderRepository: OrderRepository

    @Test
    fun `calculate weekly settlements aggregates paid orders by seller`() {
        val weekStart = LocalDate.of(2026, 4, 6)
        val sellerOne = sellerRepository.save(Seller.create("셀러 원", "seller1@example.com"))
        val sellerTwo = sellerRepository.save(Seller.create("셀러 투", "seller2@example.com"))

        createPaidOrder(
            seller = sellerOne,
            productName = "키보드",
            productPrice = 50000L,
            optionName = "적축",
            additionalPrice = 5000L,
            quantity = 2,
            paidAt = weekStart.atTime(10, 0),
        )
        createPaidOrder(
            seller = sellerTwo,
            productName = "모니터",
            productPrice = 200000L,
            optionName = "27인치",
            additionalPrice = 10000L,
            quantity = 1,
            paidAt = weekStart.atTime(15, 0),
        )

        val settlements = settlementService.calculateWeeklySettlements(weekStart)

        assertThat(settlements).hasSize(2)

        val sellerOneSettlement = settlements.first { it.seller.id == sellerOne.id }
        assertThat(sellerOneSettlement.grossSales).isEqualTo(110000L)
        assertThat(sellerOneSettlement.advertisingCost).isEqualTo(3300L)
        assertThat(sellerOneSettlement.payoutAmount).isEqualTo(106700L)
        assertThat(sellerOneSettlement.totalQuantity).isEqualTo(2L)
        assertThat(sellerOneSettlement.orderCount).isEqualTo(1L)

        val sellerTwoSettlement = settlements.first { it.seller.id == sellerTwo.id }
        assertThat(sellerTwoSettlement.grossSales).isEqualTo(210000L)
        assertThat(sellerTwoSettlement.advertisingCost).isEqualTo(6300L)
        assertThat(sellerTwoSettlement.payoutAmount).isEqualTo(203700L)

        assertThat(settlementRepository.findAll()).hasSize(2)
    }

    @Test
    fun `calculate payout amount subtracts advertising cost`() {
        val advertisingCost = settlementService.calculateAdvertisingCost(100000L)
        val payoutAmount = settlementService.calculatePayoutAmount(100000L, advertisingCost)

        assertThat(advertisingCost).isEqualTo(3000L)
        assertThat(payoutAmount).isEqualTo(97000L)
    }

    private fun createPaidOrder(
        seller: Seller,
        productName: String,
        productPrice: Long,
        optionName: String,
        additionalPrice: Long,
        quantity: Int,
        paidAt: LocalDateTime,
    ) {
        val product = productRepository.save(
            Product.create(
                seller = seller,
                name = productName,
                price = productPrice,
                description = null,
            ).apply {
                addOption(
                    name = optionName,
                    additionalPrice = additionalPrice,
                    stockQuantity = 10,
                )
            },
        )
        val cart = Cart.create(customerId = seller.id + 1000)
        val cartItem = cart.addItem(
            product = product,
            productOption = product.options.first(),
            quantity = quantity,
        )
        val order = Order.create(
            customerId = cart.customerId,
            recipientName = "정산고객",
            phone = "010-9999-9999",
            address = "서울시 마포구",
            detailAddress = "1층",
            deliveryRequest = null,
            cartItems = listOf(cartItem),
        )

        order.markPaid(paidAt)
        orderRepository.save(order)
    }
}
