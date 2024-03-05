package com.example.myapplication

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                val loggedIn = remember { mutableStateOf(false) }

                if (loggedIn.value) {
                    // Показать окно с услугами
                    ServicesScreen()
                } else {
                    // Показать экран входа
                    LoginScreen(onLogin = { loggedIn.value = true })
                }
            }
        }
    }
}

data class User(val username: String, val password: String)

private val adminUser = User("admin", "admin")

@Composable
fun MyAppContent() {
    LoginScreen(onLogin = {})
}

@Composable
fun ServicesScreen() {
    val products = listOf(
        Product("АИ-98", "Высокооктановый бензин | 59 рублей/л"),
        Product("АИ-95", "Применяется в современных автомобилях | 48 рублей/л"),
        Product("АИ-93", "Обычно используется в спортивных автомобилях | 43 рублей/л"),
        Product("АИ-92", "Основной вид бензина для массового потребления | 40 рублей/л"),
        Product("ДТ", "Дизельное топливо | 58 рублей/л")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            items(products.size) { index ->
                ProductItem(products[index])
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { /* Handle basket button click */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(text = "Корзина")
        }
    }
}

@Composable
fun ProductItem(product: Product) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded }
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = product.name,
            )
            if (expanded) {
                Text(
                    text = product.description,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { /* Handle buy button click */ },
                    modifier = Modifier.align(Alignment.Start)
                ) {
                    Text(text = "Купить")
                }
            }
        }
    }
}




data class Product(val name: String, val description: String)

@Composable
fun LoginScreen(onLogin: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { attemptLogin(username, password, onLogin) }
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { attemptLogin(username, password, onLogin) },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Login")
        }
        errorMessage?.let { message ->
            Text(
                message ?: "",
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

private fun attemptLogin(username: String, password: String, onLogin: () -> Unit) {
    if (username == "admin" && password == "admin") {
        onLogin()
    } else {
        print("Error!")
    }
}


@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(onLogin = { })
}

@Preview(showBackground = true)
@Composable
fun MyAppPreview() {
    MyApplicationTheme {
        MyAppContent()
    }
}
