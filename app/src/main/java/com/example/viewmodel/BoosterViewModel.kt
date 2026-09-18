package com.example.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.BoostOrder
import com.example.model.BoostStatus
import com.example.model.PaymentSubmission
import com.example.model.PlanType
import com.example.model.ServiceType
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

data class BoosterUiState(
    val currentPlan: PlanType = PlanType.FREE,
    val tiktokUrl: String = "",
    val isUrlSubmitted: Boolean = false,
    val selectedService: ServiceType = ServiceType.LIKES,
    val quantity: Int = 100,
    val isBoosting: Boolean = false,
    val boostProgress: Float = 0f,
    val boostStage: String = "",
    val deliveredCount: Int = 0,
    val freeDailyUsed: Boolean = false,
    val latestCompletedOrder: BoostOrder? = null,
    val orderHistory: List<BoostOrder> = emptyList(),
    // Checkout & Payment
    val showCheckoutSheet: Boolean = false,
    val checkoutPlan: PlanType = PlanType.VIP,
    val senderPhone: String = "",
    val transactionId: String = "",
    val proofImageUri: Uri? = null,
    val latestPaymentSubmission: PaymentSubmission? = null,
    val paymentMessage: String? = null,
    // SMM Mock Panel
    val smmEndpoint: String = "https://api.smm-tiktokservice.com/v2",
    val smmApiKey: String = "smm_live_prod_89fa14c77b21",
    val smmBalance: String = "$148.50 USD",
    val smmRecentApiLog: String = "Ready for order dispatches."
)

class BoosterViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(BoosterUiState())
    val uiState: StateFlow<BoosterUiState> = _uiState.asStateFlow()

    private var boostJob: Job? = null

    init {
        // Seed some realistic order history
        _uiState.update { state ->
            state.copy(
                orderHistory = listOf(
                    BoostOrder(
                        id = "ORD-7821",
                        targetUrl = "https://www.tiktok.com/@vibes.daily/video/7391827491",
                        serviceType = ServiceType.VIEWS,
                        quantity = 2500,
                        planType = PlanType.VIP,
                        status = BoostStatus.COMPLETED,
                        timestamp = System.currentTimeMillis() - 3600000 * 3
                    ),
                    BoostOrder(
                        id = "ORD-6540",
                        targetUrl = "https://www.tiktok.com/@creatorshub/video/7402847192",
                        serviceType = ServiceType.LIKES,
                        quantity = 500,
                        planType = PlanType.FREE,
                        status = BoostStatus.DELIVERED,
                        timestamp = System.currentTimeMillis() - 3600000 * 18
                    )
                )
            )
        }
    }

    fun onUrlChanged(newUrl: String) {
        _uiState.update { it.copy(tiktokUrl = newUrl.trim()) }
    }

    fun submitLink() {
        val url = _uiState.value.tiktokUrl
        if (url.isNotBlank()) {
            _uiState.update { it.copy(isUrlSubmitted = true) }
        }
    }

    fun selectService(service: ServiceType) {
        _uiState.update { state ->
            val maxAllowed = state.currentPlan.maxPerBoost
            val newQty = service.defaultQty.coerceAtMost(maxAllowed)
            state.copy(selectedService = service, quantity = newQty)
        }
    }

    fun setQuantity(qty: Int) {
        val maxAllowed = _uiState.value.currentPlan.maxPerBoost
        val clamped = qty.coerceIn(10, maxAllowed)
        _uiState.update { it.copy(quantity = clamped) }
    }

    fun startBoost() {
        val state = _uiState.value
        if (state.isBoosting) return

        if (state.currentPlan == PlanType.FREE && state.freeDailyUsed) {
            // Suggest upgrade
            openCheckout(PlanType.VIP)
            return
        }

        boostJob?.cancel()
        boostJob = viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isBoosting = true,
                    boostProgress = 0.05f,
                    deliveredCount = 0,
                    boostStage = "Connecting to TikTok API & validating target link..."
                )
            }

            delay(700)
            _uiState.update {
                it.copy(
                    boostProgress = 0.22f,
                    boostStage = "Allocating SMM high-retention server nodes..."
                )
            }

            delay(900)
            val totalQty = _uiState.value.quantity
            val steps = 10
            for (i in 1..steps) {
                delay(300)
                val currentProgress = 0.25f + (0.65f * (i.toFloat() / steps))
                val delivered = (totalQty * (i.toFloat() / steps)).toInt()
                _uiState.update {
                    it.copy(
                        boostProgress = currentProgress,
                        deliveredCount = delivered,
                        boostStage = "Delivering organic ${state.selectedService.unit} ($delivered / $totalQty)..."
                    )
                }
            }

            _uiState.update {
                it.copy(
                    boostProgress = 0.96f,
                    boostStage = "Syncing delivery report & finalizing FYP metrics..."
                )
            }
            delay(600)

            val newOrder = BoostOrder(
                id = "ORD-" + UUID.randomUUID().toString().substring(0, 5).uppercase(),
                targetUrl = state.tiktokUrl.ifBlank { "https://www.tiktok.com/@user/video/demo" },
                serviceType = state.selectedService,
                quantity = totalQty,
                planType = state.currentPlan,
                status = BoostStatus.COMPLETED,
                timestamp = System.currentTimeMillis()
            )

            _uiState.update {
                it.copy(
                    isBoosting = false,
                    boostProgress = 1.0f,
                    boostStage = "Boost successfully delivered!",
                    deliveredCount = totalQty,
                    latestCompletedOrder = newOrder,
                    orderHistory = listOf(newOrder) + it.orderHistory,
                    freeDailyUsed = if (state.currentPlan == PlanType.FREE) true else it.freeDailyUsed,
                    smmRecentApiLog = "Dispatched order #${newOrder.id} to SMM Node cluster (Service ID: ${state.selectedService.name}, Qty: $totalQty, Status: Completed)"
                )
            }
        }
    }

    fun dismissCompletionDialog() {
        _uiState.update { it.copy(latestCompletedOrder = null) }
    }

    // Checkout & Payment Logic
    fun openCheckout(plan: PlanType) {
        _uiState.update {
            it.copy(
                showCheckoutSheet = true,
                checkoutPlan = plan,
                senderPhone = "",
                transactionId = "",
                proofImageUri = null
            )
        }
    }

    fun closeCheckout() {
        _uiState.update { it.copy(showCheckoutSheet = false) }
    }

    fun onSenderPhoneChanged(phone: String) {
        _uiState.update { it.copy(senderPhone = phone) }
    }

    fun onTransactionIdChanged(tid: String) {
        _uiState.update { it.copy(transactionId = tid) }
    }

    fun onProofImageSelected(uri: Uri?) {
        _uiState.update { it.copy(proofImageUri = uri) }
    }

    fun submitPaymentProof() {
        val state = _uiState.value
        if (state.transactionId.isBlank() || state.senderPhone.isBlank()) {
            _uiState.update { it.copy(paymentMessage = "Please enter Sender Phone and JazzCash Transaction ID (TID)") }
            return
        }

        val submission = PaymentSubmission(
            id = "JC-" + UUID.randomUUID().toString().substring(0, 6).uppercase(),
            planType = state.checkoutPlan,
            amountPkr = state.checkoutPlan.rawPkr,
            senderPhone = state.senderPhone,
            transactionId = state.transactionId,
            screenshotUri = state.proofImageUri?.toString(),
            status = "Verified & Active"
        )

        // Upgrade user plan upon JazzCash submission
        _uiState.update {
            it.copy(
                showCheckoutSheet = false,
                currentPlan = state.checkoutPlan,
                latestPaymentSubmission = submission,
                freeDailyUsed = false,
                paymentMessage = null
            )
        }
    }

    fun dismissPaymentConfirmation() {
        _uiState.update { it.copy(latestPaymentSubmission = null) }
    }

    fun switchPlanDirectly(plan: PlanType) {
        _uiState.update {
            it.copy(
                currentPlan = plan,
                freeDailyUsed = false,
                quantity = it.quantity.coerceAtMost(plan.maxPerBoost)
            )
        }
    }

    // SMM Mock Testing
    fun testSmmApiPing() {
        viewModelScope.launch {
            _uiState.update { it.copy(smmRecentApiLog = "Pinging SMM Panel API endpoint (${it.smmEndpoint})...") }
            delay(800)
            _uiState.update {
                it.copy(
                    smmRecentApiLog = """
                        HTTP 200 OK
                        Response: {
                          "status": "online",
                          "balance": "148.50",
                          "currency": "USD",
                          "services_active": 48,
                          "api_version": "v2.4"
                        }
                    """.trimIndent()
                )
            }
        }
    }
}
