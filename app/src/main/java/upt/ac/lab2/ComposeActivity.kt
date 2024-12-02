package upt.ac.lab2

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class ComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val role = intent.getStringExtra("ROLE") ?: "unknown"
        setContent { LoginScreen(role) }
    }

    @Composable
    private fun LoginScreen(role: String) {
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
                }
            }
        } else {
            when (role) {
                "student" -> StudentDashboard(onLogout = { navigateBackToMain() })
                "parent" -> ParentDashboard(onLogout = { navigateBackToMain() })
                "teacher" -> TeacherDashboard(onLogout = { navigateBackToMain() })
                else -> Text("Unknown role")
            }
        }
    }

    private fun authenticate(username: String, password: String, role: String): Boolean {
        return username.isNotEmpty() && password == "password123"
    }

    @Composable
    private fun StudentDashboard(onLogout: () -> Unit) {
        val assignments = remember { mutableStateListOf("Math Homework", "Science Project") }
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top
            ) {
                Text("Welcome, Student!", style = MaterialTheme.typography.headlineLarge)
                Spacer(modifier = Modifier.height(16.dp))

                Text("Here are your grades and assignments:")
                Spacer(modifier = Modifier.height(8.dp))

                LazyColumn {
                    items(assignments.size) { index ->
                        Text("- ${assignments[index]}")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = onLogout, modifier = Modifier.fillMaxWidth()) {
                    Text("Log out")
                }
            }
        }
    }

    @Composable
    private fun ParentDashboard(onLogout: () -> Unit) {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top
            ) {
                Text("Welcome, Parent!", style = MaterialTheme.typography.headlineLarge)
                Spacer(modifier = Modifier.height(16.dp))
                Text("Here is the progress of your child.")

                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = onLogout, modifier = Modifier.fillMaxWidth()) {
                    Text("Log out")
                }
            }
        }
    }

    @Composable
    private fun TeacherDashboard(onLogout: () -> Unit) {
        val assignments = remember { mutableStateListOf<String>() }
        var newAssignment by remember { mutableStateOf("") }

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

                Button(
                    onClick = {
                        if (newAssignment.isNotEmpty()) {
                            assignments.add(newAssignment)
                            newAssignment = ""
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Add Assignment")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text("Current Assignments:")
                Spacer(modifier = Modifier.height(8.dp))

                LazyColumn {
                    items(assignments.size) { index ->
                        Text("- ${assignments[index]}")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = onLogout, modifier = Modifier.fillMaxWidth()) {
                    Text("Log out")
                }
            }
        }
    }

    private fun navigateBackToMain() {
        // Navighează înapoi la MainActivity
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }
}
