package com.example.vpweek6.model

data class Project(
    val id: String,
    val communityId: String,
    val title: String,
    val description: String,
    val technologyList: List<String>,
    val isCompleted: Boolean = false
)
