package com.mainhuan.btvn1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

class Student(val name: String, val email: String?, val phone: String?)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DanhSachSinhVien()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DanhSachSinhVien() {
    val ds = listOf(
        Student("Nguyễn Văn An", "an@sv.edu.vn", "0901 234 567"),
        Student("Trần Thị Bình", null, "0912 888 999"),
        Student("Lê Minh Chi", "chi@sv.edu.vn", null),
        Student("Phạm Quốc Dũng", null, null),
        Student("Võ Thanh Em", "em@sv.edu.vn", "0933 111 222")
    )

    // Đếm sinh viên thiếu email
    var dem = 0
    for (sv in ds) {
        if (sv.email == null) {
            dem++
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Danh sách sinh viên", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF2E4A7D))
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {

            // Tạo thẻ cho từng sinh viên
            for (sv in ds) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFEEEEEE))
                        .padding(12.dp)
                ) {
                    Text(sv.name, fontWeight = FontWeight.Bold)
                    Text("Email: " + (sv.email ?: "Chưa cập nhật"))
                    Text("SĐT: " + (sv.phone ?: "Chưa cập nhật"))
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            Text("Tổng: ${ds.size} sinh viên - Thiếu email: $dem")
        }
    }
}