package com.mainhuan.btvn2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            XepLoaiHocLuc()
        }
    }
}

// Hàm nhận vào điểm, trả về xếp loại
fun xepLoai(diem: Double): String {
    if (diem < 0 || diem > 10) {
        return "Không hợp lệ"
    } else if (diem >= 8.0) {
        return "Giỏi"
    } else if (diem >= 6.5) {
        return "Khá"
    } else if (diem >= 5.0) {
        return "Trung bình"
    } else {
        return "Yếu"
    }
}

// Chọn màu chữ theo xếp loại, cũng dùng if
fun mauXepLoai(loai: String): Color {
    if (loai == "Giỏi") {
        return Color(0xFF2E7D32)
    } else if (loai == "Khá") {
        return Color(0xFF1565C0)
    } else if (loai == "Trung bình") {
        return Color(0xFFE08A00)
    } else if (loai == "Yếu") {
        return Color(0xFFC62828)
    } else {
        return Color.Gray
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun XepLoaiHocLuc() {
    val diem = 8.5
    val loai = xepLoai(diem)
    val dsDiem = listOf(9.0, 7.0, 5.5, 3.0, 11.0)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Xếp loại học lực", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF2E4A7D))
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {

            // Thẻ điểm chính
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFEEEEEE))
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Điểm trung bình", color = Color.Gray)
                Text("$diem", fontSize = 48.sp, fontWeight = FontWeight.Bold)
                Text(loai, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = mauXepLoai(loai))
            }

            Spacer(modifier = Modifier.height(20.dp))
            Text("KIỂM TRA THÊM", fontSize = 13.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(8.dp))

            // Gọi hàm với các điểm 9, 7, 5.5, 3, 11
            for (d in dsDiem) {
                val l = xepLoai(d)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Điểm " + d.toString().removeSuffix(".0"))
                    Text(l, fontWeight = FontWeight.Bold, color = mauXepLoai(l))
                }
            }
        }
    }
}