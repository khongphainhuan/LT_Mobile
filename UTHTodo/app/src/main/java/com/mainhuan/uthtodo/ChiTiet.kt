package com.mainhuan.uthtodo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

private val MauDo = Color(0xFFDC2626)

@Composable
fun ManHinhChiTiet(nav: NavController, id: Int) {
    val cv = KhoDuLieu.timTheoId(id)
    val mauChinh = MaterialTheme.colorScheme.primary
    val mauPhu = MaterialTheme.colorScheme.onSurfaceVariant
    var viecNhoMoi by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .safeDrawingPadding()
    ) {
        // Thanh trên: ‹ Quay lại ... Sửa
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.clickable { nav.popBackStack() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = null, tint = mauChinh)
                Text("Quay lại", color = mauChinh)
            }
            Spacer(modifier = Modifier.weight(1f))
            if (cv != null) {
                Text(
                    "Sửa",
                    color = mauChinh,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .clickable { nav.navigate("edit/${cv.id}") }
                        .padding(8.dp)
                )
            }
        }

        if (cv == null) {
            Text("Công việc không còn tồn tại.", color = mauPhu, modifier = Modifier.padding(20.dp))
        } else {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp)
            ) {
                Row {
                    NhanNhom(cv.nhom)
                    if (cv.uuTien == MucUuTien.CAO) {
                        Spacer(modifier = Modifier.width(6.dp))
                        NhanMau("Ưu tiên cao", Color(0xFFFEE2E2), MauDo)
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    cv.ten,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 30.sp,
                    textDecoration = if (cv.daXong) TextDecoration.LineThrough else TextDecoration.None
                )

                Spacer(modifier = Modifier.height(16.dp))
                KhungThe(modifier = Modifier.fillMaxWidth()) {
                    DongCaiDat(Icons.Filled.DateRange, "Hạn chót", cv.thoiGian())
                    HorizontalDivider(color = MaterialTheme.colorScheme.outline)
                    DongCaiDat(Icons.Filled.Notifications, "Nhắc nhở", if (cv.nhacNho) "Trước 15 phút" else "Không")
                    HorizontalDivider(color = MaterialTheme.colorScheme.outline)
                    DongCaiDat(Icons.Filled.Refresh, "Lặp lại", "Không")
                }

                if (cv.ghiChu.isNotBlank()) {
                    TieuDeMuc("GHI CHÚ")
                    KhungThe(modifier = Modifier.fillMaxWidth()) {
                        Text(cv.ghiChu, fontSize = 14.sp)
                    }
                }

                // Việc nhỏ
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TieuDeMuc("VIỆC NHỎ")
                    Spacer(modifier = Modifier.weight(1f))
                    Text("${cv.soViecNhoXong()}/${cv.dsViecNho.size}", fontSize = 12.sp, color = mauPhu, modifier = Modifier.padding(top = 12.dp))
                }
                LinearProgressIndicator(
                    progress = { if (cv.dsViecNho.isEmpty()) 0f else cv.soViecNhoXong().toFloat() / cv.dsViecNho.size },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    color = mauChinh,
                    trackColor = MaterialTheme.colorScheme.outline
                )
                KhungThe(modifier = Modifier.fillMaxWidth()) {
                    for (vn in cv.dsViecNho.toList()) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(checked = vn.daXong, onCheckedChange = { vn.daXong = it })
                            Text(
                                vn.ten,
                                fontSize = 14.sp,
                                color = if (vn.daXong) mauPhu else MaterialTheme.colorScheme.onSurface,
                                textDecoration = if (vn.daXong) TextDecoration.LineThrough else TextDecoration.None,
                                modifier = Modifier.weight(1f)
                            )
                            IconButton(onClick = { cv.dsViecNho.remove(vn) }) {
                                Icon(Icons.Filled.Delete, contentDescription = "Xóa việc nhỏ", tint = MauDo, modifier = Modifier.size(20.dp))
                            }
                        }
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.Add, contentDescription = null, tint = mauChinh, modifier = Modifier.padding(horizontal = 12.dp))
                        OutlinedTextField(
                            value = viecNhoMoi,
                            onValueChange = { viecNhoMoi = it },
                            placeholder = { Text("Thêm việc nhỏ") },
                            singleLine = true,
                            modifier = Modifier.weight(1f)
                        )
                        TextButton(onClick = {
                            cv.themViecNho(viecNhoMoi)
                            viecNhoMoi = ""
                        }) {
                            Text("Thêm")
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)) {
                Button(
                    onClick = { cv.doiTrangThai() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(if (cv.daXong) "Đánh dấu chưa xong" else "Đánh dấu hoàn thành", fontSize = 16.sp)
                }
                TextButton(
                    onClick = {
                        KhoDuLieu.xoa(cv)
                        nav.popBackStack()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Xóa công việc", color = MauDo)
                }
            }
        }
    }
}
