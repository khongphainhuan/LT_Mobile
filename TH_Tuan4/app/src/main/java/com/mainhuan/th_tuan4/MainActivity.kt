package com.mainhuan.th_tuan4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// Màu dùng chung cho cả app
val XanhChu = Color(0xFF2196F3)
val NenThe = Color(0xFFBBDEFB)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
                // safeDrawingPadding: tránh bị thanh trạng thái và bàn phím che mất nội dung
                Box(modifier = Modifier.fillMaxSize().safeDrawingPadding()) {
                    UngDung()
                }
            }
        }
    }
}

// NAVIGATION: 3 màn hình Chào mừng -> Danh sách -> Chi tiết
@Composable
fun UngDung() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "welcome") {

        composable("welcome") {
            ManHinhChaoMung(
                onReady = {
                    navController.navigate("list") {
                        // Xóa màn hình chào mừng khỏi lịch sử, bấm Back không quay lại nữa
                        popUpTo("welcome") { inclusive = true }
                    }
                }
            )
        }

        composable("list") {
            ManHinhDanhSach(
                onChonThanhPhan = { ten -> navController.navigate("detail/$ten") }
            )
        }

        // {ten} là tham số: tên thành phần được bấm, ví dụ "detail/Text"
        composable("detail/{ten}") { backStackEntry ->
            val ten = backStackEntry.arguments?.getString("ten") ?: ""
            ManHinhChiTiet(
                ten = ten,
                onBack = { navController.popBackStack() }
            )
        }
    }
}

// Màn hình 1: Chào mừng
@Composable
fun ManHinhChaoMung(onReady: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Image(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = "Logo",
            modifier = Modifier.size(180.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text("Jetpack Compose", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "Jetpack Compose is a modern UI toolkit for building native Android " +
                    "applications using a declarative programming approach.",
            fontSize = 13.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onReady,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(containerColor = XanhChu)
        ) {
            Text("I'm ready", fontSize = 16.sp)
        }
    }
}