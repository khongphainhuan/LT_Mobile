package com.mainhuan.th_tuan4

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

val XanhNhat = Color(0xFFA8C4F5)
val XanhDam = Color(0xFF4A86E8)
val NenXam = Color(0xFFF2F2F2)

// Màn hình 3: Chi tiết một thành phần
@Composable
fun ManHinhChiTiet(ten: String, onBack: () -> Unit) {
    val tieuDe = when (ten) {
        "Text" -> "Text Detail"
        "Image" -> "Images"
        "Column" -> "Column Layout"
        "Row" -> "Row Layout"
        else -> ten
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Thanh tiêu đề: nút quay lại "<" bên trái, tiêu đề ở giữa
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        ) {
            TextButton(onClick = onBack, modifier = Modifier.align(Alignment.CenterStart)) {
                Text("<", fontSize = 24.sp, color = XanhChu)
            }
            Text(
                tieuDe,
                color = XanhChu,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        // Nội dung: chọn ví dụ tương ứng với thành phần được bấm
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            when (ten) {
                "Text" -> ChiTietText()
                "Image" -> ChiTietImage()
                "TextField" -> ChiTietTextField()
                "PasswordField" -> ChiTietPassword()
                "Toggle" -> ChiTietToggle()
                "Slider" -> ChiTietSlider()
                "DatePicker" -> ChiTietDatePicker()
                "Column" -> ChiTietColumn()
                "Row" -> ChiTietRow()
                "Button" -> ChiTietButton()
                "Checkbox" -> ChiTietCheckbox()
                "RadioButton" -> ChiTietRadio()
                "Picker" -> ChiTietPicker()
                "NavigationView" -> ChiTietNavigationView(onBack)
                "TabBar" -> ChiTietTabBar()
                "Menu" -> ChiTietMenu()
                "List" -> ChiTietList()
                "Card" -> ChiTietCard()
                "Divider" -> ChiTietDivider()
                else -> Text("Chưa có ví dụ cho $ten")
            }
        }
    }
}

// ===================== DISPLAY =====================

@Composable
fun ChiTietText() {
    val cau = buildAnnotatedString {
        append("The ")
        withStyle(SpanStyle(textDecoration = TextDecoration.LineThrough)) { append("quick") }
        withStyle(SpanStyle(color = Color(0xFFC77D1A), fontSize = 36.sp)) { append(" Brown") }
        append("\nfox ")
        withStyle(SpanStyle(letterSpacing = 8.sp)) { append("jumps") }
        withStyle(SpanStyle(fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic)) { append(" over") }
        append("\n")
        withStyle(SpanStyle(textDecoration = TextDecoration.Underline)) { append("the") }
        withStyle(SpanStyle(fontFamily = FontFamily.Cursive, fontStyle = FontStyle.Italic)) { append(" lazy") }
        append(" dog.")
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(cau, fontSize = 28.sp, lineHeight = 46.sp)
        HorizontalDivider(modifier = Modifier.padding(top = 16.dp))
    }
}

@Composable
fun ChiTietImage() {
    val url = "https://giaothongvantaitphcm.edu.vn/wp-content/uploads/2025/01/Logo-GTVT.png"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Ảnh tải từ Internet (cần thư viện Coil và quyền INTERNET)
        AsyncImage(
            model = url,
            contentDescription = "Ảnh từ Internet",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(10.dp))
        )
        Text(
            url,
            fontSize = 11.sp,
            textDecoration = TextDecoration.Underline,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 4.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Ảnh có sẵn trong app (file anh_uth trong res/drawable)
        Image(
            painter = painterResource(R.drawable.uth),
            contentDescription = "Ảnh trong app",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(10.dp))
        )
        Text("In app", fontSize = 12.sp, color = Color.Gray, modifier = Modifier.padding(top = 4.dp))

    }
}

// ===================== INPUT =====================

@Composable
fun ChiTietTextField() {
    var noiDung by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = noiDung,
            onValueChange = { noiDung = it },
            placeholder = { Text("Thông tin nhập") },
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            if (noiDung.isEmpty()) "Tự động cập nhật dữ liệu theo textfield" else noiDung,
            color = Color.Red,
            fontSize = 14.sp,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun ChiTietPassword() {
    var matKhau by remember { mutableStateOf("") }
    var hienMatKhau by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = matKhau,
            onValueChange = { matKhau = it },
            label = { Text("Mật khẩu") },
            singleLine = true,
            visualTransformation = if (hienMatKhau) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                TextButton(onClick = { hienMatKhau = !hienMatKhau }) {
                    Text(if (hienMatKhau) "Ẩn" else "Hiện")
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
        Text("Độ dài mật khẩu: ${matKhau.length} ký tự", modifier = Modifier.padding(top = 8.dp))
    }
}

@Composable
fun ChiTietToggle() {
    var dangBat by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Bật đèn", fontSize = 18.sp, modifier = Modifier.weight(1f))
            Switch(checked = dangBat, onCheckedChange = { dangBat = it })
        }
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(
                    if (dangBat) Color(0xFFFFEB3B) else Color.DarkGray,
                    RoundedCornerShape(10.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                if (dangBat) "Đèn đang BẬT" else "Đèn đang TẮT",
                color = if (dangBat) Color.Black else Color.White,
                fontSize = 18.sp
            )
        }
    }
}

@Composable
fun ChiTietSlider() {
    var giaTri by remember { mutableStateOf(50f) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Giá trị: ${giaTri.toInt()}", fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
        Slider(
            value = giaTri,
            onValueChange = { giaTri = it },
            valueRange = 0f..100f
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Hình vuông to nhỏ theo giá trị của Slider
        Box(
            modifier = Modifier
                .size((giaTri + 20).dp)
                .background(XanhChu, RoundedCornerShape(8.dp))
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChiTietDatePicker() {
    val trangThai = rememberDatePickerState()
    val ngayChon = trangThai.selectedDateMillis

    val chu = if (ngayChon == null) {
        "Chưa chọn ngày"
    } else {
        val dinhDang = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        dinhDang.timeZone = TimeZone.getTimeZone("UTC")
        "Ngày đã chọn: " + dinhDang.format(Date(ngayChon))
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(chu, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = XanhChu)
        DatePicker(state = trangThai)
    }
}

// ===================== LAYOUT =====================

@Composable
fun ChiTietColumn() {
    Column {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(NenXam, RoundedCornerShape(12.dp))
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            for (i in 1..4) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(if (i % 2 == 0) XanhDam else XanhNhat, RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Phần tử $i", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun ChiTietRow() {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        for (i in 1..4) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(NenXam, RoundedCornerShape(12.dp))
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(modifier = Modifier.weight(1f).height(56.dp).background(XanhNhat, RoundedCornerShape(10.dp)))
                Box(modifier = Modifier.weight(1f).height(56.dp).background(XanhDam, RoundedCornerShape(10.dp)))
                Box(modifier = Modifier.weight(1f).height(56.dp).background(XanhNhat, RoundedCornerShape(10.dp)))
            }
        }
    }
}

// ===================== SELECTION =====================

@Composable
fun ChiTietButton() {
    var soLan by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Đã bấm: $soLan lần", fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { soLan++ }) { Text("Button") }
        OutlinedButton(onClick = { soLan++ }) { Text("OutlinedButton") }
        TextButton(onClick = { soLan++ }) { Text("TextButton") }
        ElevatedButton(onClick = { soLan = 0 }) { Text("Đặt lại về 0") }
    }
}

@Composable
fun ChiTietCheckbox() {
    val cacNgonNgu = listOf("Kotlin", "Java", "Dart", "Swift")
    val daChon = remember { mutableStateListOf<String>() }

    Column {
        Text("Bạn biết ngôn ngữ nào?", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        for (nn in cacNgonNgu) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = daChon.contains(nn),
                    onCheckedChange = { chon ->
                        if (chon) daChon.add(nn) else daChon.remove(nn)
                    }
                )
                Text(nn)
            }
        }
        Text(
            "Đã chọn: " + (if (daChon.isEmpty()) "chưa có" else daChon.joinToString(", ")),
            color = XanhChu
        )
    }
}

@Composable
fun ChiTietRadio() {
    var gioiTinh by remember { mutableStateOf("Nam") }

    Column {
        Text("Giới tính của bạn?", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        for (gt in listOf("Nam", "Nữ", "Khác")) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(selected = gioiTinh == gt, onClick = { gioiTinh = gt })
                Text(gt)
            }
        }
        Text("Bạn đã chọn: $gioiTinh", color = XanhChu)
    }
}

@Composable
fun ChiTietPicker() {
    val cacThanhPho = listOf("TP. Hồ Chí Minh", "Hà Nội", "Đà Nẵng", "Cần Thơ", "Huế")
    var moMenu by remember { mutableStateOf(false) }
    var daChon by remember { mutableStateOf("Chọn thành phố") }

    Column {
        Text("Bạn sống ở đâu?", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(8.dp))
        Box {
            OutlinedButton(onClick = { moMenu = true }, modifier = Modifier.fillMaxWidth()) {
                Text("$daChon  ▼")
            }
            DropdownMenu(expanded = moMenu, onDismissRequest = { moMenu = false }) {
                for (tp in cacThanhPho) {
                    DropdownMenuItem(
                        text = { Text(tp) },
                        onClick = {
                            daChon = tp
                            moMenu = false
                        }
                    )
                }
            }
        }
    }
}

// ===================== NAVIGATION =====================

@Composable
fun ChiTietNavigationView(onBack: () -> Unit) {
    Column {
        Text("Màn hình bạn đang xem chính là một NavigationView:", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(8.dp))
        Text("• Thanh trên cùng có tiêu đề \"NavigationView\".")
        Text("• Nút < ở góc trái để quay lại màn hình trước.")
        Text("• App dùng NavHost và NavController để chuyển giữa 3 màn hình: Chào mừng → Danh sách → Chi tiết.")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onBack) { Text("Quay lại danh sách") }
    }
}

@Composable
fun ChiTietTabBar() {
    val cacTab = listOf("Trang chủ", "Tìm kiếm", "Cá nhân")
    var tabDangChon by remember { mutableStateOf(0) }

    Column {
        TabRow(selectedTabIndex = tabDangChon) {
            for (i in cacTab.indices) {
                Tab(
                    selected = tabDangChon == i,
                    onClick = { tabDangChon = i },
                    text = { Text(cacTab[i]) }
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(NenXam),
            contentAlignment = Alignment.Center
        ) {
            Text("Nội dung của tab: ${cacTab[tabDangChon]}", fontSize = 18.sp)
        }
    }
}

@Composable
fun ChiTietMenu() {
    var moMenu by remember { mutableStateOf(false) }
    var hanhDong by remember { mutableStateOf("chưa chọn") }

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Bài viết của tôi", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
            Box {
                TextButton(onClick = { moMenu = true }) { Text("⋮", fontSize = 22.sp) }
                DropdownMenu(expanded = moMenu, onDismissRequest = { moMenu = false }) {
                    for (hd in listOf("Sửa", "Chia sẻ", "Xóa")) {
                        DropdownMenuItem(
                            text = { Text(hd) },
                            onClick = {
                                hanhDong = hd
                                moMenu = false
                            }
                        )
                    }
                }
            }
        }
        Text("Bạn đã chọn: $hanhDong", color = XanhChu)
    }
}

// ===================== CONTENT =====================

@Composable
fun ChiTietList() {
    Column {
        LazyColumn {
            items(30) { i ->
                Text(
                    "Mục số ${i + 1}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                )
                HorizontalDivider()
            }
        }
    }
}

@Composable
fun ChiTietCard() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Card", fontWeight = FontWeight.Bold)
                Text("Khung có nền màu và bo góc")
            }
        }
        ElevatedCard(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("ElevatedCard", fontWeight = FontWeight.Bold)
                Text("Có bóng đổ, trông như nổi lên")
            }
        }
        OutlinedCard(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("OutlinedCard", fontWeight = FontWeight.Bold)
                Text("Có đường viền, nền trắng")
            }
        }
    }
}

@Composable
fun ChiTietDivider() {
    Column {
        Text("Phần 1: Thông tin cá nhân", modifier = Modifier.padding(vertical = 12.dp))
        HorizontalDivider()
        Text("Phần 2: Học tập", modifier = Modifier.padding(vertical = 12.dp))
        HorizontalDivider(thickness = 3.dp, color = XanhChu)
        Text("Phần 3: Liên hệ", modifier = Modifier.padding(vertical = 12.dp))
        HorizontalDivider()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Bên trái", modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
            VerticalDivider()
            Text("Bên phải", modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
        }
    }
}