package com.mainhuan.uthtodo

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun ManHinhSplash(onXong: () -> Unit) {
    val mauChinh = MaterialTheme.colorScheme.primary

    // Chờ 2 giây rồi tự chuyển sang màn giới thiệu
    LaunchedEffect(Unit) {
        delay(2000)
        onXong()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(mauChinh)
            .systemBarsPadding()
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BieuTuongApp(kichThuoc = 88.dp, nen = Color.White, mau = mauChinh)
            Spacer(modifier = Modifier.height(16.dp))
            Text("UTH Todo", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Bold)
            Text(
                "Việc học, việc nhà — gọn gàng mỗi ngày",
                color = Color.White.copy(alpha = 0.85f),
                fontSize = 13.sp
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LinearProgressIndicator(
                modifier = Modifier.width(90.dp),
                color = Color.White,
                trackColor = Color.White.copy(alpha = 0.3f)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text("Phiên bản 2.1.0", color = Color.White.copy(alpha = 0.8f), fontSize = 11.sp)
        }
    }
}

// Lớp cha: trang nào cũng có tiêu đề, mô tả, chữ trên nút và một hình minh họa.
// Lớp cha không biết hình vẽ thế nào nên để abstract, lớp con tự viết.
abstract class TrangGioiThieu(
    val tieuDe: String,
    val moTa: String,
    val chuNut: String = "Tiếp tục"
) {
    @Composable
    abstract fun HinhMinhHoa()
}

class TrangCongViec : TrangGioiThieu(
    tieuDe = "Mọi việc, gọn trong một chỗ",
    moTa = "Ghi lại bài tập, việc nhà, lịch hẹn. Không còn quên việc nào."
) {
    @Composable
    override fun HinhMinhHoa() {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            DongViecMinhHoa(daXong = true)
            DongViecMinhHoa(daXong = false)
            DongViecMinhHoa(daXong = false)
        }
    }
}

class TrangPhanLoai : TrangGioiThieu(
    tieuDe = "Chia theo Học tập và Cuộc sống",
    moTa = "Mỗi việc có nhóm riêng, nhìn là biết hôm nay cần lo gì."
) {
    @Composable
    override fun HinhMinhHoa() {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            TheNhomMinhHoa(NhomViec.HOC_TAP)
            TheNhomMinhHoa(NhomViec.CUOC_SONG)
        }
    }
}

class TrangNhacNho : TrangGioiThieu(
    tieuDe = "Nhắc đúng lúc",
    moTa = "Bật thông báo để UTH Todo nhắc bạn trước hạn chót.",
    chuNut = "Bắt đầu ngay"
) {
    @Composable
    override fun HinhMinhHoa() {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(Color.White, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Filled.Notifications,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(28.dp)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                "Ôn bài Toán lúc 19:00",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF1C1C1E),
                modifier = Modifier
                    .background(Color.White, RoundedCornerShape(8.dp))
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            )
        }
    }
}

// Danh sách 3 đối tượng thuộc 3 lớp con khác nhau
val dsTrangGioiThieu: List<TrangGioiThieu> = listOf(TrangCongViec(), TrangPhanLoai(), TrangNhacNho())

private val NenMinhHoa = Color(0xFFEFF2FB)
private val XamThanh = Color(0xFFE2E6EF)
private val XamChu = Color(0xFF6B7280)

@Composable
fun DongViecMinhHoa(daXong: Boolean) {
    Row(
        modifier = Modifier
            .width(160.dp)
            .background(Color.White, RoundedCornerShape(8.dp))
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (daXong) {
            Box(
                modifier = Modifier
                    .size(14.dp)
                    .background(MaterialTheme.colorScheme.primary, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text("✓", color = Color.White, fontSize = 9.sp)
            }
        } else {
            Box(
                modifier = Modifier
                    .size(14.dp)
                    .border(1.5.dp, Color.LightGray, CircleShape)
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Box(
            modifier = Modifier
                .width(90.dp)
                .height(5.dp)
                .background(XamThanh, RoundedCornerShape(50))
        )
    }
}

@Composable
fun TheNhomMinhHoa(nhom: NhomViec) {
    Column(
        modifier = Modifier
            .width(170.dp)
            .background(Color.White, RoundedCornerShape(8.dp))
            .padding(10.dp)
    ) {
        NhanNhom(nhom)
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .width(120.dp)
                .height(5.dp)
                .background(XamThanh, RoundedCornerShape(50))
        )
    }
}

@Composable
fun ManHinhGioiThieu(buoc: Int, onTiepTuc: () -> Unit, onBatDau: () -> Unit) {
    val trang = dsTrangGioiThieu[buoc]
    val laTrangCuoi = buoc == dsTrangGioiThieu.size - 1
    val mauChinh = MaterialTheme.colorScheme.primary

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .safeDrawingPadding()
            .padding(24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BieuTuongApp(kichThuoc = 28.dp, nen = mauChinh, mau = Color.White)
            Spacer(modifier = Modifier.width(8.dp))
            Text("UTH ", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF1C1C1E))
            Text("Todo", color = mauChinh, fontSize = 16.sp)
            Spacer(modifier = Modifier.weight(1f))
            Text(
                "Bỏ qua",
                color = XamChu,
                fontSize = 13.sp,
                modifier = Modifier.clickable { onBatDau() }
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Mỗi trang tự vẽ hình của mình (đa hình)
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(width = 200.dp, height = 230.dp)
                .background(NenMinhHoa, RoundedCornerShape(20.dp)),
            contentAlignment = Alignment.Center
        ) {
            trang.HinhMinhHoa()
        }

        Spacer(modifier = Modifier.weight(1f))

        Text(
            trang.tieuDe,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 30.sp,
            color = Color(0xFF1C1C1E)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(trang.moTa, fontSize = 14.sp, color = XamChu)

        Spacer(modifier = Modifier.height(24.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            for (i in dsTrangGioiThieu.indices) {
                Box(
                    modifier = Modifier
                        .width(if (i == buoc) 20.dp else 6.dp)
                        .height(6.dp)
                        .background(if (i == buoc) mauChinh else XamThanh, RoundedCornerShape(50))
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { if (laTrangCuoi) onBatDau() else onTiepTuc() },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = mauChinh)
        ) {
            Text(trang.chuNut, fontSize = 16.sp)
        }
    }
}
