package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import com.example.ui.theme.TikTokCyan
import com.example.ui.theme.TikTokDarkBg
import com.example.ui.theme.TikTokGold
import com.example.ui.theme.TikTokNeonPink
import com.example.ui.theme.TikTokProPurple
import com.example.ui.theme.TikTokSurface
import com.example.ui.theme.TikTokWhite

@Composable
fun TikTokHeader(
    currentPlan: PlanType,
    onPlanBadgeClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = TikTokDarkBg,
        modifier = modifier
            .fillMaxWidth()
            .testTag("app_header")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Brand Logo & Title
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            Brush.linearGradient(
                                listOf(TikTokNeonPink, TikTokCyan)
                            )
                        )
                        .padding(2.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(TikTokDarkBg),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ElectricBolt,
                            contentDescription = "TikTok Boost Logo",
                            tint = TikTokNeonPink,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "TIKTOK",
                            fontWeight = FontWeight.Black,
                            fontSize = 18.sp,
                            color = TikTokWhite,
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "BOOST",
                            fontWeight = FontWeight.Black,
                            fontSize = 18.sp,
                            color = TikTokNeonPink,
                            letterSpacing = 1.sp
                        )
                    }
                    Text(
                        text = "Social Media SMM Engine",
                        fontSize = 11.sp,
                        color = TikTokCyan,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // Current Plan Badge (Tappable to view Pricing/Upgrade)
            val badgeBg = when (currentPlan) {
                PlanType.FREE -> TikTokSurface
                PlanType.VIP -> TikTokGold.copy(alpha = 0.2f)
                PlanType.PRO -> TikTokProPurple.copy(alpha = 0.25f)
            }
            val badgeBorder = when (currentPlan) {
                PlanType.FREE -> Color(0xFF33334D)
                PlanType.VIP -> TikTokGold
                PlanType.PRO -> TikTokCyan
            }
            val badgeTextColor = when (currentPlan) {
                PlanType.FREE -> TikTokWhite
                PlanType.VIP -> TikTokGold
                PlanType.PRO -> TikTokCyan
            }

            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(badgeBg)
                    .border(1.dp, badgeBorder, RoundedCornerShape(20.dp))
                    .clickable(onClick = onPlanBadgeClicked)
                    .padding(horizontal = 12.dp, vertical = 6.dp)
                    .testTag("plan_badge_button"),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "Plan Badge",
                    tint = badgeTextColor,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = currentPlan.badge,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = badgeTextColor
                )
            }
        }
    }
}
