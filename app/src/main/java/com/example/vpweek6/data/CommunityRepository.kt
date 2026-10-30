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
                false
            ),
            Community(
                "cyber",
                "CyberSecurity Guild",
                "Security & Pentesting",
                "Eksplorasi ethical hacking, CTF challenges, reverse engineering, dan pertahanan jaringan kampus.",
                88,
                false
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
                false
            ),
            Community(
                "network",
                "Cisco Community",
                "Networking & Security",
                "Belajar tentang jaringan komputer, keamanan, dan solusi jaringan dari Cisco.",
                70,
                false
            ),
            Community(
                "web",
                "Web Development Community",
                "Laravel and React JS",
                "Membuat aplikasi web dengan Laravel dan React JS.",
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