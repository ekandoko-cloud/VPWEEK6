package com.example.vpweek6.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vpweek6.R
import com.example.vpweek6.viewmodel.CampusViewModel

@Composable
fun HomeScreen(campusViewModel: CampusViewModel = viewModel()) {
    val communityList by campusViewModel.communities.collectAsStateWithLifecycle()
    val projectList by campusViewModel.projects.collectAsStateWithLifecycle()
    val student = campusViewModel.studentProfile
    val joinedCommunities = communityList.filter { it.isJoined }
    val joinedCommunityCount = joinedCommunities.size
    val totalProjectCount = projectList.size

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                "CampusTech Hub",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text("Student Profile & Portfolio Dashboard", style = MaterialTheme.typography.bodySmall)
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Row(
                    modifier = Modifier.padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.stevem),
                        contentDescription = "Foto profil ${student.name}",
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Column(modifier = Modifier.padding(start = 16.dp)) {
                        Text(
                            "MAHASISWA AKTIF",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                        Text(
                            student.name,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                        Text(
                            "${student.major} • Angkatan ${student.enrollmentYear}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                        Text(
                            "NIM: ${student.studentId}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
        }

        item {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Card(modifier = Modifier.weight(1f)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            joinedCommunityCount.toString(),
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text("Joined Clubs", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
                        Text("Komunitas yang Diikuti", style = MaterialTheme.typography.bodySmall)
                    }
                }
                Card(modifier = Modifier.weight(1f)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            totalProjectCount.toString(),
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text("Total Projects", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
                        Text("Seluruh Proyek Dibuat", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }

        item {
            Text(
                "Semua Komunitas yang Diikuti ($joinedCommunityCount)",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
        }
        if (joinedCommunities.isEmpty()) {
            item { Text("Belum bergabung dengan komunitas") }
        } else {
            items(joinedCommunities, key = { community -> community.id }) { community ->
                val projectCount = projectList.count { it.communityId == community.id }
                Card(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(community.name, fontWeight = FontWeight.Bold)
                            Text(
                                "${community.subtitle} • $projectCount Proyek",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                        Text("Joined ✓", color = MaterialTheme.colorScheme.error)
                    }
                }
            }
        }

        item {
            Text(
                "Semua Proyek Mahasiswa ($totalProjectCount)",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
        }
        if (projectList.isEmpty()) {
            item { Text("Belum ada proyek yang dicatat") }
        } else {
            items(projectList, key = { project -> project.id }) { project ->
                val communityName = communityList.find { it.id == project.communityId }?.name.orEmpty()
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            communityName,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(project.title, fontWeight = FontWeight.Bold)
                        Text(
                            project.technologyList.joinToString(" • "),
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            if (project.isCompleted) "Completed" else "In Progress",
                            style = MaterialTheme.typography.labelSmall
                        )
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
