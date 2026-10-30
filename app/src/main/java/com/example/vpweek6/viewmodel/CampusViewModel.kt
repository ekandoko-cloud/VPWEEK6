package com.example.vpweek6.viewmodel

import androidx.lifecycle.ViewModel
import com.example.vpweek6.data.CommunityRepository
import com.example.vpweek6.data.ProjectRepository
import com.example.vpweek6.data.StudentRepository
import com.example.vpweek6.model.Community
import com.example.vpweek6.model.Project
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CampusViewModel : ViewModel() {
    val studentProfile = StudentRepository.student
    val communities: StateFlow<List<Community>> = CommunityRepository.communities
    val projects: StateFlow<List<Project>> = ProjectRepository.projects
    val techOptions: List<String> = ProjectRepository.techOptions
    private val _isAddProjectDialogVisible = MutableStateFlow(false)
    val isAddProjectDialogVisible: StateFlow<Boolean> = _isAddProjectDialogVisible.asStateFlow()
    private val _projectTitleInput = MutableStateFlow("")
    val projectTitleInput: StateFlow<String> = _projectTitleInput.asStateFlow()
    private val _projectDescriptionInput = MutableStateFlow("")
    val projectDescriptionInput: StateFlow<String> = _projectDescriptionInput.asStateFlow()
    private val _selectedTechnologies = MutableStateFlow<Set<String>>(emptySet())
    val selectedTechnologies: StateFlow<Set<String>> = _selectedTechnologies.asStateFlow()
    private val _warningMessage = MutableStateFlow<String?>(null)
    val warningMessage: StateFlow<String?> = _warningMessage.asStateFlow()

    fun toggleMembership(communityId: String) {
        CommunityRepository.toggleJoin(communityId)
    }

    fun onCommunityClick(community: Community, onOpenCommunityProjects: (String) -> Unit) {
        if (community.isJoined) {
            onOpenCommunityProjects(community.id)
        } else {
            _warningMessage.value = "Harap bergabung ke komunitas terlebih dahulu!"
        }
    }

    fun clearWarning() {
        _warningMessage.value = null
    }

    fun openAddProjectDialog() {
        _projectTitleInput.value = ""
        _projectDescriptionInput.value = ""
        _selectedTechnologies.value = emptySet()
        _isAddProjectDialogVisible.value = true
    }

    fun closeAddProjectDialog() {
        _isAddProjectDialogVisible.value = false
    }

    fun onProjectTitleChange(title: String) {
        _projectTitleInput.value = title
    }

    fun onProjectDescriptionChange(description: String) {
        _projectDescriptionInput.value = description
    }

    fun toggleTechnology(technology: String) {
        val current = _selectedTechnologies.value
        _selectedTechnologies.value =
            if (technology in current) current - technology else current + technology
    }

    fun saveProject(communityId: String) {
        val title = _projectTitleInput.value
        val description = _projectDescriptionInput.value
        val selected = _selectedTechnologies.value
        if (title.isBlank() || description.isBlank() || selected.isEmpty()) return
        val technologyList = techOptions.filter { it in selected }
        ProjectRepository.addProject(communityId, title.trim(), description.trim(), technologyList)
        _isAddProjectDialogVisible.value = false
    }

    fun deleteProject(projectId: String) {
        ProjectRepository.deleteProject(projectId)
    }

    fun toggleStatus(projectId: String) {
        ProjectRepository.toggleStatus(projectId)
    }
}