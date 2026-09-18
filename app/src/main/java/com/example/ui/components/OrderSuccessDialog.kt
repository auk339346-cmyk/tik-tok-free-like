package com.example.ui.components

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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.model.BoostOrder
import com.example.ui.theme.TikTokCardBg
import com.example.ui.theme.TikTokCyan
import com.example.ui.theme.TikTokDarkBg
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
fun OrderSuccessDialog(
    order: BoostOrder,
    onDismiss: () -> Unit,
    onViewHistory: () -> Unit
) {
    val clipboard = LocalClipboardManager.current
    val dateStr = SimpleDateFormat("MMM dd, yyyy • hh:mm a", Locale.getDefault()).format(Date(order.timestamp))

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = TikTokCardBg),
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    1.dp,
                    Brush.verticalGradient(listOf(TikTokCyan, TikTokNeonPink)),
                    RoundedCornerShape(24.dp)
                )
                .testTag("order_success_dialog")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Success badge with glow
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(
                                listOf(TikTokCyan, Color(0xFF10B981))
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = "Success",
                        tint = TikTokDarkBg,
                        modifier = Modifier.size(36.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Boost Successfully Delivered!",
                    fontWeight = FontWeight.Black,
                    fontSize = 18.sp,
                    color = TikTokWhite
                )

                Text(
                    text = "Your TikTok metrics are updating on FYP",
                    fontSize = 12.sp,
                    color = TikTokCyan
                )

                Spacer(modifier = Modifier.height(18.dp))

                // Receipt Card
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(TikTokDarkBg)
                        .padding(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Order ID",
                            fontSize = 12.sp,
                            color = TikTokTextSecondary
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = order.id,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = TikTokWhite
                            )
                            IconButton(
                                onClick = { clipboard.setText(AnnotatedString(order.id)) },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.ContentCopy,
                                    contentDescription = "Copy Order ID",
                                    tint = TikTokCyan,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Service Delivered",
                            fontSize = 12.sp,
                            color = TikTokTextSecondary
                        )
                        Text(
                            text = "${order.quantity} ${order.serviceType.unit}",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = TikTokNeonPink
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Target URL",
                            fontSize = 12.sp,
                            color = TikTokTextSecondary
                        )
                        Text(
                            text = order.targetUrl.take(24) + "...",
                            fontSize = 12.sp,
                            color = TikTokWhite,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Timestamp",
                            fontSize = 12.sp,
                            color = TikTokTextSecondary
                        )
                        Text(
                            text = dateStr,
                            fontSize = 11.sp,
                            color = TikTokTextTertiary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Actions
                Button(
                    onClick = onDismiss,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = TikTokNeonPink),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("boost_again_button")
                ) {
                    Text(
                        text = "Done • Boost Another Link",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = TikTokWhite
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedButton(
                    onClick = {
                        onDismiss()
                        onViewHistory()
                    },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .testTag("view_history_button")
                ) {
                    Text(
                        text = "View Orders History",
                        color = TikTokCyan,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}
