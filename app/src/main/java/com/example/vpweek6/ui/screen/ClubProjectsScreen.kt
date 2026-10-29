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
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import com.example.vpweek6.ui.widgets.AddProjectDialog
import com.example.vpweek6.ui.widgets.SimpleTopBar
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
    val isAddProjectDialogVisible by campusViewModel.isAddProjectDialogVisible.collectAsStateWithLifecycle()
    val projectTitleInput by campusViewModel.projectTitleInput.collectAsStateWithLifecycle()
    val projectDescriptionInput by campusViewModel.projectDescriptionInput.collectAsStateWithLifecycle()
    val selectedTechnologies by campusViewModel.selectedTechnologies.collectAsStateWithLifecycle()
    val communityName = communityList.find { it.id == communityId }?.name.orEmpty()
    val filteredProjects = projectList.filter { it.communityId == communityId }

    Scaffold(
        topBar = {
            SimpleTopBar(topBarTitle = "$communityName Projects", onBack = onBack)
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = campusViewModel::openAddProjectDialog,
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
            item {
                Text(
                    "Daftar Proyek Mahasiswa (${filteredProjects.size})",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
            }

            if (filteredProjects.isEmpty()) {
                item { Text("Belum ada proyek yang dicatat") }
            } else {
                items(filteredProjects, key = { project -> project.id }) { project ->
                    Card(
                        onClick = { onProjectClick(project.id) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(project.title, fontWeight = FontWeight.Bold)
                                Text(
                                    project.description,
                                    style = MaterialTheme.typography.bodySmall,
                                    maxLines = 2
                                )
                                Text(
                                    project.technologyList.joinToString(" • "),
                                    style = MaterialTheme.typography.labelSmall,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                                Text(
                                    if (project.isCompleted) "Completed" else "In Progress",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                            IconButton(onClick = { campusViewModel.deleteProject(project.id) }) {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = "Delete",
                                    tint = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    if (isAddProjectDialogVisible) {
        AddProjectDialog(
            communityName = communityName,
            technologyOptions = campusViewModel.techOptions,
            projectTitle = projectTitleInput,
            projectDescription = projectDescriptionInput,
            selectedTechnologies = selectedTechnologies,
            onProjectTitleChange = campusViewModel::onProjectTitleChange,
            onProjectDescriptionChange = campusViewModel::onProjectDescriptionChange,
            onTechnologyToggle = campusViewModel::toggleTechnology,
            onSave = { campusViewModel.saveProject(communityId) },
            onCancel = campusViewModel::closeAddProjectDialog
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ClubProjectsScreenPreview() {
    ClubProjectsScreen(
        communityId = "gdsc",
        onBack = {},
        onProjectClick = {}
    )
}
