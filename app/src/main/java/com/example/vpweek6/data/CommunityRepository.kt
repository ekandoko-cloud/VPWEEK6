package com.example.vpweek6.data

import com.example.vpweek6.model.Community
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object CommunityRepository {
    private val _communities = MutableStateFlow(
        listOf(
            Community(
                "gdsc",
                "GDSC",
                "Google Developer Student Club",
                "Fokus pada ekosistem Google: Android Compose, Flutter, Firebase, dan Google Cloud Platform.",
                142,
                true
            ),
            Community(
                "cyber",
                "CyberSecurity Guild",
                "Security & Pentesting",
                "Eksplorasi ethical hacking, CTF challenges, reverse engineering, dan pertahanan jaringan kampus.",
                88,
                true
            ),
            Community(
                "ai",
                "AI Society",
                "Machine Learning & LLM",
                "Riset terapan deep learning, computer vision, agen cerdas, dan fine-tuning large language models.",
                115,
                false
            ),
            Community(
                "mobile",
                "Mobile Dev Community",
                "Jetpack Compose & KMP",
                "Native Jetpack Compose dan iOS, serta Kotlin Multiplatform untuk aplikasi lintas platform.",
                95,
                true
            ),
            Community(
                "data",
                "Data Science Club",
                "Data Analysis & Visualization",
                "Belajar analisis data, visualisasi, dan statistik dengan Python.",
                70,
                false
            ),
            Community(
                "game",
                "Game Dev Club",
                "Unity & Game Design",
                "Membuat game bersama, dari desain, pemrograman, sampai publikasi.",
                64,
                false
            )
        )
    )
    val communities: StateFlow<List<Community>> = _communities.asStateFlow()

    fun toggleJoin(communityId: String) {
        _communities.value = _communities.value.map {
            if (it.id == communityId) it.copy(isJoined = !it.isJoined) else it
        }
    }
}