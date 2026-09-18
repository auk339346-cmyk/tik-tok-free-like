package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import com.example.ui.components.ProgressAnimationView
import com.example.ui.components.QuantitySelectorCard
import com.example.ui.components.UrlInputCard
import com.example.ui.theme.TikTokCardBg
import com.example.ui.theme.TikTokCyan
import com.example.ui.theme.TikTokDarkBg
import com.example.ui.theme.TikTokGold
import com.example.ui.theme.TikTokNeonPink
import com.example.ui.theme.TikTokSurface
import com.example.ui.theme.TikTokSurfaceElevated
import com.example.ui.theme.TikTokTextSecondary
import com.example.ui.theme.TikTokTextTertiary
import com.example.ui.theme.TikTokWhite
import com.example.viewmodel.BoosterUiState
import com.example.viewmodel.BoosterViewModel

@Composable
fun BoosterScreen(
    state: BoosterUiState,
    viewModel: BoosterViewModel,
    onNavigateToPricing: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(TikTokDarkBg)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .testTag("booster_screen")
    ) {
        // Hero Banner / Promo Card
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = TikTokCardBg),
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    1.dp,
                    Brush.horizontalGradient(
                        listOf(TikTokNeonPink.copy(alpha = 0.6f), TikTokCyan.copy(alpha = 0.6f))
                    ),
                    RoundedCornerShape(20.dp)
                )
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(TikTokNeonPink.copy(alpha = 0.2f))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "TIKTOK GROWTH SUITE",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Black,
                            color = TikTokNeonPink,
                            letterSpacing = 1.sp
                        )
                    }

                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFF10B981).copy(alpha = 0.15f))
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF10B981))
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "SMM Servers Online",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF10B981)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Boost TikTok Likes, Views & Followers",
                    fontWeight = FontWeight.Black,
                    fontSize = 20.sp,
                    color = TikTokWhite,
                    lineHeight = 26.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Organic FYP delivery with active retention nodes. Safe, private, and instant.",
                    fontSize = 12.sp,
                    color = TikTokTextSecondary,
                    lineHeight = 18.sp
                )

                // Current Plan Quick Ribbon
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(TikTokDarkBg)
                        .clickable { onNavigateToPricing() }
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = "Star",
                            tint = if (state.currentPlan == PlanType.FREE) TikTokCyan else TikTokGold,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Active: ${state.currentPlan.title} (${state.currentPlan.priceUsd})",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = TikTokWhite
                        )
                    }
                    Text(
                        text = if (state.currentPlan == PlanType.FREE) "Upgrade to VIP >" else "Manage Plan >",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (state.currentPlan == PlanType.FREE) TikTokGold else TikTokCyan
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Live Boost Progress View (shown while boosting)
        AnimatedVisibility(
            visible = state.isBoosting,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Column {
                ProgressAnimationView(
                    progress = state.boostProgress,
                    stage = state.boostStage,
                    deliveredCount = state.deliveredCount,
                    targetCount = state.quantity,
                    service = state.selectedService,
                    plan = state.currentPlan
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        // Step 1: TikTok URL Input
        UrlInputCard(
            url = state.tiktokUrl,
            onUrlChange = { viewModel.onUrlChanged(it) },
            onSubmitLink = { viewModel.submitLink() },
            isSubmitted = state.isUrlSubmitted
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Step 2: Quantity & Service Selection Panel
        QuantitySelectorCard(
            selectedService = state.selectedService,
            onServiceSelect = { viewModel.selectService(it) },
            quantity = state.quantity,
            onQuantityChange = { viewModel.setQuantity(it) },
            currentPlan = state.currentPlan,
            onUpgradeClick = { plan -> viewModel.openCheckout(plan) },
            onStartBoost = { viewModel.startBoost() },
            isBoosting = state.isBoosting,
            freeDailyUsed = state.freeDailyUsed
        )

        Spacer(modifier = Modifier.height(18.dp))

        // Growth Guarantees & Features Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            GrowthPill(
                icon = Icons.Filled.Security,
                title = "100% FYP Safe",
                subtitle = "No password needed",
                modifier = Modifier.weight(1f)
            )
            GrowthPill(
                icon = Icons.Filled.TrendingUp,
                title = "High Retention",
                subtitle = "Active profile signals",
                modifier = Modifier.weight(1f)
            )
            GrowthPill(
                icon = Icons.Filled.ElectricBolt,
                title = "Turbo Delivery",
                subtitle = "Instant ramp-up",
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun GrowthPill(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(TikTokCardBg)
            .border(1.dp, TikTokSurfaceElevated, RoundedCornerShape(14.dp))
            .padding(12.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = TikTokCyan,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = title,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = TikTokWhite
        )
        Text(
            text = subtitle,
            fontSize = 10.sp,
            color = TikTokTextSecondary
        )
    }
}
