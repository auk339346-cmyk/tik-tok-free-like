package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.PlanType
import com.example.model.ServiceType
import com.example.ui.theme.TikTokCardBg
import com.example.ui.theme.TikTokCyan
import com.example.ui.theme.TikTokDarkBg
import com.example.ui.theme.TikTokGold
import com.example.ui.theme.TikTokGoldDark
import com.example.ui.theme.TikTokNeonPink
import com.example.ui.theme.TikTokProPurple
import com.example.ui.theme.TikTokSurface
import com.example.ui.theme.TikTokSurfaceElevated
import com.example.ui.theme.TikTokTextSecondary
import com.example.ui.theme.TikTokTextTertiary
import com.example.ui.theme.TikTokWhite

@Composable
fun QuantitySelectorCard(
    selectedService: ServiceType,
    onServiceSelect: (ServiceType) -> Unit,
    quantity: Int,
    onQuantityChange: (Int) -> Unit,
    currentPlan: PlanType,
    onUpgradeClick: (PlanType) -> Unit,
    onStartBoost: () -> Unit,
    isBoosting: Boolean,
    freeDailyUsed: Boolean,
    modifier: Modifier = Modifier
) {
    val maxAllowed = currentPlan.maxPerBoost
    val presets = when (currentPlan) {
        PlanType.FREE -> listOf(25, 50, 75, 100)
        PlanType.VIP -> listOf(250, 500, 1000, 2500, 5000)
        PlanType.PRO -> listOf(1000, 5000, 10000, 25000, 50000)
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(TikTokCardBg)
            .border(1.dp, TikTokSurfaceElevated, RoundedCornerShape(20.dp))
            .padding(18.dp)
            .testTag("quantity_selector_card")
    ) {
        // Step Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(TikTokCyan.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "2",
                        color = TikTokCyan,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "Choose Service & Quantity",
                        fontWeight = FontWeight.Bold,
                        color = TikTokWhite,
                        fontSize = 15.sp
                    )
                    Text(
                        text = "High-retention social signals",
                        color = TikTokTextSecondary,
                        fontSize = 12.sp
                    )
                }
            }

            // Plan limit pill
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        if (currentPlan == PlanType.FREE) TikTokSurface else TikTokGold.copy(alpha = 0.15f)
                    )
                    .border(
                        1.dp,
                        if (currentPlan == PlanType.FREE) TikTokSurfaceElevated else TikTokGold.copy(alpha = 0.5f),
                        RoundedCornerShape(12.dp)
                    )
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "Max: $maxAllowed ${selectedService.unit}",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (currentPlan == PlanType.FREE) TikTokTextSecondary else TikTokGold
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Service Type Selector Tabs
        Text(
            text = "SERVICE TYPE",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = TikTokTextTertiary,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(ServiceType.values()) { service ->
                val isSelected = service == selectedService
                val isLocked = service.requiresVip && currentPlan == PlanType.FREE

                Surface(
                    onClick = {
                        if (isLocked) {
                            onUpgradeClick(PlanType.VIP)
                        } else {
                            onServiceSelect(service)
                        }
                    },
                    shape = RoundedCornerShape(14.dp),
                    color = when {
                        isSelected -> TikTokNeonPink
                        isLocked -> TikTokDarkBg.copy(alpha = 0.6f)
                        else -> TikTokSurface
                    },
                    border = when {
                        isSelected -> null
                        isLocked -> null
                        else -> androidx.compose.foundation.BorderStroke(1.dp, TikTokSurfaceElevated)
                    },
                    modifier = Modifier.testTag("service_tab_${service.name.lowercase()}")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = service.getIcon(),
                            contentDescription = service.title,
                            tint = if (isSelected) TikTokWhite else if (isLocked) TikTokTextTertiary else TikTokCyan,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = service.title,
                            fontSize = 13.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) TikTokWhite else if (isLocked) TikTokTextTertiary else TikTokWhite
                        )
                        if (isLocked) {
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.Filled.Lock,
                                contentDescription = "Requires VIP",
                                tint = TikTokGold,
                                modifier = Modifier.size(12.dp)
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Quantity Header with live number
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "QUANTITY SELECTOR",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = TikTokTextTertiary,
                letterSpacing = 1.sp
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "$quantity",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black,
                    color = TikTokCyan
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = selectedService.unit.lowercase(),
                    fontSize = 13.sp,
                    color = TikTokTextSecondary
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Quantity Slider
        Slider(
            value = quantity.toFloat(),
            onValueChange = { onQuantityChange(it.toInt()) },
            valueRange = 10f..maxAllowed.toFloat(),
            steps = if (maxAllowed <= 100) 8 else 19,
            colors = SliderDefaults.colors(
                thumbColor = TikTokNeonPink,
                activeTrackColor = TikTokNeonPink,
                inactiveTrackColor = TikTokSurfaceElevated
            ),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("quantity_slider")
        )

        // Preset Quick Chips
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            presets.forEach { preset ->
                val isCurrent = quantity == preset
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (isCurrent) TikTokCyan.copy(alpha = 0.2f) else TikTokSurface)
                        .border(
                            1.dp,
                            if (isCurrent) TikTokCyan else TikTokSurfaceElevated,
                            RoundedCornerShape(10.dp)
                        )
                        .clickable { onQuantityChange(preset) }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                        .testTag("preset_qty_$preset")
                ) {
                    Text(
                        text = "+$preset",
                        fontSize = 12.sp,
                        fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Medium,
                        color = if (isCurrent) TikTokCyan else TikTokWhite
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Plan Limit & Speed Info Banner
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(TikTokDarkBg)
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.ElectricBolt,
                        contentDescription = "Delivery Speed",
                        tint = if (currentPlan == PlanType.FREE) TikTokTextSecondary else TikTokGold,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Speed: ${currentPlan.speedTitle}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TikTokWhite
                    )
                }

                Text(
                    text = if (currentPlan.isUnlimited) "Unlimited Daily" else if (freeDailyUsed) "0/1 free left today" else "1/1 free boost ready",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (freeDailyUsed && currentPlan == PlanType.FREE) TikTokNeonPink else TikTokCyan
                )
            }

            if (currentPlan == PlanType.FREE) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(TikTokGold.copy(alpha = 0.1f))
                        .clickable { onUpgradeClick(PlanType.VIP) }
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = "Upgrade Star",
                            tint = TikTokGold,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Want up to 50,000 & Instant Turbo speed?",
                            fontSize = 11.sp,
                            color = TikTokGold
                        )
                    }
                    Text(
                        text = "Upgrade >",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = TikTokGold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Start Boost Button
        val buttonText = when {
            isBoosting -> "Processing Boost Delivery..."
            currentPlan == PlanType.FREE && freeDailyUsed -> "Daily Free Limit Reached • Upgrade"
            else -> "Start Boost ($quantity ${selectedService.unit})"
        }

        Button(
            onClick = {
                if (currentPlan == PlanType.FREE && freeDailyUsed) {
                    onUpgradeClick(PlanType.VIP)
                } else {
                    onStartBoost()
                }
            },
            enabled = !isBoosting,
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (currentPlan == PlanType.FREE && freeDailyUsed) TikTokGoldDark else TikTokNeonPink
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("start_boost_button")
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = if (currentPlan == PlanType.FREE && freeDailyUsed) Icons.Filled.Star else Icons.Filled.RocketLaunch,
                    contentDescription = "Start Boost Icon",
                    tint = TikTokWhite,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = buttonText,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = TikTokWhite
                )
            }
        }
    }
}
