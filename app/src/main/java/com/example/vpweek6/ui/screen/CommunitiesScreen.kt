package com.example.vpweek6.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vpweek6.ui.widgets.AppBlue
import com.example.vpweek6.ui.widgets.AppGray
import com.example.vpweek6.ui.widgets.AppRed
import com.example.vpweek6.ui.widgets.CommunityIconBox
import com.example.vpweek6.ui.widgets.PageBackground
import com.example.vpweek6.ui.widgets.whiteCardColors
import com.example.vpweek6.viewmodel.CampusViewModel

@Composable
fun CommunitiesScreen(
    onOpenCommunityProjects: (String) -> Unit,
    campusViewModel: CampusViewModel = viewModel()
) {
    val communityList by campusViewModel.communities.collectAsStateWithLifecycle()
    val warningMessage by campusViewModel.warningMessage.collectAsStateWithLifecycle()

    Box(modifier = Modifier
        .fillMaxSize()
        .background(PageBackground)) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 80.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            "Communities Directory",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            "Pilih komunitas untuk membuka Club Projects",
                            fontSize = 11.sp,
                            color = AppGray
                        )
                    }
                    Icon(Icons.Default.Search, contentDescription = "Search")
                }
            }

            items(communityList, key = { community -> community.id }) { community ->
                Card(
                    onClick = {
                        campusViewModel.onCommunityClick(
                            community,
                            onOpenCommunityProjects
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = whiteCardColors()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            CommunityIconBox(community.id)
                            Column(modifier = Modifier.padding(start = 12.dp)) {
                                Text(community.name, fontWeight = FontWeight.Bold)
                                Text(
                                    "${community.subtitle} • ${community.memberCount} Mahasiswa",
                                    fontSize = 11.sp,
                                    color = AppGray
                                )
                            }
                        }
                        Text(
                            community.description,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(vertical = 10.dp)
                        )
                        HorizontalDivider()
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                modifier = Modifier.weight(1f),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = if (community.isJoined) Icons.Default.LockOpen else Icons.Default.Lock,
                                    contentDescription = null,
                                    tint = if (community.isJoined) AppBlue else AppGray,
                                    modifier = Modifier.size(14.dp)
                                )
                                Text(
                                    text = if (community.isJoined) "Unlocked • Buka Proyek →" else "Terkunci (Gate Akses)",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (community.isJoined) AppBlue else AppGray,
                                    modifier = Modifier.padding(start = 4.dp)
                                )
                            }
                            if (community.isJoined) {
                                OutlinedButton(
                                    onClick = { campusViewModel.toggleMembership(community.id) },
                                    shape = RoundedCornerShape(20.dp),
                                    border = BorderStroke(1.dp, AppRed),
                                    colors = ButtonDefaults.outlinedButtonColors(contentColor = AppRed)
                                ) {
                                    Icon(
                                        Icons.Default.Check,
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text("Joined", modifier = Modifier.padding(start = 4.dp))
                                }
                            } else {
                                Button(
                                    onClick = { campusViewModel.toggleMembership(community.id) },
                                    shape = RoundedCornerShape(20.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = AppBlue)
                                ) {
                                    Icon(
                                        Icons.Default.Add,
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text("Join", modifier = Modifier.padding(start = 4.dp))
                                }
                            }
                        }
                    }
                }
            }
        }

        if (warningMessage != null) {
            Snackbar(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
                action = {
                    TextButton(onClick = { campusViewModel.clearWarning() }) {
                        Text("OK")
                    }
                }
            ) {
                Text(warningMessage ?: "")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CommunitiesScreenPreview() {
    CommunitiesScreen(onOpenCommunityProjects = {})
}