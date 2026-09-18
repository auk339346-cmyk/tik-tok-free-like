package com.example.ui.components

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.model.PlanType
import com.example.ui.theme.JazzCashBg
import com.example.ui.theme.JazzCashOrange
import com.example.ui.theme.JazzCashRed
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JazzCashCheckoutSheet(
    plan: PlanType,
    senderPhone: String,
    onSenderPhoneChange: (String) -> Unit,
    transactionId: String,
    onTransactionIdChange: (String) -> Unit,
    proofImageUri: Uri?,
    onProofImageSelected: (Uri?) -> Unit,
    errorMessage: String?,
    onSubmitProof: () -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val clipboard = LocalClipboardManager.current
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    // JazzCash Account Details
    val jazzCashAccountName = "Muhammad Usman (TikTok Services)"
    val jazzCashAccountNumber = "0304-1234567"

    // Photo picker launcher (Android zero-permission photo picker)
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        onProofImageSelected(uri)
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = TikTokCardBg,
        scrimColor = Color.Black.copy(alpha = 0.7f),
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        modifier = Modifier.testTag("jazzcash_checkout_sheet")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .padding(bottom = 32.dp)
        ) {
            // Header with Close Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
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
                            text = "JazzCash Checkout",
                            fontWeight = FontWeight.Black,
                            fontSize = 18.sp,
                            color = TikTokWhite
                        )
                        Text(
                            text = "Instant VIP Subscription Upgrade",
                            fontSize = 12.sp,
                            color = TikTokTextSecondary
                        )
                    }
                }

                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier.testTag("close_checkout_button")
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Close",
                        tint = TikTokTextSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Plan Summary Card
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = TikTokDarkBg),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        1.dp,
                        Brush.horizontalGradient(listOf(TikTokGold, JazzCashOrange)),
                        RoundedCornerShape(16.dp)
                    )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = plan.title,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = TikTokGold
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(TikTokGold.copy(alpha = 0.2f))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "30 DAYS",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Black,
                                    color = TikTokGold
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Max ${plan.maxPerBoost} per boost • Unlimited daily requests",
                            fontSize = 11.sp,
                            color = TikTokTextSecondary
                        )
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = plan.pricePkr,
                            fontWeight = FontWeight.Black,
                            fontSize = 18.sp,
                            color = TikTokWhite
                        )
                        Text(
                            text = "(${plan.priceUsd}/month)",
                            fontSize = 12.sp,
                            color = TikTokCyan
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // JazzCash Manual Payment Instructions
            Text(
                text = "MANUAL PAYMENT INSTRUCTIONS",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = TikTokTextTertiary,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(JazzCashBg)
                    .border(1.dp, JazzCashRed.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(JazzCashOrange)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Payment Method: JazzCash Mobile Account",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = TikTokWhite
                        )
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(JazzCashRed)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "Pakistan",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = TikTokWhite
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Account Name Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(TikTokDarkBg)
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Account Name",
                            fontSize = 11.sp,
                            color = TikTokTextSecondary
                        )
                        Text(
                            text = jazzCashAccountName,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = TikTokWhite
                        )
                    }
                    IconButton(
                        onClick = {
                            clipboard.setText(AnnotatedString(jazzCashAccountName))
                            Toast.makeText(context, "Account Name copied!", Toast.LENGTH_SHORT).show()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ContentCopy,
                            contentDescription = "Copy Account Name",
                            tint = JazzCashOrange,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Account Number Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(TikTokDarkBg)
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Account Number (Mobile)",
                            fontSize = 11.sp,
                            color = TikTokTextSecondary
                        )
                        Text(
                            text = jazzCashAccountNumber,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Black,
                            color = TikTokCyan
                        )
                    }
                    IconButton(
                        onClick = {
                            clipboard.setText(AnnotatedString(jazzCashAccountNumber.replace("-", "")))
                            Toast.makeText(context, "JazzCash number copied!", Toast.LENGTH_SHORT).show()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ContentCopy,
                            contentDescription = "Copy Account Number",
                            tint = TikTokCyan,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Steps list
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "1. Open your JazzCash App or dial *786#",
                        fontSize = 11.sp,
                        color = TikTokTextSecondary
                    )
                    Text(
                        text = "2. Select 'Send Money' -> 'Mobile Account'",
                        fontSize = 11.sp,
                        color = TikTokTextSecondary
                    )
                    Text(
                        text = "3. Send ${plan.pricePkr} to $jazzCashAccountNumber",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TikTokGold
                    )
                    Text(
                        text = "4. Copy the 12-digit TID from the JazzCash SMS and enter below",
                        fontSize = 11.sp,
                        color = TikTokTextSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Payment Verification Input Fields
            Text(
                text = "PAYMENT VERIFICATION",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = TikTokTextTertiary,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Sender Phone Number
            OutlinedTextField(
                value = senderPhone,
                onValueChange = onSenderPhoneChange,
                label = { Text("Sender's JazzCash Phone Number") },
                placeholder = { Text("e.g. 03001234567") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Phone,
                        contentDescription = "Sender Phone",
                        tint = JazzCashOrange
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = TikTokDarkBg,
                    unfocusedContainerColor = TikTokDarkBg,
                    focusedBorderColor = JazzCashOrange,
                    unfocusedBorderColor = TikTokSurfaceElevated,
                    focusedTextColor = TikTokWhite,
                    unfocusedTextColor = TikTokWhite,
                    focusedLabelColor = JazzCashOrange,
                    unfocusedLabelColor = TikTokTextSecondary
                ),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("sender_phone_input")
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Transaction ID (TID)
            OutlinedTextField(
                value = transactionId,
                onValueChange = onTransactionIdChange,
                label = { Text("Transaction ID (TID)") },
                placeholder = { Text("e.g. 019284719284") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Receipt,
                        contentDescription = "TID",
                        tint = TikTokCyan
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = TikTokDarkBg,
                    unfocusedContainerColor = TikTokDarkBg,
                    focusedBorderColor = TikTokCyan,
                    unfocusedBorderColor = TikTokSurfaceElevated,
                    focusedTextColor = TikTokWhite,
                    unfocusedTextColor = TikTokWhite,
                    focusedLabelColor = TikTokCyan,
                    unfocusedLabelColor = TikTokTextSecondary
                ),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("transaction_id_input")
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Screenshot / Proof Upload Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Payment Proof Screenshot (Optional)",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = TikTokTextSecondary
                )
                if (proofImageUri != null) {
                    Text(
                        text = "Attached ✓",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = TikTokCyan
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            if (proofImageUri == null) {
                // Upload button
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(TikTokDarkBg)
                        .border(1.dp, TikTokSurfaceElevated, RoundedCornerShape(14.dp))
                        .clickable {
                            photoPickerLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        }
                        .padding(vertical = 16.dp)
                        .testTag("upload_proof_button"),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.AddPhotoAlternate,
                            contentDescription = "Upload Screenshot",
                            tint = TikTokCyan,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Select Payment Receipt / Screenshot",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TikTokWhite
                        )
                    }
                }
            } else {
                // Preview with remove button
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(TikTokDarkBg)
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = proofImageUri,
                        contentDescription = "Payment Receipt Screenshot",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(54.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Payment Screenshot Selected",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = TikTokWhite
                        )
                        Text(
                            text = "Ready to upload with submission",
                            fontSize = 11.sp,
                            color = TikTokTextSecondary
                        )
                    }
                    IconButton(
                        onClick = { onProofImageSelected(null) },
                        modifier = Modifier.testTag("remove_proof_button")
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Remove Proof",
                            tint = TikTokNeonPink
                        )
                    }
                }
            }

            if (!errorMessage.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = errorMessage,
                    color = TikTokNeonPink,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Submit Proof Button
            Button(
                onClick = onSubmitProof,
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = JazzCashRed
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("submit_payment_proof_button")
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.CheckCircle,
                        contentDescription = "Submit",
                        tint = TikTokWhite,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Submit Payment Proof (${plan.pricePkr})",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = TikTokWhite
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Security,
                    contentDescription = "Secure",
                    tint = TikTokTextTertiary,
                    modifier = Modifier.size(12.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Encrypted JazzCash Verification • Instant Plan Unlock",
                    fontSize = 11.sp,
                    color = TikTokTextTertiary
                )
            }
        }
    }
}
