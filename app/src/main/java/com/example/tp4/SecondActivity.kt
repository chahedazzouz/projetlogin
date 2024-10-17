package com.example.tp4

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmepassword by remember { mutableStateOf("") }
    var submittedText by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Sign Up", fontSize = 32.sp)
        Image(painter = painterResource(R.drawable.singup), contentDescription = null,  modifier = Modifier.fillMaxWidth(),contentScale = ContentScale.FillWidth )
        Spacer(modifier = Modifier.height(16.dp))

        // Champ 1
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Champ 2
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = confirmepassword,
            onValueChange = { confirmepassword = it },
            label = { Text("Confirm password") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Bouton pour soumettre
        Button(onClick = {
            submittedText = "Submitted: $name and $email"
        }) {
            Text("Sign Up")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Affichage des informations soumises
        if (submittedText.isNotEmpty()) {
            Text(text = submittedText, fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Bouton pour aller à la NewFormActivity
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
