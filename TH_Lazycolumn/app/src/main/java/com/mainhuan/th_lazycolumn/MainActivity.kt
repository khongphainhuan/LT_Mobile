package com.mainhuan.th_lazycolumn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlin.random.Random

val XanhChu = Color(0xFF2196F3)
val NenThe = Color(0xFFE3F2FD)
val XanhLa = Color(0xFF4CAF50)
val Cam = Color(0xFFFF9800)

// Số lượng dòng trong danh sách
val SO_LUONG = 1_000_000

// Các câu nói để chọn ngẫu nhiên
val cacCauNoi = listOf(
    "The only way to do great work is to love what you do.",
    "Stay hungry, stay foolish.",
    "Simplicity is the ultimate sophistication.",
    "Innovation distinguishes between a leader and a follower.",
    "First, solve the problem. Then, write the code.",
    "Code is like humor. When you have to explain it, it's bad."
)

// Lấy ngẫu nhiên một câu cho dòng số "so".
// Dùng Random(so): cùng một số thì luôn ra cùng một câu, nên cuộn lên xuống không bị đổi chữ.
// Không tạo sẵn list 1 triệu câu, vì sẽ tốn rất nhiều bộ nhớ.
fun cauNgauNhien(so: Int): String {
    return cacCauNoi[Random(so).nextInt(cacCauNoi.size)]
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UngDung()
        }
    }
}

// ĐIỀU HƯỚNG: Chọn loại danh sách -> Column hoặc LazyColumn -> Chi tiết
@Composable
fun UngDung() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "choose") {
        composable("choose") {
            ManHinhChon(
                onColumn = { navController.navigate("column") },
                onLazy = { navController.navigate("lazy") }
            )
        }
        composable("column") {
            ManHinhColumn(
                onBack = { navController.popBackStack() },
                onChon = { so -> navController.navigate("detail/$so") }
            )
        }
        composable("lazy") {
            ManHinhLazyColumn(
                onBack = { navController.popBackStack() },
                onChon = { so -> navController.navigate("detail/$so") }
            )
        }
        composable("detail/{so}") { backStackEntry ->
            val so = backStackEntry.arguments?.getString("so")?.toInt() ?: 1
            ManHinhChiTiet(so = so, onBack = { navController.popBackStack() })
        }
    }
}

// Thanh tiêu đề màu xanh. onBack = null thì không hiện nút quay lại.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThanhTieuDe(tieuDe: String, onBack: (() -> Unit)? = null) {
    TopAppBar(
        title = { Text(tieuDe, color = Color.White, fontWeight = FontWeight.SemiBold) },
        navigationIcon = {
            if (onBack != null) {
                TextButton(onClick = onBack) {
                    Text("←", color = Color.White, fontSize = 22.sp)
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = XanhChu)
    )
}

// Một thẻ trong danh sách: số thứ tự, câu nói, mũi tên bên phải
@Composable
fun TheCauNoi(so: Int, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 5.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(NenThe)
            .clickable { onClick() }
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text("$so", fontWeight = FontWeight.Bold)
            Text(cauNgauNhien(so), fontSize = 13.sp, color = Color.Gray)
        }
        Text("→", fontSize = 20.sp)
    }
}

// ===================== MÀN HÌNH 1: CHỌN LOẠI DANH SÁCH =====================

@Composable
fun ManHinhChon(onColumn: () -> Unit, onLazy: () -> Unit) {
    Scaffold(topBar = { ThanhTieuDe("Choose List Type") }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = onColumn,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = XanhLa)
            ) {
                Text("Column (1,000,000 items)")
            }
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = onLazy,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Cam)
            ) {
                Text("LazyColumn (1,000,000 items)")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Lưu ý: Column sẽ làm app treo vì phải vẽ cả 1.000.000 dòng cùng lúc.",
                fontSize = 12.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

// ===================== MÀN HÌNH 2A: COLUMN (chậm) =====================

@Composable
fun ManHinhColumn(onBack: () -> Unit, onChon: (Int) -> Unit) {
    Scaffold(topBar = { ThanhTieuDe("Column", onBack) }) { padding ->
        // Column thường: tạo và vẽ TẤT CẢ các dòng ngay từ đầu
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            for (so in 1..SO_LUONG) {
                TheCauNoi(so, onClick = { onChon(so) })
            }
        }
    }
}

// ===================== MÀN HÌNH 2B: LAZYCOLUMN (nhanh) =====================

@Composable
fun ManHinhLazyColumn(onBack: () -> Unit, onChon: (Int) -> Unit) {
    Scaffold(topBar = { ThanhTieuDe("LazyColumn", onBack) }) { padding ->
        // LazyColumn: chỉ vẽ những dòng đang hiện trên màn hình
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(SO_LUONG) { i ->
                val so = i + 1
                TheCauNoi(so, onClick = { onChon(so) })
            }
        }
    }
}

// ===================== MÀN HÌNH 3: CHI TIẾT =====================

@Composable
fun ManHinhChiTiet(so: Int, onBack: () -> Unit) {
    Scaffold(topBar = { ThanhTieuDe("Item $so", onBack) }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Câu nói số $so", fontSize = 14.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "\"${cauNgauNhien(so)}\"",
                fontSize = 28.sp,
                lineHeight = 38.sp,
                fontFamily = FontFamily.Serif,
                textAlign = TextAlign.Center
            )
        }
    }
}