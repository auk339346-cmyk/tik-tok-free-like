package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.components.JazzCashCheckoutSheet
import com.example.ui.components.OrderSuccessDialog
import com.example.ui.components.PaymentConfirmationDialog
import com.example.ui.components.TikTokHeader
import com.example.ui.screens.BoosterScreen
import com.example.ui.screens.HistoryScreen
import com.example.ui.screens.PricingScreen
import com.example.ui.screens.SmmApiScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.TikTokCardBg
import com.example.ui.theme.TikTokCyan
import com.example.ui.theme.TikTokDarkBg
import com.example.ui.theme.TikTokGold
import com.example.ui.theme.TikTokNeonPink
import com.example.ui.theme.TikTokSurfaceElevated
import com.example.ui.theme.TikTokTextSecondary
import com.example.ui.theme.TikTokWhite
import com.example.viewmodel.BoosterViewModel

enum class AppNavTab(val label: String) {
    BOOSTER("Booster"),
    PRICING("VIP Pricing"),
    HISTORY("History"),
    SMM_API("SMM API")
}

class MainActivity : ComponentActivity() {
    private val viewModel: BoosterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MainAppContent(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun MainAppContent(viewModel: BoosterViewModel) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    var currentTab by remember { mutableStateOf(AppNavTab.BOOSTER) }

    Scaffold(
        topBar = {
            TikTokHeader(
                currentPlan = state.currentPlan,
                onPlanBadgeClicked = { currentTab = AppNavTab.PRICING }
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = TikTokCardBg,
                modifier = Modifier
                    .windowInsetsPadding(WindowInsets.navigationBars)
                    .testTag("bottom_navigation_bar")
            ) {
                NavigationBarItem(
                    selected = currentTab == AppNavTab.BOOSTER,
                    onClick = { currentTab = AppNavTab.BOOSTER },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.RocketLaunch,
                            contentDescription = "Booster"
                        )
                    },
                    label = {
                        Text(
                            text = AppNavTab.BOOSTER.label,
                            fontSize = 11.sp,
                            fontWeight = if (currentTab == AppNavTab.BOOSTER) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = TikTokWhite,
                        selectedTextColor = TikTokNeonPink,
                        indicatorColor = TikTokNeonPink,
                        unselectedIconColor = TikTokTextSecondary,
                        unselectedTextColor = TikTokTextSecondary
                    ),
                    modifier = Modifier.testTag("nav_tab_booster")
                )

                NavigationBarItem(
                    selected = currentTab == AppNavTab.PRICING,
                    onClick = { currentTab = AppNavTab.PRICING },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = "VIP Pricing"
                        )
                    },
                    label = {
                        Text(
                            text = AppNavTab.PRICING.label,
                            fontSize = 11.sp,
                            fontWeight = if (currentTab == AppNavTab.PRICING) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = TikTokDarkBg,
                        selectedTextColor = TikTokGold,
                        indicatorColor = TikTokGold,
                        unselectedIconColor = TikTokTextSecondary,
                        unselectedTextColor = TikTokTextSecondary
                    ),
                    modifier = Modifier.testTag("nav_tab_pricing")
                )

                NavigationBarItem(
                    selected = currentTab == AppNavTab.HISTORY,
                    onClick = { currentTab = AppNavTab.HISTORY },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.History,
                            contentDescription = "History"
                        )
                    },
                    label = {
                        Text(
                            text = AppNavTab.HISTORY.label,
                            fontSize = 11.sp,
                            fontWeight = if (currentTab == AppNavTab.HISTORY) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = TikTokDarkBg,
                        selectedTextColor = TikTokCyan,
                        indicatorColor = TikTokCyan,
                        unselectedIconColor = TikTokTextSecondary,
                        unselectedTextColor = TikTokTextSecondary
                    ),
                    modifier = Modifier.testTag("nav_tab_history")
                )

                NavigationBarItem(
                    selected = currentTab == AppNavTab.SMM_API,
                    onClick = { currentTab = AppNavTab.SMM_API },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Code,
                            contentDescription = "SMM API"
                        )
                    },
                    label = {
                        Text(
                            text = AppNavTab.SMM_API.label,
                            fontSize = 11.sp,
                            fontWeight = if (currentTab == AppNavTab.SMM_API) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = TikTokDarkBg,
                        selectedTextColor = TikTokCyan,
                        indicatorColor = TikTokCyan,
                        unselectedIconColor = TikTokTextSecondary,
                        unselectedTextColor = TikTokTextSecondary
                    ),
                    modifier = Modifier.testTag("nav_tab_smm_api")
                )
            }
        },
        containerColor = TikTokDarkBg,
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentTab) {
                AppNavTab.BOOSTER -> BoosterScreen(
                    state = state,
                    viewModel = viewModel,
                    onNavigateToPricing = { currentTab = AppNavTab.PRICING }
                )
                AppNavTab.PRICING -> PricingScreen(
                    state = state,
                    viewModel = viewModel
                )
                AppNavTab.HISTORY -> HistoryScreen(
                    orders = state.orderHistory,
                    onBoostNewLink = { currentTab = AppNavTab.BOOSTER }
                )
                AppNavTab.SMM_API -> SmmApiScreen(
                    state = state,
                    viewModel = viewModel
                )
            }
        }
    }

    // Modal: JazzCash Checkout Sheet
    if (state.showCheckoutSheet) {
        JazzCashCheckoutSheet(
            plan = state.checkoutPlan,
            senderPhone = state.senderPhone,
            onSenderPhoneChange = { viewModel.onSenderPhoneChanged(it) },
            transactionId = state.transactionId,
            onTransactionIdChange = { viewModel.onTransactionIdChanged(it) },
            proofImageUri = state.proofImageUri,
            onProofImageSelected = { viewModel.onProofImageSelected(it) },
            errorMessage = state.paymentMessage,
            onSubmitProof = { viewModel.submitPaymentProof() },
            onDismiss = { viewModel.closeCheckout() }
        )
    }

    // Dialog: Payment Submission Confirmation
    state.latestPaymentSubmission?.let { submission ->
        PaymentConfirmationDialog(
            submission = submission,
            onDismiss = {
                viewModel.dismissPaymentConfirmation()
                currentTab = AppNavTab.BOOSTER
            }
        )
    }

    // Dialog: Boost Order Completed Receipt
    state.latestCompletedOrder?.let { order ->
        OrderSuccessDialog(
            order = order,
            onDismiss = { viewModel.dismissCompletionDialog() },
            onViewHistory = {
                viewModel.dismissCompletionDialog()
                currentTab = AppNavTab.HISTORY
            }
        )
    }
}
