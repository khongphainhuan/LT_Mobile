package com.mainhuan.uthtodo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GiaoDienUthTodo {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    UngDung()
                }
            }
        }
    }
}

// Giao diện (theme) đổi theo Cài đặt: sáng/tối, màu chủ đạo, cỡ chữ
@Composable
fun GiaoDienUthTodo(content: @Composable () -> Unit) {
    val caiDat = KhoDuLieu.caiDat
    val giaoDienToi = when (caiDat.giaoDien) {
        GiaoDien.SANG -> false
        GiaoDien.TOI -> true
        GiaoDien.TU_DONG -> isSystemInDarkTheme()
    }

    val bangMau = if (giaoDienToi) {
        darkColorScheme(
            primary = caiDat.mauChuDao,
            onPrimary = Color.White,
            background = Color(0xFF0E0E10),
            onBackground = Color.White,
            surface = Color(0xFF1C1C1E),
            onSurface = Color.White,
            surfaceVariant = Color(0xFF2A2A2E),
            onSurfaceVariant = Color(0xFF9CA3AF),
            outline = Color(0xFF34343A)
        )
    } else {
        lightColorScheme(
            primary = caiDat.mauChuDao,
            onPrimary = Color.White,
            background = Color(0xFFF4F5F9),
            onBackground = Color(0xFF1C1C1E),
            surface = Color.White,
            onSurface = Color(0xFF1C1C1E),
            surfaceVariant = Color(0xFFEEF0F5),
            onSurfaceVariant = Color(0xFF6B7280),
            outline = Color(0xFFE2E6EF)
        )
    }

    // Đổi cỡ chữ toàn app bằng fontScale
    val matDo = LocalDensity.current
    MaterialTheme(colorScheme = bangMau) {
        CompositionLocalProvider(LocalDensity provides Density(matDo.density, caiDat.coChu)) {
            content()
        }
    }
}

@Composable
fun UngDung() {
    val nav = rememberNavController()

    NavHost(navController = nav, startDestination = "splash") {

        composable("splash") {
            ManHinhSplash(
                onXong = {
                    nav.navigate("onboarding/0") {
                        popUpTo("splash") { inclusive = true }
                    }
                }
            )
        }

        composable("onboarding/{buoc}") { backStackEntry ->
            val buoc = backStackEntry.arguments?.getString("buoc")?.toInt() ?: 0
            ManHinhGioiThieu(
                buoc = buoc,
                onTiepTuc = { nav.navigate("onboarding/${buoc + 1}") },
                onBatDau = {
                    nav.navigate("login") {
                        popUpTo(nav.graph.id) { inclusive = true }
                    }
                }
            )
        }

        composable("login") {
            ManHinhDangNhap(
                onDangNhap = { nguoiDung ->
                    KhoDuLieu.nguoiDung = nguoiDung
                    nav.navigate("home") {
                        popUpTo(nav.graph.id) { inclusive = true }
                    }
                }
            )
        }

        composable("home") { ManHinhTrangChu(nav) }

        // Danh sách có thể mở sẵn bộ lọc nhóm, ví dụ "list?nhom=HOC_TAP"
        composable(
            "list?nhom={nhom}",
            arguments = listOf(navArgument("nhom") {
                type = NavType.StringType
                defaultValue = "TAT_CA"
            })
        ) { backStackEntry ->
            ManHinhDanhSach(nav, backStackEntry.arguments?.getString("nhom") ?: "TAT_CA")
        }

        composable("notify") { ManHinhThongBao(nav) }

        composable("settings") { ManHinhCaiDat(nav) }

        composable("add") { ManHinhThemViec(nav, viecCanSua = null) }

        composable("edit/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toInt() ?: -1
            ManHinhThemViec(nav, viecCanSua = KhoDuLieu.timTheoId(id))
        }

        composable("detail/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toInt() ?: -1
            ManHinhChiTiet(nav, id)
        }

        composable("profile") { ManHinhHoSo(nav) }
    }
}
