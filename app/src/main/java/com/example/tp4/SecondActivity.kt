package com.example.tp4

import android.content.Intent
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tp4.ui.theme.Tp4Theme

class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Tp4Theme {
                FormScreen()
            }
        }
    }
}

// Formulaire simple sur la page SecondActivity
@Composable
fun FormScreen(modifier: Modifier = Modifier) {
    var field1 by remember { mutableStateOf("") }
    var field2 by remember { mutableStateOf("") }
    var submittedText by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Simple Form", fontSize = 32.sp)

        Spacer(modifier = Modifier.height(16.dp))

        // Champ 1
        TextField(
            value = field1,
            onValueChange = { field1 = it },
            label = { Text("Field 1") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Champ 2
        TextField(
            value = field2,
            onValueChange = { field2 = it },
            label = { Text("Field 2") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Bouton pour soumettre
        Button(onClick = {
            submittedText = "Submitted: $field1 and $field2"
        }) {
            Text("Submit")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Affichage des informations soumises
        if (submittedText.isNotEmpty()) {
            Text(text = submittedText, fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Bouton pour aller à la NewFormActivity
        Button(onClick = {
            val intent = Intent(context, NewFormActivity::class.java)
            context.startActivity(intent)
        }) {
            Text("Go to New Form")
        }
    }
}

// Prévisualisation du formulaire
@Composable
@Preview(showBackground = true)
fun FormPreview() {
    Tp4Theme {
        FormScreen()
    }
}
