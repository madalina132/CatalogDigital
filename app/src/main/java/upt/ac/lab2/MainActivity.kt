package upt.ac.lab2

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : AppCompatActivity() {

    companion object {
        val studentAssignments: SnapshotStateMap<String, MutableList<String>> =
            mutableStateMapOf(
                "Student1" to mutableListOf(),
                "Student2" to mutableListOf(),
                "Student3" to mutableListOf()
            )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { homeScreen() }
    }

    @Composable
    private fun homeScreen() {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    "Welcome to Digital Catalog",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(bottom = 32.dp)
                )

                Button(
                    onClick = { openComposeActivity("student") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    Text("Login as Student")
                }

                Button(
                    onClick = { openComposeActivity("parent") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    Text("Login as Parent")
                }

                Button(
                    onClick = { openComposeActivity("teacher") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Login as Teacher")
                }
            }
        }
    }

    private fun openComposeActivity(role: String) {
        val intent = Intent(this, ComposeActivity::class.java)
        intent.putExtra("ROLE", role)
        startActivity(intent)
    }
}
