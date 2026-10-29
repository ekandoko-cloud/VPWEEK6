package com.example.vpweek6.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vpweek6.viewmodel.CampusViewModel

@Composable
fun CommunitiesScreen(
    onOpenCommunityProjects: (String) -> Unit,
    campusViewModel: CampusViewModel = viewModel()
) {
    val communityList by campusViewModel.communities.collectAsStateWithLifecycle()
    val warningMessage by campusViewModel.warningMessage.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                "Communities Directory",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text("Pilih komunitas untuk membuka Club Projects", style = MaterialTheme.typography.bodySmall)
        }

        items(communityList, key = { community -> community.id }) { community ->
            Card(
                onClick = {
                    campusViewModel.onCommunityClick(community, onOpenCommunityProjects)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(community.name, fontWeight = FontWeight.Bold)
                    Text(
                        "${community.subtitle} • ${community.memberCount} Mahasiswa",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        community.description,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    HorizontalDivider()
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (community.isJoined) "Unlocked • Buka Proyek →" else "Terkunci (Gate Akses)",
                            style = MaterialTheme.typography.labelMedium,
                            color = if (community.isJoined) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.outline,
                            modifier = Modifier.weight(1f)
                        )
                        if (community.isJoined) {
                            OutlinedButton(
                                onClick = { campusViewModel.toggleMembership(community.id) },
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.error),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    contentColor = MaterialTheme.colorScheme.error
                                )
                            ) { Text("✓ Joined") }
                        } else {
                            Button(onClick = { campusViewModel.toggleMembership(community.id) }) {
                                Text("+ Join")
                            }
                        }
                    }
                }
            }
        }
    }

    if (warningMessage != null) {
        AlertDialog(
            onDismissRequest = { campusViewModel.clearWarning() },
            title = { Text("Akses Terkunci", fontWeight = FontWeight.Bold) },
            text = { Text(warningMessage!!) },
            confirmButton = {
                TextButton(onClick = { campusViewModel.clearWarning() }) {
                    Text("OK")
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CommunitiesScreenPreview() {
    CommunitiesScreen(
        onOpenCommunityProjects = {}
    )
}
