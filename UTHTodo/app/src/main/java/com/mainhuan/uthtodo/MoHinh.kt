package com.mainhuan.uthtodo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import java.util.Calendar

// Nhóm công việc: mỗi nhóm có tên và màu riêng
enum class NhomViec(val ten: String, val mauNen: Color, val mauChu: Color) {
    HOC_TAP("Học tập", Color(0xFFE0E7FF), Color(0xFF2F5BEA)),
    CUOC_SONG("Cuộc sống", Color(0xFFFFEDD5), Color(0xFFC2410C))
}

// Mức ưu tiên của công việc
enum class MucUuTien(val ten: String) {
    THAP("Thấp"),
    VUA("Vừa"),
    CAO("Cao")
}

// Kiểu giao diện trong phần Cài đặt
enum class GiaoDien(val ten: String) {
    SANG("Sáng"),
    TOI("Tối"),
    TU_DONG("Tự động")
}

// Một việc nhỏ bên trong công việc (checklist)
class ViecNho(val ten: String, daXong: Boolean = false) {
    var daXong by mutableStateOf(daXong)
}

// Một công việc. Các thuộc tính dùng mutableStateOf để giao diện tự cập nhật khi sửa.
class CongViec(
    val id: Int,
    ten: String,
    ghiChu: String,
    nhom: NhomViec,
    ngay: String,
    gio: String,
    nhacNho: Boolean,
    uuTien: MucUuTien,
    daXong: Boolean = false
) {
    var ten by mutableStateOf(ten)
    var ghiChu by mutableStateOf(ghiChu)
    var nhom by mutableStateOf(nhom)
    var ngay by mutableStateOf(ngay)
    var gio by mutableStateOf(gio)
    var nhacNho by mutableStateOf(nhacNho)
    var uuTien by mutableStateOf(uuTien)
    var daXong by mutableStateOf(daXong)
    val dsViecNho = mutableStateListOf<ViecNho>()

    fun thoiGian(): String = "$ngay, $gio"

    fun laGap(): Boolean = uuTien == MucUuTien.CAO && !daXong

    fun soViecNhoXong(): Int = dsViecNho.count { it.daXong }

    fun doiTrangThai() {
        daXong = !daXong
    }

    fun themViecNho(tenViec: String) {
        if (tenViec.isNotBlank()) dsViecNho.add(ViecNho(tenViec.trim()))
    }
}

// Người dùng và số liệu thống kê hiển thị ở màn Hồ sơ
class NguoiDung(
    val ten: String,
    val email: String,
    val lienKetGoogle: Boolean,
    val soViecDaXong: Int = 128,
    val chuoiNgay: Int = 12,
    val tiLeDungHan: Int = 86,
    val viecTrongTuan: List<Int> = listOf(3, 5, 2, 3, 2, 1, 2),
    val mucTieuTuan: Int = 22
) {
    // "Minh Anh" -> "MA"
    fun chuVietTat(): String {
        val cacTu = ten.trim().split(" ").filter { it.isNotEmpty() }
        return if (cacTu.size >= 2) "${cacTu[0][0]}${cacTu[1][0]}".uppercase()
        else ten.take(2).uppercase()
    }
}

// Các lựa chọn trong màn Cài đặt
class CaiDatApp {
    var giaoDien by mutableStateOf(GiaoDien.SANG)
    var mauChuDao by mutableStateOf(Color(0xFF2F5BEA))
    var coChu by mutableStateOf(1f)
    var nhacViec by mutableStateOf(true)
    var amThanh by mutableStateOf(true)
    var tongKetCuoiNgay by mutableStateOf(false)
}

// Kho dữ liệu dùng chung cho cả app (object = chỉ có một bản duy nhất)
object KhoDuLieu {
    var nguoiDung by mutableStateOf<NguoiDung?>(null)
    val caiDat = CaiDatApp()
    val dsCongViec = mutableStateListOf<CongViec>()
    val dsMauChuDao = listOf(
        Color(0xFF2F5BEA),
        Color(0xFF16A34A),
        Color(0xFFC2410C),
        Color(0xFF7C3AED)
    )
    private var idTiepTheo = 1

    init {
        taoDuLieuMau()
    }

    fun themCongViec(
        ten: String, ghiChu: String, nhom: NhomViec, ngay: String,
        gio: String, nhacNho: Boolean, uuTien: MucUuTien, daXong: Boolean = false
    ): CongViec {
        val cv = CongViec(idTiepTheo, ten, ghiChu, nhom, ngay, gio, nhacNho, uuTien, daXong)
        idTiepTheo++
        dsCongViec.add(cv)
        return cv
    }

    fun timTheoId(id: Int): CongViec? = dsCongViec.find { it.id == id }

    fun xoa(cv: CongViec) {
        dsCongViec.remove(cv)
    }

    fun soViecXong(): Int = dsCongViec.count { it.daXong }

    fun soViecConLai(nhom: NhomViec): Int = dsCongViec.count { it.nhom == nhom && !it.daXong }

    private fun taoDuLieuMau() {
        val baiTap = themCongViec(
            "Nộp bài tập Lập trình di động",
            "Nộp file .zip lên hệ thống của trường. Nhớ kèm ảnh chụp màn hình app chạy trên máy ảo.",
            NhomViec.HOC_TAP, "Hôm nay", "09:00", true, MucUuTien.CAO
        )
        baiTap.dsViecNho.add(ViecNho("Hoàn thiện màn hình đăng nhập", true))
        baiTap.dsViecNho.add(ViecNho("Chụp ảnh màn hình app", true))
        baiTap.dsViecNho.add(ViecNho("Nén file và nộp bài"))

        themCongViec("Ôn chương 3 – Cấu trúc dữ liệu", "", NhomViec.HOC_TAP, "Hôm nay", "14:00", true, MucUuTien.VUA)
        themCongViec("Đi chợ mua đồ cho tuần", "", NhomViec.CUOC_SONG, "Hôm nay", "17:30", false, MucUuTien.VUA)
        themCongViec("Họp nhóm đồ án", "", NhomViec.HOC_TAP, "Thứ Năm", "20:00", true, MucUuTien.VUA)
        themCongViec("Tập thể dục 30 phút", "", NhomViec.CUOC_SONG, "Hôm nay", "06:30", false, MucUuTien.THAP, daXong = true)
        themCongViec("Đọc 20 trang sách", "", NhomViec.CUOC_SONG, "Hôm nay", "21:00", false, MucUuTien.THAP, daXong = true)
        themCongViec("Xem lại bài giảng tuần 4", "", NhomViec.HOC_TAP, "Hôm nay", "08:00", false, MucUuTien.VUA, daXong = true)
    }
}

// Dùng Calendar thay vì java.time để chạy được trên Android 7 (minSdk 24)

fun tenThu(lich: Calendar): String {
    return when (lich.get(Calendar.DAY_OF_WEEK)) {
        Calendar.MONDAY -> "Thứ Hai"
        Calendar.TUESDAY -> "Thứ Ba"
        Calendar.WEDNESDAY -> "Thứ Tư"
        Calendar.THURSDAY -> "Thứ Năm"
        Calendar.FRIDAY -> "Thứ Sáu"
        Calendar.SATURDAY -> "Thứ Bảy"
        else -> "Chủ Nhật"
    }
}

// Ví dụ: "Thứ Ba, 20 tháng 10"
fun homNayDayDu(): String {
    val lich = Calendar.getInstance()
    return "${tenThu(lich)}, ${lich.get(Calendar.DAY_OF_MONTH)} tháng ${lich.get(Calendar.MONTH) + 1}"
}

// 7 ngày tới để chọn khi thêm việc: "Hôm nay", "Ngày mai", "Thứ Năm, 22/10"...
fun dsNgayChon(): List<String> {
    val ketQua = mutableListOf<String>()
    for (i in 0..6) {
        val lich = Calendar.getInstance()
        lich.add(Calendar.DAY_OF_MONTH, i)
        val nhan = when (i) {
            0 -> "Hôm nay"
            1 -> "Ngày mai"
            else -> "${tenThu(lich)}, ${lich.get(Calendar.DAY_OF_MONTH)}/${lich.get(Calendar.MONTH) + 1}"
        }
        ketQua.add(nhan)
    }
    return ketQua
}

val dsGioChon = listOf("06:30", "08:00", "09:00", "14:00", "17:30", "19:00", "20:00", "21:00")
