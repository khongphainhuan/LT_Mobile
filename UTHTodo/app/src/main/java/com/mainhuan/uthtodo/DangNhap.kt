package com.mainhuan.uthtodo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Đăng nhập Google thật cần Firebase (bài sau), ở đây chỉ giả lập bằng người dùng mẫu

@Composable
fun ManHinhDangNhap(onDangNhap: (NguoiDung) -> Unit) {
    val mauChinh = MaterialTheme.colorScheme.primary
    val mauPhu = MaterialTheme.colorScheme.onSurfaceVariant

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .safeDrawingPadding()
            .padding(24.dp)
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        BieuTuongApp(kichThuoc = 64.dp, nen = mauChinh, mau = Color.White)
        Spacer(modifier = Modifier.height(24.dp))
        Text("Chào mừng đến với\nUTH Todo", fontSize = 28.sp, fontWeight = FontWeight.Bold, lineHeight = 34.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "Đăng nhập để lưu công việc và đồng bộ trên mọi thiết bị của bạn.",
            fontSize = 14.sp,
            color = mauPhu
        )
        Spacer(modifier = Modifier.height(20.dp))
        DongLoiIch(Icons.Filled.Refresh, "Tự động sao lưu, không lo mất dữ liệu")
        DongLoiIch(Icons.Filled.Done, "Dùng trên iPhone, iPad và Mac")

        Spacer(modifier = Modifier.weight(1f))

        OutlinedButton(
            onClick = {
                onDangNhap(NguoiDung(ten = "Minh Anh", email = "minhanh@gmail.com", lienKetGoogle = true))
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(22.dp)
                    .background(Color.White, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text("G", color = Color(0xFF4285F4), fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text("Tiếp tục với Google", fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface)
        }

        Text(
            "hoặc",
            fontSize = 12.sp,
            color = mauPhu,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        TextButton(
            onClick = { onDangNhap(NguoiDung(ten = "UTH", email = "", lienKetGoogle = false)) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Dùng thử không cần tài khoản", color = mauChinh, fontWeight = FontWeight.SemiBold)
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "Khi tiếp tục, bạn đồng ý với Điều khoản sử dụng và Chính sách quyền riêng tư của UTH Todo.",
            fontSize = 11.sp,
            color = mauPhu,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun DongLoiIch(icon: ImageVector, chu: String) {
    Row(
        modifier = Modifier.padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(chu, fontSize = 13.sp)
    }
}
