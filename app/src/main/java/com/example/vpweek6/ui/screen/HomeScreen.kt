package com.example.vpweek6.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.CropSquare
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vpweek6.R
import com.example.vpweek6.ui.widgets.AppBlue
import com.example.vpweek6.ui.widgets.AppGray
import com.example.vpweek6.ui.widgets.AppLightBlue
import com.example.vpweek6.ui.widgets.AppLightPurple
import com.example.vpweek6.ui.widgets.AppPurple
import com.example.vpweek6.ui.widgets.CommunityIconBox
import com.example.vpweek6.ui.widgets.IconBox
import com.example.vpweek6.ui.widgets.JoinedBadge
import com.example.vpweek6.ui.widgets.PageBackground
import com.example.vpweek6.ui.widgets.StatusBadge
import com.example.vpweek6.ui.widgets.whiteCardColors
import com.example.vpweek6.viewmodel.CampusViewModel

@Composable
fun HomeScreen(campusViewModel: CampusViewModel = viewModel()) {
    val communityList by campusViewModel.communities.collectAsStateWithLifecycle()
    val projectList by campusViewModel.projects.collectAsStateWithLifecycle()
    val student = campusViewModel.studentProfile
    val joinedClubs = communityList.filter { it.isJoined }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(PageBackground),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("CampusTech Hub", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                    Text("Student Profile & Portfolio Dashboard", fontSize = 11.sp, color = AppGray)
                }
                Image(
                    painter = painterResource(R.drawable.stevem),
                    contentDescription = "Foto profil",
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.horizontalGradient(
                                listOf(
                                    Color(0xFF1E3FA8),
                                    Color(0xFF3B82F6)
                                )
                            )
                        )
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.stevem),
                        contentDescription = "Foto profil ${student.name}",
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Column(modifier = Modifier.padding(start = 16.dp)) {
                        Text(
                            "MAHASISWA AKTIF",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier
                                .background(
                                    Color.White.copy(alpha = 0.25f),
                                    RoundedCornerShape(8.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                        Text(
                            student.name,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                        Text(
                            "${student.major} • Angkatan ${student.enrollmentYear}",
                            fontSize = 12.sp,
                            color = Color.White
                        )
                        Text("NIM: ${student.studentId}", fontSize = 12.sp, color = Color.White)
                    }
                }
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Card(modifier = Modifier.weight(1f), colors = whiteCardColors()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        IconBox(Icons.Default.Group, AppBlue, AppLightBlue)
                        if (joinedClubs.isEmpty()) {
                            Text(
                                "Belum bergabung dengan komunitas",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        } else {
                            Text(
                                joinedClubs.size.toString(),
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                            Text("Joined Clubs", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                            Text("Komunitas yang Diikuti", fontSize = 11.sp, color = AppGray)
                        }
                    }
                }
                Card(modifier = Modifier.weight(1f), colors = whiteCardColors()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        IconBox(Icons.Default.ContentCopy, AppPurple, AppLightPurple)
                        if (projectList.isEmpty()) {
                            Text(
                                "Belum ada proyek yang dicatat",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        } else {
                            Text(
                                projectList.size.toString(),
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                            Text("Total Projects", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                            Text("Seluruh Proyek Dibuat", fontSize = 11.sp, color = AppGray)
                        }
                    }
                }
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = null,
                        tint = AppBlue,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        "Semua Komunitas yang Diikuti (${joinedClubs.size})",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
                Text(
                    "Status: Joined",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppBlue
                )
            }
        }
        if (joinedClubs.isEmpty()) {
            item { Text("Belum bergabung dengan komunitas", color = AppGray) }
        } else {
            items(joinedClubs, key = { club -> club.id }) { club ->
                val projectCount = projectList.count { it.communityId == club.id }
                Card(modifier = Modifier.fillMaxWidth(), colors = whiteCardColors()) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CommunityIconBox(club.id)
                        Column(modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 12.dp)) {
                            Text(club.name, fontWeight = FontWeight.Bold, color = Color.Black)
                            Text(
                                "${club.subtitle} • $projectCount Proyek",
                                fontSize = 11.sp,
                                color = AppGray
                            )
                        }
                        JoinedBadge()
                    }
                }
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.CropSquare,
                        contentDescription = null,
                        tint = AppPurple,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        "Semua Proyek Mahasiswa (${projectList.size})",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
                Text(
                    "Total: ${projectList.size} Proyek",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppBlue
                )
            }
        }
        if (projectList.isEmpty()) {
            item { Text("Belum ada proyek yang dicatat", color = AppGray) }
        } else {
            items(projectList, key = { project -> project.id }) { project ->
                val communityName = communityList.find { it.id == project.communityId }?.name ?: ""
                Card(modifier = Modifier.fillMaxWidth(), colors = whiteCardColors()) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CommunityIconBox(project.communityId)
                        Column(modifier = Modifier
                            .weight(1f)
                            .padding(start = 12.dp)) {
                            Text(
                                communityName,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = AppBlue
                            )
                            Text(project.title, fontWeight = FontWeight.Bold, color = Color.Black)
                            Text(
                                project.technologyList.joinToString(" • "),
                                fontSize = 11.sp,
                                color = AppGray
                            )
                            Box(modifier = Modifier.padding(top = 6.dp)) {
                                StatusBadge(project.isCompleted)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    HomeScreen()
}