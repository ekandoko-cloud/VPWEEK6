package com.example.vpweek6.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vpweek6.ui.widgets.SimpleTopBar
import com.example.vpweek6.ui.widgets.TechStackRow
import com.example.vpweek6.viewmodel.CampusViewModel

@Composable
fun ProjectDetailScreen(
    projectId: String,
    onBack: () -> Unit,
    campusViewModel: CampusViewModel = viewModel()
) {
    val communityList by campusViewModel.communities.collectAsStateWithLifecycle()
    val projectList by campusViewModel.projects.collectAsStateWithLifecycle()
    val selectedProject = projectList.find { it.id == projectId }
    val communityName = if (selectedProject != null) {
        communityList.find { it.id == selectedProject.communityId }?.name.orEmpty()
    } else {
        ""
    }

    Scaffold(
        topBar = {
            SimpleTopBar(topBarTitle = "Project Detail", onBack = onBack)
        }
    ) { innerPadding ->
        if (selectedProject == null) {
            Text(
                "Proyek tidak ditemukan",
                modifier = Modifier.padding(innerPadding).padding(16.dp)
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.inverseSurface
                        )
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            Text(
                                communityName,
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.inverseOnSurface
                            )
                            Text(
                                selectedProject.title,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.inverseOnSurface
                            )
                        }
                    }
                }

                item {
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text("Mark as Done", fontWeight = FontWeight.Bold)
                                Text(
                                    "Status: ${if (selectedProject.isCompleted) "Completed" else "In Progress"}",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                            Switch(
                                checked = selectedProject.isCompleted,
                                onCheckedChange = { campusViewModel.toggleStatus(projectId) }
                            )
                        }
                    }
                }

                item {
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Deskripsi Lengkap Proyek", fontWeight = FontWeight.Bold)
                            Text(
                                selectedProject.description,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                    }
                }

                item {
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Tech Stack Terpilih", fontWeight = FontWeight.Bold)
                            Column(modifier = Modifier.padding(top = 8.dp)) {
                                TechStackRow(selectedProject.technologyList)
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
private fun ProjectDetailScreenPreview() {
    ProjectDetailScreen(
        projectId = "p1",
        onBack = {}
    )
}
