package com.example.tp4

import android.os.Bundle
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import android.content.ContentValues
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.tp4.data.Product
import com.example.tp4.ui.theme.Tp4Theme

class Homepage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Tp4Theme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "product_list") {
                    composable("product_list") { ListComposable(navController) }
                    composable("product_detail/{name}/{price}/{description}") { backStackEntry ->
                        val name = backStackEntry.arguments?.getString("name")
                        val price = backStackEntry.arguments?.getString("price")
                        val description = backStackEntry.arguments?.getString("description")
                        ProductDetailScreen(name = name, price = price, description = description, navController = navController)
                    }
                }
            }
        }
    }
}

@Composable
fun ListComposable(navController: NavHostController) {
    val list = remember {
        mutableStateListOf(
            Product("Product 1", 10.8, "Description 1", R.drawable.product),
            Product("Product 2", 20.0, "Description 2", R.drawable.produit),
            Product("Product 3", 15.0, "Description 3", R.drawable.prod),
            Product("Product 4", 12.5, "Description 4", R.drawable.image),
            Product("Product 5", 18.3, "Description 5", R.drawable.img)
        )
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(list) { product ->
            ProductCard(navController = navController, product = product)
        }
    }
}

@Composable
fun ProductCard(navController: NavHostController, product: Product, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp)
            .clickable {
                navController.navigate("product_detail/${product.name}/${product.price}/${product.description}")
            },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = product.imageUrl),
                contentDescription = product.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .padding(8.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(8.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = product.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Price: ${product.price} €",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

@Composable
fun ProductDetailScreen(name: String?, price: String?, description: String?, navController: NavHostController) {
    var userName by remember { mutableStateOf("") }
    var userList by remember { mutableStateOf(emptyList<String>()) }
    var selectedUser by remember { mutableStateOf<String?>(null) }
    val dbHelper = DatabaseHelper(navController.context)

    // Fetch the list of users from the database
    LaunchedEffect(Unit) {
        userList = dbHelper.getAllUsers()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Display product details
        Text(
            text = "Product Name: $name",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Price: $price €",
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = description ?: "No description available",
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        // User Management Section
        OutlinedTextField(
            value = userName,
            onValueChange = { userName = it },
            label = { Text("User Name") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Button to Register or Update User
        Button(onClick = {
            if (selectedUser != null) {
                dbHelper.updateUser(selectedUser!!, userName) // Update user
                selectedUser = null // Reset selected user
            } else {
                dbHelper.insertUser(userName) // Register new user
            }
            userName = "" // Clear the input field
            userList = dbHelper.getAllUsers() // Refresh the user list
        }) {
            Text(if (selectedUser == null) "Register User" else "Update User")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Display the list of users with Edit and Delete buttons
        LazyColumn {
            items(userList) { user ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(user, modifier = Modifier.align(Alignment.CenterVertically))

                    // Edit button
                    IconButton(onClick = {
                        selectedUser = user
                        userName = user // Pre-fill the name field for editing
                    }) {
                        Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
                    }

                    // Delete button
                    IconButton(onClick = {
                        dbHelper.deleteUser(user)
                        userList = dbHelper.getAllUsers() // Refresh the list after deletion
                    }) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
                    }
                }
            }
        }
    }
}

// Database Helper Class for managing users
class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "userDB", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)"
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS users")
        onCreate(db)
    }

    // Insert user
    fun insertUser(userName: String) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("name", userName)
        }
        db.insert("users", null, values)
        db.close()
    }

    // Get all users
    fun getAllUsers(): List<String> {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT name FROM users", null)
        val users = mutableListOf<String>()
        while (cursor.moveToNext()) {
            users.add(cursor.getString(cursor.getColumnIndex("name")))
        }
        cursor.close()
        db.close()
        return users
    }

    // Update user
    fun updateUser(oldName: String, newName: String) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("name", newName)
        }
        db.update("users", values, "name = ?", arrayOf(oldName))
        db.close()
    }

    // Delete user
    fun deleteUser(userName: String) {
        val db = this.writableDatabase
        db.delete("users", "name = ?", arrayOf(userName))
        db.close()
    }
}
