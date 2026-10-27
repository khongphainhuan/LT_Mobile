package com.mainhuan.uthtodo

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

// Biểu tượng app: ô bo góc, bên trong là 2 dòng "việc cần làm"
@Composable
fun BieuTuongApp(kichThuoc: Dp, nen: Color, mau: Color) {
    val d = kichThuoc / 6
    Box(
        modifier = Modifier
            .size(kichThuoc)
            .background(nen, RoundedCornerShape(kichThuoc / 4)),
        contentAlignment = Alignment.Center
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(d / 2)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(d).background(mau, CircleShape))
                Spacer(modifier = Modifier.width(d / 2))
                Box(modifier = Modifier.width(d * 2).height(d / 2).background(mau, RoundedCornerShape(50)))
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(d).border(d / 6, mau, CircleShape))
                Spacer(modifier = Modifier.width(d / 2))
                Box(
                    modifier = Modifier
                        .width(d * 1.5f)
                        .height(d / 2)
                        .background(mau.copy(alpha = 0.6f), RoundedCornerShape(50))
                )
            }
        }
    }
}

// Nhãn nhỏ có nền màu, ví dụ "Học tập", "Gấp"
@Composable
fun NhanMau(chu: String, nen: Color, mauChu: Color) {
    Text(
        chu,
        fontSize = 10.sp,
        fontWeight = FontWeight.SemiBold,
        color = mauChu,
        modifier = Modifier
            .background(nen, RoundedCornerShape(4.dp))
            .padding(horizontal = 6.dp, vertical = 2.dp)
    )
}

@Composable
fun NhanNhom(nhom: NhomViec) {
    NhanMau(nhom.ten, nhom.mauNen, nhom.mauChu)
}

@Composable
fun NhanGap() {
    NhanMau("Gấp", Color(0xFFFEE2E2), Color(0xFFDC2626))
}

// Ảnh đại diện tròn với chữ viết tắt, ví dụ "MA"
@Composable
fun AnhDaiDien(chu: String, kichThuoc: Dp, onClick: (() -> Unit)? = null) {
    val mau = MaterialTheme.colorScheme.primary
    Box(
        modifier = Modifier
            .size(kichThuoc)
            .clip(CircleShape)
            .background(mau.copy(alpha = 0.15f))
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier),
        contentAlignment = Alignment.Center
    ) {
        Text(chu, color = mau, fontWeight = FontWeight.Bold, fontSize = (kichThuoc.value * 0.36f).sp)
    }
}

// Khung thẻ nền trắng (tối: nền xám đậm), bo góc
@Composable
fun KhungThe(modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp), content = content)
    }
}

// Tiêu đề nhỏ của mỗi mục, ví dụ "CẦN LÀM", "GIAO DIỆN"
@Composable
fun TieuDeMuc(chu: String) {
    Text(
        chu,
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.padding(top = 20.dp, bottom = 8.dp)
    )
}

// Vòng tròn đánh dấu hoàn thành
@Composable
fun VongTronHoanThanh(daXong: Boolean, onClick: () -> Unit) {
    val mau = MaterialTheme.colorScheme.primary
    Box(
        modifier = Modifier
            .size(22.dp)
            .clip(CircleShape)
            .then(
                if (daXong) Modifier.background(mau)
                else Modifier.border(1.5.dp, MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f), CircleShape)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        if (daXong) {
            Icon(Icons.Filled.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
        }
    }
}

// Một dòng công việc trong danh sách
@Composable
fun TheViec(cv: CongViec, hienNgay: Boolean, onClick: () -> Unit) {
    val mauPhu = MaterialTheme.colorScheme.onSurfaceVariant
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
            VongTronHoanThanh(cv.daXong, onClick = { cv.doiTrangThai() })
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    cv.ten,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (cv.daXong) mauPhu else MaterialTheme.colorScheme.onSurface,
                    textDecoration = if (cv.daXong) TextDecoration.LineThrough else TextDecoration.None,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    NhanNhom(cv.nhom)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(if (hienNgay) cv.thoiGian() else cv.gio, fontSize = 12.sp, color = mauPhu)
                }
            }
            if (cv.laGap()) {
                NhanGap()
                Spacer(modifier = Modifier.width(6.dp))
            }
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = mauPhu)
        }
    }
}

// Một dòng cài đặt: tên bên trái, giá trị bên phải
@Composable
fun DongCaiDat(icon: ImageVector?, nhan: String, giaTri: String, onClick: (() -> Unit)? = null) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (icon != null) {
            Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(12.dp))
        }
        Text(nhan, fontSize = 15.sp, modifier = Modifier.weight(1f))
        Text(giaTri, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

// Một dòng có công tắc bật/tắt
@Composable
fun DongCongTac(nhan: String, moTa: String, dangBat: Boolean, onDoi: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(nhan, fontSize = 15.sp)
            Text(moTa, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Switch(checked = dangBat, onCheckedChange = onDoi)
    }
}

fun chuyenTab(nav: NavController, route: String) {
    nav.navigate(route) {
        popUpTo("home")
        launchSingleTop = true
    }
}

@Composable
fun RowScope.OTab(icon: ImageVector, nhan: String, dangChon: Boolean, onClick: () -> Unit) {
    val mau = if (dangChon) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
    Column(
        modifier = Modifier
            .weight(1f)
            .clickable { onClick() }
            .padding(vertical = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(icon, contentDescription = nhan, tint = mau, modifier = Modifier.size(22.dp))
        Text(nhan, fontSize = 10.sp, color = mau)
    }
}

@Composable
fun ThanhDieuHuong(tabHienTai: String, onChonTab: (String) -> Unit, onThem: () -> Unit) {
    Surface(color = MaterialTheme.colorScheme.surface, shadowElevation = 8.dp) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .height(64.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OTab(Icons.Filled.Home, "Trang chủ", tabHienTai == "home") { onChonTab("home") }
            OTab(Icons.AutoMirrored.Filled.List, "Danh sách", tabHienTai == "list") { onChonTab("list") }
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                        .clickable { onThem() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Thêm việc", tint = Color.White)
                }
            }
            OTab(Icons.Filled.Notifications, "Thông báo", tabHienTai == "notify") { onChonTab("notify") }
            OTab(Icons.Filled.Settings, "Cài đặt", tabHienTai == "settings") { onChonTab("settings") }
        }
    }
}

// Khung chung cho 4 màn chính: có thanh điều hướng dưới
@Composable
fun KhungManHinhChinh(nav: NavController, tab: String, content: @Composable (PaddingValues) -> Unit) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            ThanhDieuHuong(
                tabHienTai = tab,
                onChonTab = { route -> chuyenTab(nav, route) },
                onThem = { nav.navigate("add") }
            )
        }
    ) { padding ->
        content(padding)
    }
}
