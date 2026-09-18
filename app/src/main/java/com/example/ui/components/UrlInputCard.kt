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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContentPaste
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.TikTokCardBg
import com.example.ui.theme.TikTokCyan
import com.example.ui.theme.TikTokDarkBg
import com.example.ui.theme.TikTokNeonPink
import com.example.ui.theme.TikTokSurface
import com.example.ui.theme.TikTokSurfaceElevated
import com.example.ui.theme.TikTokTextSecondary
import com.example.ui.theme.TikTokTextTertiary
import com.example.ui.theme.TikTokWhite

@Composable
fun UrlInputCard(
    url: String,
    onUrlChange: (String) -> Unit,
    onSubmitLink: () -> Unit,
    isSubmitted: Boolean,
    modifier: Modifier = Modifier
) {
    val clipboardManager = LocalClipboardManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val sampleLinks = listOf(
        "tiktok.com/@viral.creator/video/72910384",
        "https://vt.tiktok.com/ZSjX99A/",
        "@trending.user"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(TikTokCardBg)
            .border(1.dp, if (isSubmitted) TikTokCyan.copy(alpha = 0.6f) else TikTokSurfaceElevated, RoundedCornerShape(20.dp))
            .padding(18.dp)
            .testTag("url_input_card")
    ) {
        // Step header
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
                        .background(TikTokNeonPink.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "1",
                        color = TikTokNeonPink,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "TikTok Target URL or Profile",
                        fontWeight = FontWeight.Bold,
                        color = TikTokWhite,
                        fontSize = 15.sp
                    )
                    Text(
                        text = "Paste your video link or username to boost",
                        color = TikTokTextSecondary,
                        fontSize = 12.sp
                    )
                }
            }

            if (isSubmitted && url.isNotBlank()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(TikTokCyan.copy(alpha = 0.15f))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.CheckCircle,
                        contentDescription = "Verified Link",
                        tint = TikTokCyan,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Ready",
                        color = TikTokCyan,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Input Field with Paste action
        OutlinedTextField(
            value = url,
            onValueChange = onUrlChange,
            placeholder = {
                Text(
                    text = "https://www.tiktok.com/@username/video/...",
                    color = TikTokTextTertiary,
                    fontSize = 13.sp
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Link,
                    contentDescription = "Link Icon",
                    tint = if (url.isNotBlank()) TikTokNeonPink else TikTokTextSecondary
                )
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        val clip = clipboardManager.getText()?.text
                        if (!clip.isNullOrBlank()) {
                            onUrlChange(clip.trim())
                        }
                    },
                    modifier = Modifier.testTag("paste_button")
                ) {
                    Icon(
                        imageVector = Icons.Filled.ContentPaste,
                        contentDescription = "Paste from Clipboard",
                        tint = TikTokCyan
                    )
                }
            },
            singleLine = true,
            maxLines = 1,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Uri,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                    if (url.isNotBlank()) onSubmitLink()
                }
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = TikTokDarkBg,
                unfocusedContainerColor = TikTokDarkBg,
                focusedBorderColor = TikTokNeonPink,
                unfocusedBorderColor = TikTokSurfaceElevated,
                focusedTextColor = TikTokWhite,
                unfocusedTextColor = TikTokWhite
            ),
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("tiktok_url_input")
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Sample Quick Chips
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Quick:",
                fontSize = 11.sp,
                color = TikTokTextTertiary,
                fontWeight = FontWeight.SemiBold
            )
            sampleLinks.take(2).forEach { sample ->
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(TikTokSurface)
                        .clickable { onUrlChange(sample) }
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = if (sample.startsWith("@")) sample else "Sample Link",
                        fontSize = 11.sp,
                        color = TikTokTextSecondary
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Submit Link Button
        Button(
            onClick = {
                keyboardController?.hide()
                onSubmitLink()
            },
            enabled = url.isNotBlank(),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .testTag("submit_link_button"),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = TikTokNeonPink,
                disabledContainerColor = TikTokSurfaceElevated
            )
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.Send,
                    contentDescription = "Submit Link",
                    tint = TikTokWhite,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (isSubmitted) "Link Confirmed • Change Link" else "Submit / Verify Link",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = TikTokWhite
                )
            }
        }
    }
}
