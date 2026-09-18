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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
fun SmmApiScreen(
    state: BoosterUiState,
    viewModel: BoosterViewModel,
    modifier: Modifier = Modifier
) {
    val clipboard = LocalClipboardManager.current

    val serviceCatalog = listOf(
        ServiceMapping("101", "TikTok Video Likes", "$0.08 / 1k", "Instant (<5s)"),
        ServiceMapping("102", "TikTok FYP Views", "$0.01 / 1k", "5,000 / min"),
        ServiceMapping("103", "TikTok Profile Followers", "$0.45 / 1k", "Gradual Safe"),
        ServiceMapping("104", "TikTok Video Saves / Bookmarks", "$0.06 / 1k", "Algorithm booster"),
        ServiceMapping("105", "TikTok Share Link Multipliers", "$0.10 / 1k", "Viral trigger")
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(TikTokDarkBg)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .testTag("smm_api_screen")
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "SMM Panel API",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Black,
                    color = TikTokWhite
                )
                Text(
                    text = "Developer integration & Automated Dispatch Dashboard",
                    fontSize = 12.sp,
                    color = TikTokTextSecondary
                )
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(TikTokCyan.copy(alpha = 0.15f))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "API v2.0 Ready",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = TikTokCyan
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Balance & Endpoint Summary
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = TikTokCardBg),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, TikTokSurfaceElevated, RoundedCornerShape(16.dp))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.AccountBalanceWallet,
                            contentDescription = "Wallet",
                            tint = TikTokGold,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Provider Reseller Balance",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = TikTokWhite
                        )
                    }

                    Text(
                        text = state.smmBalance,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Black,
                        color = Color(0xFF10B981)
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Endpoint Field
                Text(
                    text = "BASE API ENDPOINT",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = TikTokTextTertiary,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(TikTokDarkBg)
                        .padding(horizontal = 10.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = state.smmEndpoint,
                        fontSize = 12.sp,
                        fontFamily = FontFamily.Monospace,
                        color = TikTokCyan
                    )
                    IconButton(
                        onClick = { clipboard.setText(AnnotatedString(state.smmEndpoint)) },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ContentCopy,
                            contentDescription = "Copy Endpoint",
                            tint = TikTokTextSecondary,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // API Key Field
                Text(
                    text = "API SECRET KEY",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = TikTokTextTertiary,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(TikTokDarkBg)
                        .padding(horizontal = 10.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = state.smmApiKey,
                        fontSize = 12.sp,
                        fontFamily = FontFamily.Monospace,
                        color = TikTokGold
                    )
                    IconButton(
                        onClick = { clipboard.setText(AnnotatedString(state.smmApiKey)) },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ContentCopy,
                            contentDescription = "Copy API Key",
                            tint = TikTokTextSecondary,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Live API Test Trigger
        Button(
            onClick = { viewModel.testSmmApiPing() },
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = TikTokSurfaceElevated),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .testTag("test_smm_api_button")
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.PlayArrow,
                    contentDescription = "Test API",
                    tint = TikTokCyan,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Send Mock SMM API Ping (Check Status)",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = TikTokWhite
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // API Console / Response Box
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF07070B))
                .border(1.dp, TikTokSurfaceElevated, RoundedCornerShape(12.dp))
                .padding(12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.Terminal,
                    contentDescription = "Console",
                    tint = TikTokTextTertiary,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "API RESPONSE CONSOLE",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = TikTokTextTertiary
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = state.smmRecentApiLog,
                fontSize = 11.sp,
                fontFamily = FontFamily.Monospace,
                color = TikTokCyan,
                lineHeight = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Service Mapping Table
        Text(
            text = "TIKTOK SMM SERVICE CATALOG MAPPING",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = TikTokTextTertiary,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            serviceCatalog.forEach { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(TikTokCardBg)
                        .border(1.dp, TikTokSurfaceElevated, RoundedCornerShape(10.dp))
                        .padding(horizontal = 12.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(TikTokDarkBg)
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "ID #${item.id}",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = TikTokNeonPink
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = item.name,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = TikTokWhite
                            )
                            Text(
                                text = item.rate,
                                fontSize = 11.sp,
                                color = TikTokCyan
                            )
                        }
                    }

                    Text(
                        text = item.speed,
                        fontSize = 11.sp,
                        color = TikTokTextSecondary
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}

private data class ServiceMapping(
    val id: String,
    val name: String,
    val rate: String,
    val speed: String
)
