package com.example.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CloudSync
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.example.model.ServiceType
import com.example.ui.theme.TikTokCardBg
import com.example.ui.theme.TikTokCyan
import com.example.ui.theme.TikTokDarkBg
import com.example.ui.theme.TikTokNeonPink
import com.example.ui.theme.TikTokSurfaceElevated
import com.example.ui.theme.TikTokTextSecondary
import com.example.ui.theme.TikTokTextTertiary
import com.example.ui.theme.TikTokWhite

@Composable
fun ProgressAnimationView(
    progress: Float,
    stage: String,
    deliveredCount: Int,
    targetCount: Int,
    service: ServiceType,
    plan: PlanType,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(TikTokCardBg)
            .border(
                1.dp,
                Brush.horizontalGradient(listOf(TikTokNeonPink, TikTokCyan)),
                RoundedCornerShape(20.dp)
            )
            .padding(20.dp)
            .testTag("progress_animation_view")
    ) {
        // Status & Live Pulse
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(TikTokNeonPink.copy(alpha = glowAlpha))
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "LIVE BOOST DISPATCH",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = TikTokNeonPink,
                    letterSpacing = 1.sp
                )
            }

            // Node cluster info
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(TikTokDarkBg)
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.CloudSync,
                    contentDescription = "Cloud Node",
                    tint = TikTokCyan,
                    modifier = Modifier.size(12.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Cluster #${plan.name}-01",
                    fontSize = 11.sp,
                    color = TikTokCyan,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Progress Numbers & Percentage
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Column {
                Text(
                    text = "Delivered Progress",
                    fontSize = 12.sp,
                    color = TikTokTextSecondary
                )
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = "$deliveredCount",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Black,
                        color = TikTokWhite
                    )
                    Text(
                        text = " / $targetCount ${service.unit}",
                        fontSize = 14.sp,
                        color = TikTokTextSecondary,
                        modifier = Modifier.padding(bottom = 4.dp, start = 4.dp)
                    )
                }
            }

            Text(
                text = "${(progress * 100).toInt()}%",
                fontSize = 24.sp,
                fontWeight = FontWeight.Black,
                color = TikTokCyan
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Custom Glowing Neon Progress Bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(TikTokDarkBg)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(fraction = progress.coerceIn(0f, 1f))
                    .height(12.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(
                        Brush.horizontalGradient(
                            listOf(TikTokNeonPink, TikTokCyan)
                        )
                    )
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Live Stage Text and Speed
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(TikTokDarkBg)
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircularProgressIndicator(
                progress = { progress },
                strokeWidth = 2.dp,
                color = TikTokCyan,
                trackColor = TikTokSurfaceElevated,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = stage.ifBlank { "Processing boost..." },
                fontSize = 12.sp,
                color = TikTokWhite,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.Speed,
                    contentDescription = "Speed",
                    tint = TikTokTextTertiary,
                    modifier = Modifier.size(12.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Delivery Speed: ${plan.speedTitle}",
                    fontSize = 11.sp,
                    color = TikTokTextTertiary
                )
            }

            Text(
                text = "Algorithm FYP-Safe ✓",
                fontSize = 11.sp,
                color = Color(0xFF10B981),
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
