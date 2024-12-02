package upt.ac.lab2

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { homeScreen() } // Apelul funcției @Composable
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
