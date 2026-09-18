package com.example.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.ui.graphics.vector.ImageVector

enum class PlanType(
    val title: String,
    val priceUsd: String,
    val pricePkr: String,
    val rawPkr: Int,
    val rawUsd: Int,
    val period: String,
    val maxPerBoost: Int,
    val isUnlimited: Boolean,
    val speedTitle: String,
    val badge: String,
    val features: List<String>
) {
    FREE(
        title = "Free Tier",
        priceUsd = "$0",
        pricePkr = "PKR 0",
        rawPkr = 0,
        rawUsd = 0,
        period = "Free Forever",
        maxPerBoost = 100,
        isUnlimited = false,
        speedTitle = "Standard Delivery",
        badge = "FREE",
        features = listOf(
            "Up to 100 Likes / Views per request",
            "1 free boost per day",
            "Standard delivery speed (2-4 hrs)",
            "Public delivery server nodes",
            "Community support"
        )
    ),
    VIP(
        title = "VIP Tier",
        priceUsd = "$10",
        pricePkr = "PKR 2,800",
        rawPkr = 2800,
        rawUsd = 10,
        period = "per month",
        maxPerBoost = 5000,
        isUnlimited = true,
        speedTitle = "Fast Priority Queue",
        badge = "VIP POPULAR",
        features = listOf(
            "Up to 5,000 Likes or Views per request",
            "Unlimited daily boost requests",
            "Fast priority processing (5-15 min)",
            "High-retention genuine delivery nodes",
            "Followers & Saves boost unlocked",
            "JazzCash verified priority activation"
        )
    ),
    PRO(
        title = "Pro Tier",
        priceUsd = "$20",
        pricePkr = "PKR 5,600",
        rawPkr = 5600,
        rawUsd = 20,
        period = "per month",
        maxPerBoost = 50000,
        isUnlimited = true,
        speedTitle = "Ultra-Fast Instant Turbo",
        badge = "PRO UNLIMITED",
        features = listOf(
            "Up to 50,000 Likes, Views or Followers",
            "Ultra-fast instant turbo delivery (<60s)",
            "Unlimited boosts & concurrent links",
            "24/7 Dedicated VIP Support manager",
            "SMM Panel direct API key access",
            "Zero drop guarantee & refill protection"
        )
    )
}

enum class ServiceType(
    val title: String,
    val description: String,
    val unit: String,
    val defaultQty: Int,
    val requiresVip: Boolean = false
) {
    LIKES(
        title = "Likes",
        description = "High retention organic video likes",
        unit = "Likes",
        defaultQty = 100,
        requiresVip = false
    ),
    VIEWS(
        title = "Views",
        description = "Instant FYP-optimized video views",
        unit = "Views",
        defaultQty = 500,
        requiresVip = false
    ),
    FOLLOWERS(
        title = "Followers",
        description = "Profile followers from active accounts",
        unit = "Followers",
        defaultQty = 250,
        requiresVip = true
    ),
    SAVES(
        title = "Saves / Bookmarks",
        description = "Increases TikTok algorithm ranking score",
        unit = "Saves",
        defaultQty = 100,
        requiresVip = true
    ),
    SHARES(
        title = "Shares",
        description = "Viral link shares to boost reach",
        unit = "Shares",
        defaultQty = 100,
        requiresVip = true
    );

    fun getIcon(): ImageVector = when (this) {
        LIKES -> Icons.Filled.Favorite
        VIEWS -> Icons.Filled.Visibility
        FOLLOWERS -> Icons.Filled.PersonAdd
        SAVES -> Icons.Filled.Bookmark
        SHARES -> Icons.Filled.Share
    }
}

enum class BoostStatus(val label: String) {
    QUEUED("Queued"),
    PROCESSING("Processing"),
    DELIVERED("Delivered"),
    COMPLETED("Completed")
}

data class BoostOrder(
    val id: String,
    val targetUrl: String,
    val serviceType: ServiceType,
    val quantity: Int,
    val planType: PlanType,
    val status: BoostStatus,
    val timestamp: Long = System.currentTimeMillis()
)

data class PaymentSubmission(
    val id: String,
    val planType: PlanType,
    val amountPkr: Int,
    val senderPhone: String,
    val transactionId: String,
    val screenshotUri: String? = null,
    val timestamp: Long = System.currentTimeMillis(),
    val status: String = "Pending Verification"
)
