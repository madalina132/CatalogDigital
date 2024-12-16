package upt.ac.lab2

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class ComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val role = intent.getStringExtra("ROLE") ?: "unknown"
        setContent { LoginScreen(role, MainActivity.studentAssignments) }
    }

    @Composable
    private fun LoginScreen(
        role: String,
        studentAssignments: SnapshotStateMap<String, MutableList<String>>
    ) {
        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var isLoggedIn by remember { mutableStateOf(false) }

        if (!isLoggedIn) {
            Surface {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Login as $role",
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    TextField(
                        value = username,
                        onValueChange = { username = it },
                        label = { Text("Username") },
                        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                    )

                    TextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = { isLoggedIn = authenticate(username, password, role) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Login")
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { navigateBackToMain() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Back")
                    }
                }
            }
        } else {
            when (role) {
                "student" -> StudentDashboard(username, studentAssignments)
                "parent" -> ParentDashboard(studentAssignments)
                "teacher" -> TeacherDashboard(studentAssignments)
                else -> Text("Unknown role")
            }
        }
    }

    private fun authenticate(username: String, password: String, role: String): Boolean {
        return username.isNotEmpty() && password == "password123"
    }

    @Composable
    private fun StudentDashboard(
        studentName: String,
        assignments: SnapshotStateMap<String, MutableList<String>>
    ) {
        val studentAssignments = assignments[studentName] ?: emptyList()

        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top
            ) {
                Text("Welcome, $studentName!", style = MaterialTheme.typography.headlineLarge)
                Spacer(modifier = Modifier.height(16.dp))

                Text("Here are your assignments:")
                Spacer(modifier = Modifier.height(8.dp))

                LazyColumn {
                    items(studentAssignments.size) { index ->
                        Text("- ${studentAssignments[index]}")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { navigateBackToMain() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Back")
                }
            }
        }
    }

    @Composable
    private fun ParentDashboard(assignments: SnapshotStateMap<String, MutableList<String>>) {
        var childName by remember { mutableStateOf("") }
        val childAssignments = assignments[childName] ?: emptyList()

        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top
            ) {
                Text("Welcome, Parent!", style = MaterialTheme.typography.headlineLarge)
                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = childName,
                    onValueChange = { childName = it },
                    label = { Text("Enter your child's name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text("Assignments for $childName:")
                Spacer(modifier = Modifier.height(8.dp))

                LazyColumn {
                    items(childAssignments.size) { index ->
                        Text("- ${childAssignments[index]}")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { navigateBackToMain() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Back")
                }
            }
        }
    }

    @Composable
    private fun TeacherDashboard(assignments: SnapshotStateMap<String, MutableList<String>>) {
        var newAssignment by remember { mutableStateOf("") }
        var selectedStudent by remember { mutableStateOf("All Students") }
        val students = assignments.keys.toList()
        var isDropdownExpanded by remember { mutableStateOf(false) }

        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top
            ) {
                Text("Welcome, Teacher!", style = MaterialTheme.typography.headlineLarge)
                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = newAssignment,
                    onValueChange = { newAssignment = it },
                    label = { Text("Add a new assignment") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text("Select a student:")
                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = { isDropdownExpanded = !isDropdownExpanded },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(selectedStudent)
                }

                DropdownMenu(
                    expanded = isDropdownExpanded,
                    onDismissRequest = { isDropdownExpanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("All Students") },
                        onClick = {
                            selectedStudent = "All Students"
                            isDropdownExpanded = false
                        }
                    )

                    students.forEach { student ->
                        DropdownMenuItem(
                            text = { Text(student) },
                            onClick = {
                                selectedStudent = student
                                isDropdownExpanded = false
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        if (newAssignment.isNotEmpty()) {
                            if (selectedStudent == "All Students") {
                                students.forEach { assignments[it]?.add(newAssignment) }
                            } else {
                                assignments[selectedStudent]?.add(newAssignment)
                            }
                            newAssignment = ""
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Add Assignment")
                }

                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { navigateBackToMain() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Back")
                }
            }
        }
    }

    private fun navigateBackToMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }
}
