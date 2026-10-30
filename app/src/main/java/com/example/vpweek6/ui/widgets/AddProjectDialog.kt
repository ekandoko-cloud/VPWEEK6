package com.example.vpweek6.ui.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties

@Composable
fun AddProjectDialog(
    communityName: String,
    technologyOptions: List<String>,
    projectTitle: String,
    projectDescription: String,
    selectedTechnologies: Set<String>,
    onProjectTitleChange: (String) -> Unit,
    onProjectDescriptionChange: (String) -> Unit,
    onTechnologyToggle: (String) -> Unit,
    onSave: () -> Unit,
    onCancel: () -> Unit
) {
    val isFormValid = projectTitle.isNotBlank() && projectDescription.isNotBlank() && selectedTechnologies.isNotEmpty()

    AlertDialog(
        onDismissRequest = {},
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false),
        shape = RoundedCornerShape(20.dp),
        containerColor = Color.White,
        title = {
            Column {
                Text("Add Project", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                Text("Komunitas: $communityName", fontSize = 12.sp, color = AppGray)
            }
        },
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = projectTitle,
                    onValueChange = onProjectTitleChange,
                    label = { Text("Judul Proyek *", color = Color.Black) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = projectDescription,
                    onValueChange = onProjectDescriptionChange,
                    label = { Text("Deskripsi Proyek (Minimal 3 Baris) *", color = Color.Black) },
                    minLines = 3,
                    modifier = Modifier.fillMaxWidth()
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Tech Stack (Pilih Multi) *", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                    Text("${selectedTechnologies.size} Dipilih", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = AppBlue)
                }
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    for (rowItems in technologyOptions.chunked(2)) {
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            for (technology in rowItems) {
                                val isSelected = technology in selectedTechnologies
                                Card(
                                    onClick = { onTechnologyToggle(technology) },
                                    shape = RoundedCornerShape(16.dp),
                                    colors = CardDefaults.cardColors(containerColor = if (isSelected) AppLightBlue else Color.White),
                                    border = BorderStroke(1.dp, if (isSelected) AppBlue else AppGray)
                                ) {
                                    Text(
                                        text = if (isSelected) "✓  $technology" else "+  $technology",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = if (isSelected) AppBlue else Color.Black,
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onSave,
                enabled = isFormValid,
                colors = ButtonDefaults.buttonColors(containerColor = AppBlue)
            ) {
                Text("Save", color = Color.Black)
            }
        },
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text("Cancel", color = Color.Black)
            }
        }
    )
}