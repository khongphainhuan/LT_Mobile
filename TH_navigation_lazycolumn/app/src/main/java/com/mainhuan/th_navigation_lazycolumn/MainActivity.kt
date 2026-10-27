package com.mainhuan.th_navigation_lazycolumn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import java.util.Locale

val XanhChu = Color(0xFF2196F3)
val NenThe = Color(0xFFBBDEFB)
val CAU_NOI = "The only way to do great work is to love what you do."

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
                Box(modifier = Modifier.fillMaxSize().safeDrawingPadding()) {
                    UngDung()
                }
            }
        }
    }
}

// ĐIỀU HƯỚNG: Root -> List -> Detail
@Composable
fun UngDung() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "root") {

        // Màn hình 1: Root
        composable("root") {
            ManHinhRoot(
                onPush = { navController.navigate("list") }          // Root -> List
            )
        }

        // Màn hình 2: List (LazyColumn)
        composable("list") {
            ManHinhDanhSach(
                onBack = { navController.popBackStack() },             // List -> Root
                onChon = { so -> navController.navigate("detail/$so") } // List -> Detail
            )
        }

        // Màn hình 3: Detail, nhận tham số "so" là số thứ tự câu nói
        composable("detail/{so}") { backStackEntry ->
            val so = backStackEntry.arguments?.getString("so")?.toInt() ?: 1
            ManHinhChiTiet(
                so = so,
                onBack = { navController.popBackStack() },             // Detail -> List
                onVeRoot = {
                    // Detail -> Root: bỏ hết các màn hình phía trên "root"
                    navController.popBackStack("root", inclusive = false)
                }
            )
        }
    }
}

// Thanh tiêu đề dùng chung: nút "<" màu xanh bên trái, tiêu đề ở giữa
@Composable
fun ThanhTieuDe(tieuDe: String, onBack: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(36.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(XanhChu)
                .clickable { onBack() },
            contentAlignment = Alignment.Center
        ) {
            Text("<", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
        Text(
            tieuDe,
            color = XanhChu,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

// Đổi số thành chữ: 1 -> "01", 1000000 -> "1.000.000"
fun dinhDangSo(n: Int): String {
    if (n < 10) return "0$n"
    return String.format(Locale.US, "%,d", n).replace(',', '.')
}

// ===================== MÀN HÌNH 1: ROOT =====================

@Composable
fun ManHinhRoot(onPush: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))

        // Muốn dùng logo Jetpack Compose: thêm ảnh vào res/drawable rồi đổi tên ở đây
        Image(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = "Logo",
            modifier = Modifier.size(180.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text("Navigation", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "is a framework that simplifies the implementation of navigation between " +
                    "different UI components (activities, fragments, or composables) in an app",
            fontSize = 13.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onPush,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(containerColor = XanhChu)
        ) {
            Text("PUSH", fontSize = 16.sp)
        }
    }
}

// ===================== MÀN HÌNH 2: LIST =====================

@Composable
fun ManHinhDanhSach(onBack: () -> Unit, onChon: (Int) -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        ThanhTieuDe("LazyColumn", onBack)

        // 1 triệu dòng nhưng không lag: LazyColumn chỉ vẽ những dòng đang hiện trên màn hình
        LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
            items(1_000_000) { i ->
                val so = i + 1
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(NenThe)
                        .clickable { onChon(so) }
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "${dinhDangSo(so)} | $CAU_NOI",
                        fontSize = 14.sp,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(Color.Black, RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(">", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

// ===================== MÀN HÌNH 3: DETAIL =====================

@Composable
fun ManHinhChiTiet(so: Int, onBack: () -> Unit, onVeRoot: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        ThanhTieuDe("Detail", onBack)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Câu nói số ${dinhDangSo(so)}", fontSize = 13.sp, color = Color.Gray)
            Text(
                "\"$CAU_NOI\"",
                fontSize = 13.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Khung trích dẫn nền chuyển màu xanh
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(listOf(NenThe, XanhChu)),
                        RoundedCornerShape(12.dp)
                    )
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "\"$CAU_NOI\"",
                    fontSize = 30.sp,
                    lineHeight = 40.sp,
                    fontFamily = FontFamily.Serif,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text("Steve Jobs", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    "http://quotes.thisgrandpablogs.com/",
                    fontSize = 11.sp,
                    color = Color.White,
                    textDecoration = TextDecoration.Underline
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = onVeRoot,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .height(50.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(containerColor = XanhChu)
            ) {
                Text("BACK TO ROOT", fontSize = 16.sp)
            }
        }
    }
}