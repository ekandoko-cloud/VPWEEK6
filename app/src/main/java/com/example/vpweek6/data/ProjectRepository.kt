package com.example.vpweek6.data

import com.example.vpweek6.model.Project
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object ProjectRepository {
    val techOptions =
        listOf("Kotlin", "Jetpack Compose", "Firebase", "Ktor", "Room", "Python", "Google Maps SDK")
    private var nextId = 1
    private val _projects = MutableStateFlow(emptyList<Project>())
    val projects: StateFlow<List<Project>> = _projects.asStateFlow()

    fun addProject(
        communityId: String,
        title: String,
        description: String,
        technologyList: List<String>
    ) {
        val project = Project("p$nextId", communityId, title, description, technologyList)
        nextId++
        _projects.value += project
    }

    fun deleteProject(projectId: String) {
        _projects.value = _projects.value.filter { it.id != projectId }
    }

    fun toggleStatus(projectId: String) {
        _projects.value = _projects.value.map {
            if (it.id == projectId) it.copy(isCompleted = !it.isCompleted) else it
        }
    }
}