package com.example.retrofit_get_request_example

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.example.retrofit_get_request_example.ui.theme.RetrofitgetrequestexampleTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RetrofitgetrequestexampleTheme {
                IpAddressApp()
            }
        }
    }
}

@Composable
private fun IpAddressApp() {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            color = Color(0xFFF5F5DC)
        ) {
            IpAddressScreen()
        }
    }
}

@Composable
fun IpAddressScreen() {
    var ipAddress by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current

    fun loadIpAddress() {
        scope.launch {
            isLoading = true
            ipAddress = try {
                NetworkService.api.getIp().myip
            } catch (e: Exception) {
                "Ошибка получения IP"
            } finally {
                isLoading = false
            }
        }
    }

    LaunchedEffect(Unit) { loadIpAddress() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (!isLoading) {
            Text(
                text = "Ваш IP адрес: ",
                style = MaterialTheme.typography.headlineLarge,
            )

            SelectionContainer {
                Text(
                    text = ipAddress,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .clickable {
                            clipboardManager.setText(AnnotatedString(ipAddress))
                            Toast.makeText(
                                context,
                                "Скопировано!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                )
            }
        } else {
            CircularProgressIndicator()
        }

        Button(onClick = { loadIpAddress() }) {
            Text("Обновить")
        }
    }
}