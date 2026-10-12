package com.mainhuan.th3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ManHinhTinhToan()
        }
    }
}

@Composable
fun ManHinhTinhToan() {
    // State: những giá trị màn hình cần nhớ
    var so1 by remember { mutableStateOf("") }
    var so2 by remember { mutableStateOf("") }
    var phepToan by remember { mutableStateOf("") }

    // Tính kết quả
    val a = so1.toDoubleOrNull()
    val b = so2.toDoubleOrNull()
    var ketQua = ""

    if (a != null && b != null) {
        ketQua = when (phepToan) {
            "+" -> (a + b).toString()
            "-" -> (a - b).toString()
            "*" -> (a * b).toString()
            "/" -> if (b == 0.0) "Không chia được cho 0" else (a / b).toString()
            else -> ""
        }
        ketQua = ketQua.removeSuffix(".0")  // 8.0 -> 8
    }

    // Giao diện
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(120.dp))
        Text(text = "Thực hành 03", fontSize = 18.sp)
        Spacer(modifier = Modifier.height(60.dp))

        OutlinedTextField(
            value = so1,
            onValueChange = { so1 = it },
            label = { Text("Nhập số thứ nhất") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            NutPhepToan("+", Color(0xFFE5483F), phepToan == "+") { phepToan = "+" }
            NutPhepToan("-", Color(0xFFE2A94B), phepToan == "-") { phepToan = "-" }
            NutPhepToan("*", Color(0xFF5B3FE0), phepToan == "*") { phepToan = "*" }
            NutPhepToan("/", Color(0xFF1C1C1E), phepToan == "/") { phepToan = "/" }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = so2,
            onValueChange = { so2 = it },
            label = { Text("Nhập số thứ hai") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Kết quả: $ketQua",
            fontSize = 16.sp,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

// Nút phép toán: nút đang được chọn có viền đen
@Composable
fun NutPhepToan(kyHieu: String, mau: Color, dangChon: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = mau),
        shape = RoundedCornerShape(8.dp),
        border = if (dangChon) BorderStroke(3.dp, Color.Black) else null,
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier.size(60.dp)
    ) {
        Text(text = kyHieu, fontSize = 20.sp)
    }
}