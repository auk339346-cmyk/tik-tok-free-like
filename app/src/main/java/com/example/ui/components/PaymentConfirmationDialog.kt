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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.window.Dialog
import com.example.model.PaymentSubmission
import com.example.ui.theme.JazzCashOrange
import com.example.ui.theme.JazzCashRed
import com.example.ui.theme.TikTokCardBg
import com.example.ui.theme.TikTokCyan
import com.example.ui.theme.TikTokDarkBg
import com.example.ui.theme.TikTokGold
import com.example.ui.theme.TikTokNeonPink
import com.example.ui.theme.TikTokSurface
import com.example.ui.theme.TikTokTextSecondary
import com.example.ui.theme.TikTokTextTertiary
import com.example.ui.theme.TikTokWhite

@Composable
fun PaymentConfirmationDialog(
    submission: PaymentSubmission,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = TikTokCardBg),
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    1.dp,
                    Brush.verticalGradient(listOf(TikTokGold, JazzCashOrange)),
                    RoundedCornerShape(24.dp)
                )
                .testTag("payment_confirmation_dialog")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Celebration Icon
                Box(
                    modifier = Modifier
                        .size(68.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(
                                listOf(JazzCashOrange, TikTokGold)
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.CheckCircle,
                        contentDescription = "Success",
                        tint = TikTokDarkBg,
                        modifier = Modifier.size(42.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Payment Proof Submitted!",
                    fontWeight = FontWeight.Black,
                    fontSize = 19.sp,
                    color = TikTokWhite
                )

                Text(
                    text = "JazzCash verification successful • Plan activated",
                    fontSize = 12.sp,
                    color = TikTokGold
                )

                Spacer(modifier = Modifier.height(18.dp))

                // Details Card
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(TikTokDarkBg)
                        .padding(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Upgraded Plan",
                            fontSize = 12.sp,
                            color = TikTokTextSecondary
                        )
                        Text(
                            text = submission.planType.title,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = TikTokGold
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Amount Paid",
                            fontSize = 12.sp,
                            color = TikTokTextSecondary
                        )
                        Text(
                            text = "PKR ${submission.amountPkr} (${submission.planType.priceUsd})",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TikTokWhite
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Transaction ID (TID)",
                            fontSize = 12.sp,
                            color = TikTokTextSecondary
                        )
                        Text(
                            text = submission.transactionId,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = TikTokCyan
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Sender Mobile",
                            fontSize = 12.sp,
                            color = TikTokTextSecondary
                        )
                        Text(
                            text = submission.senderPhone,
                            fontSize = 12.sp,
                            color = TikTokWhite
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Status",
                            fontSize = 12.sp,
                            color = TikTokTextSecondary
                        )
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color(0xFF10B981).copy(alpha = 0.2f))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "ACTIVE & UNLOCKED",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF10B981)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Benefit Callout
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(TikTokSurface)
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = "Star",
                        tint = TikTokGold,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "You now have unlimited requests & up to ${submission.planType.maxPerBoost} per boost!",
                        fontSize = 11.sp,
                        color = TikTokWhite
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = onDismiss,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = TikTokNeonPink),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("dismiss_payment_confirmation_button")
                ) {
                    Text(
                        text = "Start Boosting with ${submission.planType.title} 🚀",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = TikTokWhite
                    )
                }
            }
        }
    }
}
