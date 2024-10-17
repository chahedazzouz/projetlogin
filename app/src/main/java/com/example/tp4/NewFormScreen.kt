package com.example.tp4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tp4.ui.theme.Tp4Theme

class NewFormActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Tp4Theme {
                NewFormScreen()
            }
        }
    }
}

// Écran du nouveau formulaire
@Composable
fun NewFormScreen(modifier: Modifier = Modifier) {
    var additionalInfo by remember { mutableStateOf("") }
    var submittedText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "New Form", fontSize = 32.sp)

        Spacer(modifier = Modifier.height(16.dp))

        // Champ pour des informations supplémentaires
        TextField(
            value = additionalInfo,
            onValueChange = { additionalInfo = it },
            label = { Text("Additional Info") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Bouton pour soumettre
        Button(onClick = {
            submittedText = "Submitted: $additionalInfo"
        }) {
            Text("Submit")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Affichage des informations soumises
        if (submittedText.isNotEmpty()) {
            Text(text = submittedText, fontSize = 18.sp)
        }
    }
}

// Prévisualisation de la nouvelle activité de formulaire
@Composable
@Preview(showBackground = true)
fun NewFormPreview() {
    Tp4Theme {
        NewFormScreen()
    }
}
