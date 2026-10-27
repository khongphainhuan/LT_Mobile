package com.mainhuan.uthtodo

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.util.Calendar

@Composable
fun ManHinhCaiDat(nav: NavController) {
    val caiDat = KhoDuLieu.caiDat
    val nguoiDung = KhoDuLieu.nguoiDung

    KhungManHinhChinh(nav, "settings") { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Cài đặt", fontSize = 26.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                AnhDaiDien(nguoiDung?.chuVietTat() ?: "UT", 40.dp, onClick = { nav.navigate("profile") })
            }

            TieuDeMuc("GIAO DIỆN")
            KhungThe(modifier = Modifier.fillMaxWidth()) {
                // 3 ô xem trước: Sáng / Tối / Tự động
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    for (gd in GiaoDien.entries) {
                        OChonGiaoDien(
                            giaoDien = gd,
                            dangChon = caiDat.giaoDien == gd,
                            modifier = Modifier.weight(1f),
                            onClick = { caiDat.giaoDien = gd }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Màu chủ đạo", fontSize = 15.sp, modifier = Modifier.weight(1f))
                    for (mau in KhoDuLieu.dsMauChuDao) {
                        Box(
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .size(26.dp)
                                .clip(CircleShape)
                                .background(mau)
                                .border(
                                    if (caiDat.mauChuDao == mau) 3.dp else 0.dp,
                                    if (caiDat.mauChuDao == mau) MaterialTheme.colorScheme.onSurface else Color.Transparent,
                                    CircleShape
                                )
                                .clickable { caiDat.mauChuDao = mau }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Cỡ chữ", fontSize = 15.sp)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("A", fontSize = 12.sp)
                    Slider(
                        value = caiDat.coChu,
                        onValueChange = { caiDat.coChu = it },
                        valueRange = 0.85f..1.3f,
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp)
                    )
                    Text("A", fontSize = 20.sp)
                }
            }

            TieuDeMuc("THÔNG BÁO")
            KhungThe(modifier = Modifier.fillMaxWidth()) {
                DongCongTac("Nhắc việc", "Báo trước hạn chót 15 phút", caiDat.nhacViec) { caiDat.nhacViec = it }
                DongCongTac("Âm thanh", "Phát âm khi có thông báo", caiDat.amThanh) { caiDat.amThanh = it }
                DongCongTac("Tổng kết cuối ngày", "Gửi lúc 21:00 mỗi tối", caiDat.tongKetCuoiNgay) { caiDat.tongKetCuoiNgay = it }
            }

            TieuDeMuc("CHUNG")
            KhungThe(modifier = Modifier.fillMaxWidth()) {
                DongCaiDat(null, "Ngôn ngữ", "Tiếng Việt")
                HorizontalDivider(color = MaterialTheme.colorScheme.outline)
                DongCaiDat(Icons.Filled.Info, "Giới thiệu UTH Todo", "Phiên bản 1.0")
            }
        }
    }
}

// Ô xem trước giao diện
@Composable
fun OChonGiaoDien(giaoDien: GiaoDien, dangChon: Boolean, modifier: Modifier, onClick: () -> Unit) {
    val mauVien = if (dangChon) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
    val mauSang = Color.White
    val mauToi = Color(0xFF1C1C1E)

    Column(
        modifier = modifier.clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .clip(RoundedCornerShape(8.dp))
                .border(if (dangChon) 2.dp else 1.dp, mauVien, RoundedCornerShape(8.dp))
        ) {
            when (giaoDien) {
                GiaoDien.SANG -> MauXemTruoc(mauSang, Color(0xFFE2E6EF), Modifier.weight(1f))
                GiaoDien.TOI -> MauXemTruoc(mauToi, Color(0xFF3A3A40), Modifier.weight(1f))
                GiaoDien.TU_DONG -> {
                    MauXemTruoc(mauSang, Color(0xFFE2E6EF), Modifier.weight(1f))
                    MauXemTruoc(mauToi, Color(0xFF3A3A40), Modifier.weight(1f))
                }
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            giaoDien.ten,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            color = if (dangChon) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun MauXemTruoc(nen: Color, thanh: Color, modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .background(nen)
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth().height(6.dp).background(thanh, RoundedCornerShape(50)))
        Box(modifier = Modifier.fillMaxWidth(0.7f).height(6.dp).background(thanh, RoundedCornerShape(50)))
        Box(modifier = Modifier.fillMaxWidth(0.5f).height(6.dp).background(thanh, RoundedCornerShape(50)))
    }
}

@Composable
fun ManHinhHoSo(nav: NavController) {
    val nd = KhoDuLieu.nguoiDung ?: NguoiDung(ten = "UTH", email = "", lienKetGoogle = false)
    val mauChinh = MaterialTheme.colorScheme.primary
    val mauPhu = MaterialTheme.colorScheme.onSurfaceVariant
    val mauDo = Color(0xFFDC2626)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .safeDrawingPadding()
    ) {
        // Thanh trên
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp)
        ) {
            Row(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .clickable { nav.popBackStack() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = null, tint = mauChinh)
                Text("Quay lại", color = mauChinh)
            }
            Text("Hồ sơ", fontSize = 17.sp, fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.Center))
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            AnhDaiDien(nd.chuVietTat(), 88.dp)
            Spacer(modifier = Modifier.height(10.dp))
            Text(nd.ten, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            if (nd.email.isNotEmpty()) {
                Text(nd.email, fontSize = 13.sp, color = mauPhu)
            }
            if (nd.lienKetGoogle) {
                Spacer(modifier = Modifier.height(8.dp))
                NhanMau("✓ Đã liên kết tài khoản Google", Color(0xFFDCFCE7), Color(0xFF15803D))
            }

            // 3 ô thống kê
            Spacer(modifier = Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                OThongKe("${nd.soViecDaXong}", "Việc đã xong", Modifier.weight(1f))
                OThongKe("${nd.chuoiNgay} ngày", "Chuỗi liên tiếp", Modifier.weight(1f))
                OThongKe("${nd.tiLeDungHan}%", "Đúng hạn", Modifier.weight(1f))
            }

            // Biểu đồ cột tuần này
            Spacer(modifier = Modifier.height(12.dp))
            KhungThe(modifier = Modifier.fillMaxWidth()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Tuần này", fontWeight = FontWeight.SemiBold, modifier = Modifier.weight(1f))
                    Text("${nd.viecTrongTuan.sum()}/${nd.mucTieuTuan} việc", fontSize = 12.sp, color = mauPhu)
                }
                Spacer(modifier = Modifier.height(12.dp))
                BieuDoTuan(nd.viecTrongTuan)
            }

            Spacer(modifier = Modifier.height(12.dp))
            KhungThe(modifier = Modifier.fillMaxWidth()) {
                DongCaiDat(Icons.Filled.Refresh, "Đồng bộ dữ liệu", "Vừa xong")
                HorizontalDivider(color = MaterialTheme.colorScheme.outline)
                DongCaiDat(Icons.Filled.Lock, "Quyền riêng tư", "›")
            }

            Spacer(modifier = Modifier.height(20.dp))
            OutlinedButton(
                onClick = {
                    KhoDuLieu.nguoiDung = null
                    nav.navigate("login") {
                        popUpTo(nav.graph.id) { inclusive = true }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = null, tint = mauDo)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Đăng xuất", color = mauDo)
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun OThongKe(so: String, nhan: String, modifier: Modifier) {
    KhungThe(modifier = modifier) {
        Text(so, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Text(nhan, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

// Biểu đồ cột: mỗi cột là số việc xong trong một ngày, cột hôm nay tô đậm
@Composable
fun BieuDoTuan(soViec: List<Int>) {
    val cacThu = listOf("T2", "T3", "T4", "T5", "T6", "T7", "CN")
    val lonNhat = (soViec.maxOrNull() ?: 1).coerceAtLeast(1)
    // Calendar: Chủ nhật = 1, Thứ hai = 2 ... -> đổi về vị trí 0..6 (T2..CN)
    val thuHienTai = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
    val viTriHomNay = if (thuHienTai == Calendar.SUNDAY) 6 else thuHienTai - 2
    val mauChinh = MaterialTheme.colorScheme.primary

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        for (i in cacThu.indices) {
            val giaTri = if (i < soViec.size) soViec[i] else 0
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .width(22.dp)
                        .height((giaTri.toFloat() / lonNhat * 80f).dp)
                        .background(
                            if (i == viTriHomNay) mauChinh else mauChinh.copy(alpha = 0.3f),
                            RoundedCornerShape(6.dp)
                        )
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(cacThu[i], fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}
