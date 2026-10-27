package com.mainhuan.uthtodo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlin.math.roundToInt

@Composable
fun ManHinhTrangChu(nav: NavController) {
    val nguoiDung = KhoDuLieu.nguoiDung
    val tong = KhoDuLieu.dsCongViec.size
    val xong = KhoDuLieu.soViecXong()
    val tiLe = if (tong == 0) 0f else xong.toFloat() / tong
    val viecHomNay = KhoDuLieu.dsCongViec.filter { it.ngay == "Hôm nay" }
    val mauChinh = MaterialTheme.colorScheme.primary

    KhungManHinhChinh(nav, "home") { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {
            // Lời chào
            Row(verticalAlignment = Alignment.CenterVertically) {
                AnhDaiDien(nguoiDung?.chuVietTat() ?: "UT", 40.dp, onClick = { nav.navigate("profile") })
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(homNayDayDu(), fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("Chào ${nguoiDung?.ten ?: "UTH"}!", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surface)
                        .clickable { chuyenTab(nav, "notify") },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Filled.Notifications, contentDescription = "Thông báo", modifier = Modifier.size(20.dp))
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(9.dp)
                            .size(7.dp)
                            .background(Color(0xFFDC2626), CircleShape)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Thẻ tiến độ
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(mauChinh, RoundedCornerShape(16.dp))
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Tiến độ hôm nay", color = Color.White.copy(alpha = 0.85f), fontSize = 13.sp)
                    Text("$xong/$tong việc", color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.Bold)
                    Text("Bắt đầu từ việc nhỏ nhất nhé.", color = Color.White.copy(alpha = 0.85f), fontSize = 12.sp)
                }
                Box(modifier = Modifier.size(64.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(
                        progress = { tiLe },
                        modifier = Modifier.fillMaxSize(),
                        color = Color.White,
                        trackColor = Color.White.copy(alpha = 0.25f),
                        strokeWidth = 6.dp
                    )
                    Text("${(tiLe * 100).roundToInt()}%", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Hai thẻ nhóm
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                for (nhom in NhomViec.entries) {
                    TheNhomTrangChu(
                        nhom = nhom,
                        soConLai = KhoDuLieu.soViecConLai(nhom),
                        modifier = Modifier.weight(1f),
                        onClick = { nav.navigate("list?nhom=${nhom.name}") }
                    )
                }
            }

            // Việc hôm nay
            Row(
                modifier = Modifier.padding(top = 24.dp, bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Việc hôm nay", fontSize = 17.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                Text(
                    "Xem tất cả",
                    color = mauChinh,
                    fontSize = 13.sp,
                    modifier = Modifier.clickable { chuyenTab(nav, "list") }
                )
            }
            if (viecHomNay.isEmpty()) {
                Text("Hôm nay chưa có việc nào.", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            for (cv in viecHomNay) {
                TheViec(cv, hienNgay = false, onClick = { nav.navigate("detail/${cv.id}") })
            }
        }
    }
}

@Composable
fun TheNhomTrangChu(nhom: NhomViec, soConLai: Int, modifier: Modifier, onClick: () -> Unit) {
    val icon = if (nhom == NhomViec.HOC_TAP) Icons.Filled.Edit else Icons.Filled.Home
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(nhom.mauNen, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = nhom.mauChu, modifier = Modifier.size(18.dp))
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(nhom.ten, fontWeight = FontWeight.SemiBold)
            Text("$soConLai việc còn lại", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
fun ManHinhDanhSach(nav: NavController, nhomBanDau: String) {
    var tuKhoa by remember { mutableStateOf("") }
    var boLoc by remember { mutableStateOf(nhomBanDau) }   // "TAT_CA", "HOC_TAP", "CUOC_SONG"

    val ketQua = KhoDuLieu.dsCongViec.filter { cv ->
        (boLoc == "TAT_CA" || cv.nhom.name == boLoc) &&
            cv.ten.contains(tuKhoa.trim(), ignoreCase = true)
    }
    val canLam = ketQua.filter { !it.daXong }
    val daXong = ketQua.filter { it.daXong }
    val soChuaXong = KhoDuLieu.dsCongViec.count { !it.daXong }

    KhungManHinhChinh(nav, "list") { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(20.dp)
        ) {
            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Danh sách", fontSize = 26.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                    Text("$soChuaXong việc chưa xong", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = tuKhoa,
                    onValueChange = { tuKhoa = it },
                    placeholder = { Text("Tìm công việc") },
                    leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    ChipLoc("Tất cả", boLoc == "TAT_CA") { boLoc = "TAT_CA" }
                    for (nhom in NhomViec.entries) {
                        ChipLoc(nhom.ten, boLoc == nhom.name) { boLoc = nhom.name }
                    }
                }
                TieuDeMuc("CẦN LÀM")
            }

            if (canLam.isEmpty()) {
                item { Text("Không có việc nào cần làm.", color = MaterialTheme.colorScheme.onSurfaceVariant) }
            }
            items(canLam, key = { it.id }) { cv ->
                TheViec(cv, hienNgay = true, onClick = { nav.navigate("detail/${cv.id}") })
            }

            if (daXong.isNotEmpty()) {
                item { TieuDeMuc("ĐÃ XONG") }
                items(daXong, key = { it.id }) { cv ->
                    TheViec(cv, hienNgay = true, onClick = { nav.navigate("detail/${cv.id}") })
                }
            }
        }
    }
}

// Nút lọc dạng viên thuốc: đang chọn thì nền đậm
@Composable
fun ChipLoc(chu: String, dangChon: Boolean, onClick: () -> Unit) {
    val nen = if (dangChon) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.surface
    val mauChu = if (dangChon) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.onSurface
    Text(
        chu,
        fontSize = 13.sp,
        fontWeight = FontWeight.Medium,
        color = mauChu,
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(nen)
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp)
    )
}

@Composable
fun ManHinhThongBao(nav: NavController) {
    val dsNhac = KhoDuLieu.dsCongViec.filter { it.nhacNho && !it.daXong }

    KhungManHinhChinh(nav, "notify") { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(20.dp)
        ) {
            item {
                Text("Thông báo", fontSize = 26.sp, fontWeight = FontWeight.Bold)
                Text(
                    if (KhoDuLieu.caiDat.nhacViec) "Các việc sẽ được nhắc trước hạn chót 15 phút"
                    else "Bạn đang tắt nhắc việc trong Cài đặt",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
            if (dsNhac.isEmpty()) {
                item { Text("Không có nhắc nhở nào.", color = MaterialTheme.colorScheme.onSurfaceVariant) }
            }
            items(dsNhac, key = { it.id }) { cv ->
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { nav.navigate("detail/${cv.id}") },
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.surface
                ) {
                    Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.Notifications, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(cv.ten, fontWeight = FontWeight.Medium)
                            Text(
                                "Hạn chót ${cv.thoiGian()} · nhắc trước 15 phút",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}
