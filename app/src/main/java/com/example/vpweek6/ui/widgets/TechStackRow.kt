package com.example.vpweek6.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TechStackRow(technologyList: List<String>) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        items(technologyList) { technology ->
            Text(
                text = technology,
                fontSize = 11.sp,
                modifier = Modifier
                    .background(AppLightGray, RoundedCornerShape(8.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }
    }
}