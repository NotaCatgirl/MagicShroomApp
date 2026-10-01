package com.example.magicshroomapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.magicshroomapp.network.UserRow
import com.example.magicshroomapp.network.supabase
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import io.github.jan.supabase.postgrest.query.Columns
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var users by remember {
                mutableStateOf<List<UserRow>>(emptyList())
            }

            var status by remember {
                mutableStateOf("Loading users...")
            }

            LaunchedEffect(Unit) {
                try {
                    val loadedUsers = withContext(Dispatchers.IO) {
                        supabase
                            .from("Users")
                            .select(
                                columns = Columns.list(
                                    "user_id",
                                    "username",
                                    "created_at"
                                )
                            )
                            .decodeList<UserRow>()
                    }
                    users = loadedUsers
                    status = if (loadedUsers.isEmpty()) {
                        "No users found."
                    } else {
                        "Loaded ${loadedUsers.size} users"
                    }

                    loadedUsers.forEach { user ->
                        Log.d(
                            "SUPABASE_TEST",
                            "User: ${user.userId}, ${user.username}"
                        )
                    }
                } catch (e: Exception) {
                    status = "Supabase error: ${e.message}"
                    Log.e("SUPABASE_TEST", "Supabase failed", e)
                }
            }

            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                Text(text = status)

                users.forEach { user ->
                    Text(
                        text = "${user.userId}: ${user.username}",
                        modifier = Modifier.padding(top = 12.dp)
                    )
                }
            }
        }
    }
}
/** Original home logic
 *
 *    override fun onCreate(savedInstanceState: Bundle?) {
 *         super.onCreate(savedInstanceState)
 *         enableEdgeToEdge()
 *         setContent {
 *             MagicShroomAppTheme {
 *                 Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
 *                     Greeting(
 *                         name = "Android",
 *                         modifier = Modifier.padding(innerPadding)
 *                     )
 *                 }
 *             }
 *         }
 *     }
 * }
 *
 * @Composable
 * fun Greeting(name: String, modifier: Modifier = Modifier) {
 *     Text(
 *         text = "Hello $name!",
 *         modifier = modifier
 *     )
 * }
 *
 * @Preview(showBackground = true)
 * @Composable
 * fun GreetingPreview() {
 *     MagicShroomAppTheme {
 *         Greeting("Android")
 *     }
 *
 */