package com.mainhuan.uthtodo

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

// viecCanSua = null thì là thêm mới, khác null thì là sửa công việc đó

@Composable
fun ManHinhThemViec(nav: NavController, viecCanSua: CongViec?) {
    val dsNgay = remember { dsNgayChon() }
    var ten by remember { mutableStateOf(viecCanSua?.ten ?: "") }
    var ghiChu by remember { mutableStateOf(viecCanSua?.ghiChu ?: "") }
    var nhom by remember { mutableStateOf(viecCanSua?.nhom ?: NhomViec.HOC_TAP) }
    var ngay by remember { mutableStateOf(viecCanSua?.ngay ?: dsNgay[0]) }
    var gio by remember { mutableStateOf(viecCanSua?.gio ?: "19:00") }
    var nhacNho by remember { mutableStateOf(viecCanSua?.nhacNho ?: true) }
    var uuTien by remember { mutableStateOf(viecCanSua?.uuTien ?: MucUuTien.VUA) }

    val hopLe = ten.isNotBlank()
    val mauChinh = MaterialTheme.colorScheme.primary
    val mauPhu = MaterialTheme.colorScheme.onSurfaceVariant

    fun luu() {
        if (!hopLe) return
        if (viecCanSua == null) {
            KhoDuLieu.themCongViec(ten.trim(), ghiChu.trim(), nhom, ngay, gio, nhacNho, uuTien)
        } else {
            viecCanSua.ten = ten.trim()
            viecCanSua.ghiChu = ghiChu.trim()
            viecCanSua.nhom = nhom
            viecCanSua.ngay = ngay
            viecCanSua.gio = gio
            viecCanSua.nhacNho = nhacNho
            viecCanSua.uuTien = uuTien
        }
        nav.popBackStack()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .safeDrawingPadding()
    ) {
        // Thanh trên: Hủy | Việc mới | Lưu
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Hủy", color = mauChinh, modifier = Modifier.clickable { nav.popBackStack() })
            Text(
                if (viecCanSua == null) "Việc mới" else "Sửa việc",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )
            Text(
                "Lưu",
                color = if (hopLe) mauChinh else mauPhu,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.clickable { luu() }
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            KhungThe(modifier = Modifier.fillMaxWidth()) {
                Text("Tên công việc", fontSize = 12.sp, color = mauPhu)
                OutlinedTextField(
                    value = ten,
                    onValueChange = { ten = it },
                    placeholder = { Text("Ví dụ: Ôn chương 4 – Giải thuật") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text("Ghi chú", fontSize = 12.sp, color = mauPhu)
                OutlinedTextField(
                    value = ghiChu,
                    onValueChange = { ghiChu = it },
                    placeholder = { Text("Thêm ghi chú (không bắt buộc)") },
                    minLines = 2,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            TieuDeMuc("NHÓM")
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                for (n in NhomViec.entries) {
                    val dangChon = nhom == n
                    Text(
                        n.ten,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Medium,
                        color = if (dangChon) mauChinh else MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .background(MaterialTheme.colorScheme.surface)
                            .border(
                                if (dangChon) 2.dp else 1.dp,
                                if (dangChon) mauChinh else MaterialTheme.colorScheme.outline,
                                RoundedCornerShape(10.dp)
                            )
                            .clickable { nhom = n }
                            .padding(vertical = 12.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            KhungThe(modifier = Modifier.fillMaxWidth()) {
                DongChonGiaTri(Icons.Filled.DateRange, "Ngày", ngay, dsNgay) { ngay = it }
                HorizontalDivider(color = MaterialTheme.colorScheme.outline)
                DongChonGiaTri(Icons.Filled.Refresh, "Giờ", gio, dsGioChon) { gio = it }
                HorizontalDivider(color = MaterialTheme.colorScheme.outline)
                Row(
                    modifier = Modifier.padding(vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Filled.Notifications, contentDescription = null, tint = mauPhu, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Nhắc tôi", fontSize = 15.sp)
                        Text("Trước 15 phút", fontSize = 12.sp, color = mauPhu)
                    }
                    Switch(checked = nhacNho, onCheckedChange = { nhacNho = it })
                }
            }

            TieuDeMuc("MỨC ƯU TIÊN")
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(10.dp))
                    .padding(4.dp)
            ) {
                for (m in MucUuTien.entries) {
                    val dangChon = uuTien == m
                    Text(
                        m.ten,
                        textAlign = TextAlign.Center,
                        fontWeight = if (dangChon) FontWeight.SemiBold else FontWeight.Normal,
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (dangChon) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.surfaceVariant)
                            .clickable { uuTien = m }
                            .padding(vertical = 8.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }

        Button(
            onClick = { luu() },
            enabled = hopLe,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .height(52.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Lưu công việc", fontSize = 16.sp)
        }
    }
}

// Một dòng bấm vào sẽ xổ ra danh sách để chọn
@Composable
fun DongChonGiaTri(
    icon: ImageVector,
    nhan: String,
    giaTri: String,
    luaChon: List<String>,
    onChon: (String) -> Unit
) {
    var moMenu by remember { mutableStateOf(false) }
    Box {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { moMenu = true }
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(12.dp))
            Text(nhan, fontSize = 15.sp, modifier = Modifier.weight(1f))
            Text(giaTri, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Medium)
        }
        DropdownMenu(expanded = moMenu, onDismissRequest = { moMenu = false }) {
            for (lc in luaChon) {
                DropdownMenuItem(
                    text = { Text(lc) },
                    onClick = {
                        onChon(lc)
                        moMenu = false
                    }
                )
            }
        }
    }
}
