package com.example.vpweek6.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val AppBlue = Color(0xFF1F4FD8)
val AppLightBlue = Color(0xFFE8EFFF)
val AppRed = Color(0xFFD32F2F)
val AppLightRed = Color(0xFFFDECEC)
val AppGreen = Color(0xFF2E7D32)
val AppLightGreen = Color(0xFFE3F5E6)
val AppOrange = Color(0xFFB26A00)
val AppLightYellow = Color(0xFFFFF1C9)
val AppPurple = Color(0xFF7C3AED)
val AppLightPurple = Color(0xFFEFE8FF)
val AppGray = Color(0xFF6B7280)
val AppLightGray = Color(0xFFF1F3F9)
val AppDark = Color(0xFF111827)
val PageBackground = Color(0xFFF5F7FB)

@Composable
fun whiteCardColors() = CardDefaults.cardColors(containerColor = Color.White)

@Composable
fun IconBox(icon: ImageVector, iconColor: Color, backgroundColor: Color) {
    Box(
        modifier = Modifier
            .size(44.dp)
            .background(backgroundColor, RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        Icon(icon, contentDescription = null, tint = iconColor)
    }
}

@Composable
fun CommunityIconBox(communityId: String) {
    when (communityId) {
        "gdsc" -> IconBox(Icons.Default.Code, AppBlue, AppLightBlue)
        "cyber" -> IconBox(Icons.Default.Security, AppRed, AppLightRed)
        "ai" -> IconBox(Icons.Default.AutoAwesome, AppPurple, AppLightPurple)
        "mobile" -> IconBox(Icons.Default.PhoneAndroid, AppBlue, AppLightBlue)
        "data" -> IconBox(Icons.Default.BarChart, AppGreen, AppLightGreen)
        else -> IconBox(Icons.Default.SportsEsports, AppOrange, AppLightYellow)
    }
}

@Composable
fun StatusBadge(isCompleted: Boolean) {
    Text(
        text = if (isCompleted) "Completed" else "In Progress",
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        color = if (isCompleted) AppGreen else AppOrange,
        modifier = Modifier
            .background(
                if (isCompleted) AppLightGreen else AppLightYellow,
                RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 8.dp, vertical = 3.dp)
    )
}

@Composable
fun JoinedBadge() {
    Text(
        text = "Joined ✓",
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        color = AppRed,
        modifier = Modifier
            .background(AppLightRed, RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 3.dp)
    )
}