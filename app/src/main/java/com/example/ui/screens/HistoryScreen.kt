package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.BoostOrder
import com.example.model.BoostStatus
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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HistoryScreen(
    orders: List<BoostOrder>,
    onBoostNewLink: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(TikTokDarkBg)
            .padding(16.dp)
            .testTag("history_screen")
    ) {
        // Screen Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Boost History",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Black,
                    color = TikTokWhite
                )
                Text(
                    text = "Track your TikTok service dispatches & FYP status",
                    fontSize = 12.sp,
                    color = TikTokTextSecondary
                )
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(TikTokSurface)
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "${orders.size} Total Orders",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = TikTokCyan
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (orders.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 60.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Filled.History,
                        contentDescription = "No History",
                        tint = TikTokTextTertiary,
                        modifier = Modifier.size(56.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "No boosts yet",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = TikTokWhite
                    )
                    Text(
                        text = "Paste a TikTok link in the Booster tab to start",
                        fontSize = 12.sp,
                        color = TikTokTextSecondary
                    )
                }
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(orders, key = { it.id }) { order ->
                    OrderHistoryItem(order = order)
                }
            }
        }
    }
}

@Composable
private fun OrderHistoryItem(order: BoostOrder) {
    val dateStr = SimpleDateFormat("MMM dd, hh:mm a", Locale.getDefault()).format(Date(order.timestamp))

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = TikTokCardBg),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, TikTokSurfaceElevated, RoundedCornerShape(16.dp))
            .testTag("order_item_${order.id}")
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Top Row: ID, plan badge and status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = order.id,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = TikTokWhite
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(TikTokDarkBg)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = order.planType.name,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (order.planType.rawUsd > 0) TikTokGold else TikTokTextSecondary
                        )
                    }
                }

                // Status Tag
                val (statusColor, statusText) = when (order.status) {
                    BoostStatus.COMPLETED -> Color(0xFF10B981) to "Completed"
                    BoostStatus.DELIVERED -> Color(0xFF10B981) to "Delivered"
                    BoostStatus.PROCESSING -> TikTokCyan to "In Progress"
                    BoostStatus.QUEUED -> Color(0xFFF59E0B) to "Queued"
                }

                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(statusColor.copy(alpha = 0.15f))
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(statusColor)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = statusText,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = statusColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Service & Quantity Banner
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(TikTokDarkBg)
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = order.serviceType.getIcon(),
                        contentDescription = order.serviceType.title,
                        tint = TikTokNeonPink,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "${order.quantity} ${order.serviceType.unit}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black,
                        color = TikTokWhite
                    )
                }

                Text(
                    text = dateStr,
                    fontSize = 11.sp,
                    color = TikTokTextTertiary
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Target URL
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Filled.Link,
                    contentDescription = "URL",
                    tint = TikTokTextTertiary,
                    modifier = Modifier.size(12.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = order.targetUrl,
                    fontSize = 11.sp,
                    color = TikTokTextSecondary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
