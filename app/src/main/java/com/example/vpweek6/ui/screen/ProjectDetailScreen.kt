package com.example.vpweek6.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vpweek6.ui.widgets.AppBlue
import com.example.vpweek6.ui.widgets.AppDark
import com.example.vpweek6.ui.widgets.AppGray
import com.example.vpweek6.ui.widgets.PageBackground
import com.example.vpweek6.ui.widgets.StatusBadge
import com.example.vpweek6.ui.widgets.TechStackRow
import com.example.vpweek6.ui.widgets.TopBar
import com.example.vpweek6.ui.widgets.whiteCardColors
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
    val communityName = communityList.find { it.id == selectedProject?.communityId }?.name ?: ""

    Scaffold(
        containerColor = PageBackground,
        topBar = { TopBar(topBarTitle = "Project Detail", onBack = onBack) }
    ) { innerPadding ->
        if (selectedProject == null) {
            Text("Proyek tidak ditemukan", color = Color.Black, modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp))
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
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = AppDark)
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            Text(
                                communityName,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier
                                    .background(AppBlue, RoundedCornerShape(8.dp))
                                    .padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                            Text(
                                selectedProject.title,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier.padding(top = 12.dp)
                            )
                        }
                    }
                }

                item {
                    Card(modifier = Modifier.fillMaxWidth(), colors = whiteCardColors()) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text("Mark as Done", color = Color.Black, fontWeight = FontWeight.Bold)
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(top = 4.dp)
                                ) {
                                    Text("Status: ", fontSize = 11.sp, color = AppGray)
                                    StatusBadge(selectedProject.isCompleted)
                                }
                            }
                            Switch(
                                checked = selectedProject.isCompleted,
                                onCheckedChange = { campusViewModel.toggleStatus(projectId) }
                            )
                        }
                    }
                }

                item {
                    Card(modifier = Modifier.fillMaxWidth(), colors = whiteCardColors()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Deskripsi Lengkap Proyek", color = Color.Black, fontWeight = FontWeight.Bold)
                            Text(
                                selectedProject.description,
                                fontSize = 12.sp,
                                color = AppGray,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                    }
                }

                item {
                    Card(modifier = Modifier.fillMaxWidth(), colors = whiteCardColors()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Tech Stack Terpilih", color = Color.Black, fontWeight = FontWeight.Bold)
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
    ProjectDetailScreen(projectId = "p1", onBack = {})
}