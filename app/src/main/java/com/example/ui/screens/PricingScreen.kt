package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.PlanType
import com.example.ui.theme.JazzCashOrange
import com.example.ui.theme.JazzCashRed
import com.example.ui.theme.TikTokCardBg
import com.example.ui.theme.TikTokCyan
import com.example.ui.theme.TikTokCyanAccent
import com.example.ui.theme.TikTokDarkBg
import com.example.ui.theme.TikTokGold
import com.example.ui.theme.TikTokNeonPink
import com.example.ui.theme.TikTokProPurple
import com.example.ui.theme.TikTokSurface
import com.example.ui.theme.TikTokSurfaceElevated
import com.example.ui.theme.TikTokTextSecondary
import com.example.ui.theme.TikTokTextTertiary
import com.example.ui.theme.TikTokWhite
import com.example.viewmodel.BoosterUiState
import com.example.viewmodel.BoosterViewModel

@Composable
fun PricingScreen(
    state: BoosterUiState,
    viewModel: BoosterViewModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(TikTokDarkBg)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .testTag("pricing_screen")
    ) {
        // Page Title
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(TikTokGold.copy(alpha = 0.15f))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "TRANSPARENT TIKTOK SMM PRICING",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = TikTokGold,
                    letterSpacing = 1.sp
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Choose Your Growth Tier",
                fontSize = 24.sp,
                fontWeight = FontWeight.Black,
                color = TikTokWhite
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Scale your views, likes and followers with local JazzCash payment",
                fontSize = 13.sp,
                color = TikTokTextSecondary
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Three Tiers: FREE, VIP, PRO
        PlanTierCard(
            plan = PlanType.FREE,
            isCurrent = state.currentPlan == PlanType.FREE,
            onSelectPlan = { viewModel.switchPlanDirectly(PlanType.FREE) },
            onCheckout = { /* Free requires no payment */ }
        )

        Spacer(modifier = Modifier.height(16.dp))

        PlanTierCard(
            plan = PlanType.VIP,
            isCurrent = state.currentPlan == PlanType.VIP,
            onSelectPlan = { viewModel.switchPlanDirectly(PlanType.VIP) },
            onCheckout = { viewModel.openCheckout(PlanType.VIP) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        PlanTierCard(
            plan = PlanType.PRO,
            isCurrent = state.currentPlan == PlanType.PRO,
            onSelectPlan = { viewModel.switchPlanDirectly(PlanType.PRO) },
            onCheckout = { viewModel.openCheckout(PlanType.PRO) }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // JazzCash Info Callout
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = TikTokCardBg),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, JazzCashRed.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(JazzCashRed),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "JC",
                        fontWeight = FontWeight.Black,
                        color = TikTokWhite,
                        fontSize = 16.sp
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "Official JazzCash Partner Integration",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = TikTokWhite
                    )
                    Text(
                        text = "Instant automatic account activation with verified TID",
                        fontSize = 11.sp,
                        color = TikTokTextSecondary
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Composable
private fun PlanTierCard(
    plan: PlanType,
    isCurrent: Boolean,
    onSelectPlan: () -> Unit,
    onCheckout: () -> Unit
) {
    val borderColor = when (plan) {
        PlanType.FREE -> if (isCurrent) TikTokCyan else TikTokSurfaceElevated
        PlanType.VIP -> TikTokGold
        PlanType.PRO -> TikTokCyanAccent
    }

    val badgeColor = when (plan) {
        PlanType.FREE -> TikTokTextSecondary
        PlanType.VIP -> TikTokGold
        PlanType.PRO -> TikTokCyan
    }

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = TikTokCardBg),
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = if (plan == PlanType.VIP || plan == PlanType.PRO) 1.5.dp else 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(20.dp)
            )
            .testTag("plan_card_${plan.name.lowercase()}")
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            // Header Row: Plan name, badge, and current status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = plan.title,
                        fontWeight = FontWeight.Black,
                        fontSize = 18.sp,
                        color = TikTokWhite
                    )
                    Text(
                        text = plan.badge,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = badgeColor,
                        letterSpacing = 1.sp
                    )
                }

                if (isCurrent) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF10B981).copy(alpha = 0.2f))
                            .border(1.dp, Color(0xFF10B981), RoundedCornerShape(12.dp))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "CURRENT PLAN",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF10B981)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Pricing Row
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = plan.priceUsd,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Black,
                    color = TikTokWhite
                )
                Text(
                    text = " / ${plan.period}",
                    fontSize = 13.sp,
                    color = TikTokTextSecondary,
                    modifier = Modifier.padding(bottom = 6.dp, start = 4.dp)
                )
                if (plan != PlanType.FREE) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "• ${plan.pricePkr}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = TikTokGold,
                        modifier = Modifier.padding(bottom = 6.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Delivery speed indicator
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.ElectricBolt,
                    contentDescription = "Speed",
                    tint = if (plan == PlanType.FREE) TikTokTextSecondary else TikTokGold,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Delivery: ${plan.speedTitle}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = TikTokWhite
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Features List
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                plan.features.forEach { feat ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(18.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(
                                    if (plan == PlanType.FREE) TikTokSurface else TikTokNeonPink.copy(alpha = 0.2f)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Check,
                                contentDescription = "Feature",
                                tint = if (plan == PlanType.FREE) TikTokCyan else TikTokNeonPink,
                                modifier = Modifier.size(12.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = feat,
                            fontSize = 13.sp,
                            color = TikTokWhite
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Action Buttons
            if (plan == PlanType.FREE) {
                if (!isCurrent) {
                    OutlinedButton(
                        onClick = onSelectPlan,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("select_free_plan_button")
                    ) {
                        Text(
                            text = "Switch to Free Plan",
                            color = TikTokWhite,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(TikTokSurface)
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Active Free Tier (1 boost/day)",
                            color = TikTokTextSecondary,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            } else {
                // Paid Plans: VIP & PRO
                Button(
                    onClick = onCheckout,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (plan == PlanType.VIP) JazzCashRed else TikTokNeonPink
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("upgrade_${plan.name.lowercase()}_button")
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.Payment,
                            contentDescription = "Pay",
                            tint = TikTokWhite,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (isCurrent) "Renew with JazzCash (${plan.pricePkr})" else "Upgrade to ${plan.title} • ${plan.pricePkr}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = TikTokWhite
                        )
                    }
                }

                // Demo switch helper
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Or ",
                        fontSize = 11.sp,
                        color = TikTokTextTertiary
                    )
                    Text(
                        text = "activate instantly for testing",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = TikTokCyan,
                        modifier = Modifier.clickable { onSelectPlan() }
                    )
                }
            }
        }
    }
}
