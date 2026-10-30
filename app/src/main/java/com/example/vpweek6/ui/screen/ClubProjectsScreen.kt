package com.example.vpweek6.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vpweek6.ui.widgets.AddProjectDialog
import com.example.vpweek6.ui.widgets.AppBlue
import com.example.vpweek6.ui.widgets.AppGray
import com.example.vpweek6.ui.widgets.AppLightBlue
import com.example.vpweek6.ui.widgets.AppRed
import com.example.vpweek6.ui.widgets.CommunityIconBox
import com.example.vpweek6.ui.widgets.JoinedBadge
import com.example.vpweek6.ui.widgets.PageBackground
import com.example.vpweek6.ui.widgets.StatusBadge
import com.example.vpweek6.ui.widgets.TechStackRow
import com.example.vpweek6.ui.widgets.TopBar
import com.example.vpweek6.ui.widgets.whiteCardColors
import com.example.vpweek6.viewmodel.CampusViewModel

@Composable
fun ClubProjectsScreen(
    communityId: String,
    onBack: () -> Unit,
    onProjectClick: (String) -> Unit,
    campusViewModel: CampusViewModel = viewModel()
) {
    val communityList by campusViewModel.communities.collectAsStateWithLifecycle()
    val projectList by campusViewModel.projects.collectAsStateWithLifecycle()
    val isDialogVisible by campusViewModel.isAddProjectDialogVisible.collectAsStateWithLifecycle()
    val projectTitleInput by campusViewModel.projectTitleInput.collectAsStateWithLifecycle()
    val projectDescriptionInput by campusViewModel.projectDescriptionInput.collectAsStateWithLifecycle()
    val selectedTechnologies by campusViewModel.selectedTechnologies.collectAsStateWithLifecycle()
    val community = communityList.find { it.id == communityId }
    val communityName = community?.name ?: ""
    val filteredProjects = projectList.filter { it.communityId == communityId }

    Scaffold(
        containerColor = PageBackground,
        topBar = {
            TopBar(
                topBarTitle = "$communityName Projects",
                onBack = onBack,
                subtitle = community?.subtitle ?: ""
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { campusViewModel.openAddProjectDialog() },
                containerColor = AppBlue,
                contentColor = Color.White,
                icon = { Icon(Icons.Default.Add, contentDescription = null) },
                text = { Text("Add Project") }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 88.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (community != null) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = AppLightBlue)
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    community.subtitle,
                                    fontWeight = FontWeight.Bold,
                                    color = AppBlue
                                )
                                Text(
                                    "${community.memberCount} Anggota • ${filteredProjects.size} Proyek Aktif",
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
                Text(
                    "▢ Daftar Proyek Mahasiswa (${filteredProjects.size})",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            if (filteredProjects.isEmpty()) {
                item { Text("Belum ada proyek yang dicatat", color = AppGray) }
            } else {
                items(filteredProjects, key = { project -> project.id }) { project ->
                    Card(
                        onClick = { onProjectClick(project.id) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = whiteCardColors()
                    ) {
                        Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.Top) {
                            CommunityIconBox(project.communityId)
                            Column(modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 12.dp)) {
                                Text(project.title, fontWeight = FontWeight.Bold)
                                Text(
                                    project.description,
                                    fontSize = 11.sp,
                                    color = AppGray,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Column(modifier = Modifier.padding(vertical = 6.dp)) {
                                    TechStackRow(project.technologyList)
                                }
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        "ID: ${project.id}",
                                        fontSize = 10.sp,
                                        color = AppGray,
                                        modifier = Modifier.weight(1f)
                                    )
                                    StatusBadge(project.isCompleted)
                                }
                            }
                            IconButton(onClick = { campusViewModel.deleteProject(project.id) }) {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = "Delete",
                                    tint = AppRed
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    if (isDialogVisible) {
        AddProjectDialog(
            communityName = "$communityName (${community?.subtitle ?: ""})",
            technologyOptions = campusViewModel.techOptions,
            projectTitle = projectTitleInput,
            projectDescription = projectDescriptionInput,
            selectedTechnologies = selectedTechnologies,
            onProjectTitleChange = { campusViewModel.onProjectTitleChange(it) },
            onProjectDescriptionChange = { campusViewModel.onProjectDescriptionChange(it) },
            onTechnologyToggle = { campusViewModel.toggleTechnology(it) },
            onSave = { campusViewModel.saveProject(communityId) },
            onCancel = { campusViewModel.closeAddProjectDialog() }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ClubProjectsScreenPreview() {
    ClubProjectsScreen(communityId = "gdsc", onBack = {}, onProjectClick = {})
}