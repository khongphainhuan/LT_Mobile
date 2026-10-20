package com.mainhuan.th_tuan4

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Một thành phần UI: tên và mô tả ngắn
class ThanhPhan(val ten: String, val moTa: String)

// Một nhóm thành phần: tiêu đề nhóm và danh sách thành phần trong nhóm
class NhomThanhPhan(val tieuDe: String, val danhSach: List<ThanhPhan>)

val dsNhom = listOf(
    NhomThanhPhan(
        "Display", listOf(
            ThanhPhan("Text", "Displays text"),
            ThanhPhan("Image", "Displays an image")
        )
    ),
    NhomThanhPhan(
        "Input", listOf(
            ThanhPhan("TextField", "Input field for text"),
            ThanhPhan("PasswordField", "Input field for passwords"),
            ThanhPhan("Toggle", "On/off switch"),
            ThanhPhan("Slider", "Selects a value from a range"),
            ThanhPhan("DatePicker", "Selects a date and time")
        )
    ),
    NhomThanhPhan(
        "Layout", listOf(
            ThanhPhan("Column", "Arranges elements vertically"),
            ThanhPhan("Row", "Arranges elements horizontally")
        )
    ),
    NhomThanhPhan(
        "Selection", listOf(
            ThanhPhan("Button", "Triggers an action when tapped"),
            ThanhPhan("Checkbox", "Selects one or multiple options"),
            ThanhPhan("RadioButton", "Selects a single option"),
            ThanhPhan("Picker", "Selects a value from a list")
        )
    ),
    NhomThanhPhan(
        "Navigation", listOf(
            ThanhPhan("NavigationView", "Navigation container with title and back button"),
            ThanhPhan("TabBar", "Switches between main sections"),
            ThanhPhan("Menu", "Shows a menu with multiple actions")
        )
    ),
    NhomThanhPhan(
        "Content", listOf(
            ThanhPhan("List", "Displays a list of items"),
            ThanhPhan("Card", "Container with content and style"),
            ThanhPhan("Divider", "Separates content into sections")
        )
    )
)

// Màn hình 2: Danh sách các thành phần
@Composable
fun ManHinhDanhSach(onChonThanhPhan: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            "UI Components List",
            color = XanhChu,
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
        )

        // LazyColumn: danh sách cuộn được
        LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
            for (nhom in dsNhom) {
                // Tiêu đề nhóm: Display, Input, Layout...
                item {
                    Text(
                        nhom.tieuDe,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp,
                        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                    )
                }
                // Các thẻ thành phần trong nhóm
                items(nhom.danhSach) { tp ->
                    TheThanhPhan(tp, onClick = { onChonThanhPhan(tp.ten) })
                }
            }
        }
    }
}

// Một thẻ màu xanh nhạt: tên đậm + mô tả
@Composable
fun TheThanhPhan(tp: ThanhPhan, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(NenThe)
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(tp.ten, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
        Text(tp.moTa, fontSize = 13.sp, color = Color.DarkGray)
    }
}