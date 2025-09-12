package com.nxt.katalisreading.presentation.component


import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.nxt.katalisreading.R
import com.nxt.katalisreading.presentation.navigation.Screen
import com.nxt.katalisreading.presentation.theme.MyAppTheme


@Preview
@Composable
fun BottomBarPreview(){
    MyAppTheme {
        BottomBar(navController = NavController(LocalContext.current), {})
    }
}

@Composable
fun BottomBar(
    navController: NavController,
    onReselect: (String) -> Unit = {},
) {
    val bottomMenuItemList = prepareBottomMenu()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface ,
        tonalElevation = 10.dp,
        modifier = Modifier.height(56.dp)
    ){
        bottomMenuItemList.forEach {bottomMenuItem ->
            val selected = currentDestination.isInHierarchy(bottomMenuItem.route)
            NavigationBarItem(
                selected = selected,
                onClick = {
                    if(selected){
                        onReselect(bottomMenuItem.route)
                    }else{
                        navController.navigate(bottomMenuItem.route){
                            popUpTo(navController.graph.findStartDestination().id){ //Dùng để pop (xóa) các destination khỏi backstack cho đến route chỉ định.
                                saveState = true //cho phép lưu lại trạng thái của các destination đó
                            }
                            launchSingleTop = true //không push thêm một bản mới, mà chỉ tái sử dụng bản hiện tại trong stack
                            restoreState = true //khôi phục lại trạng thái cũ
                        }
                    }
                },
                icon = {
                    Icon(
                        painter = if(selected) painterResource( bottomMenuItem.iconSelected) else painterResource(bottomMenuItem.icon),
                        contentDescription = null,
                        tint = if(selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.size(20.dp)
                            .offset(y = 5.dp)
                    )
                },
                label = {
                    Text(
                        text = bottomMenuItem.label,
                        style = if(selected) MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold) else MaterialTheme.typography.bodySmall,
                        color = if(selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground

                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent // Bỏ màu nền
                )
            )

        }
    }
}

//kiem tra xem có phải screen đang hiển thị ko
private fun NavDestination?.isInHierarchy(route: String): Boolean{
    return this?.hierarchy?.any { it.route == route} == true
}

data class BottomMenuItem(
    val label: String,
    val icon: Int,
    val iconSelected : Int,
    val route: String,
)

@Composable
fun prepareBottomMenu(): List<BottomMenuItem> {
    val bottomMenuItemList = arrayListOf<BottomMenuItem>()

    bottomMenuItemList.add(
        BottomMenuItem(
            label = "Trang chủ",
            icon = R.drawable.home,
            iconSelected = R.drawable.home_selected,
            route = Screen.Home.route,
        )
    )
    bottomMenuItemList.add(
        BottomMenuItem(
            label = "Xếp hạng",
            icon =  R.drawable.ranking,
            iconSelected = R.drawable.ranking_selected,
            route = Screen.Ranking.route,
        )
    )
    bottomMenuItemList.add(
        BottomMenuItem(
            label = "Của tôi",
            icon = R.drawable.folder,
            iconSelected = R.drawable.folder_selected,
            route = Screen.Folder.route,
        )
    )
    bottomMenuItemList.add(
        BottomMenuItem(
            label = "Tài khoản",
            icon = R.drawable.user,
            iconSelected = R.drawable.user_selected,
            route = Screen.Profile.route,
        )
    )

    return bottomMenuItemList
}