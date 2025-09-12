package com.nxt.katalisreading.presentation.screen.profile

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.nxt.katalisreading.R
import com.nxt.katalisreading.presentation.theme.MyAppTheme

@Composable
fun ProfileScreen(
    navController: NavController,
    modifier: Modifier = Modifier
){
    val settingItems = listOf(Pair(R.drawable.user, "Dữ liệu cá nhân"),
        Pair(R.drawable.security_user, "Bảo mật"),
        Pair(R.drawable.notification, "Thông báo"),
        Pair(R.drawable.crown, "Đăng ký VIP"),
        Pair(R.drawable.global, "Ngôn ngữ"),
        Pair(R.drawable.gallery, "Chất lượng ảnh"),
        Pair(R.drawable.broom, "Xóa cache"),)
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        //Thong tin ca nhan
        item{
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(vertical = 40.dp, horizontal = 16.dp)
            ){
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("https://res.cloudinary.com/dtcdt4dcw/image/upload/v1755699343/avatar_default_hdmpcg.png")
                        .crossfade(true)
                        .placeholder(R.drawable.avatar_default)
                        .error(R.drawable.avatar_default)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp),
                    contentScale = ContentScale.FillBounds
                )
                Spacer(Modifier.width(10.dp))

                Column(
                    Modifier.weight(1f)
                ) {
                    //Ten
                    Text(
                        text = "Nguyễn Xuân Trưởng",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onBackground,
                        maxLines = 2,
                        modifier = Modifier.padding(bottom = 5.dp)
                    )
                    //Email
                    Text(
                        text = "truongnguyen31032004@gmail.com",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                }
                Spacer(Modifier.width(10.dp))
                IconButton(
                    onClick = {
                        /*TODO*/
                    },
                    modifier = Modifier.size(26.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.edit_2),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.fillMaxSize()
                    )
                }

            }
        }

        //Account Setting
        item{
            Surface (
                tonalElevation = 16.dp,
                shadowElevation = 8.dp,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
            ){
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 20.dp),
                ) {
                    Text(
                        text = "Cài đặt chung",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onBackground,
                        maxLines = 2,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                    settingItems.forEach { data ->
                        SettingItem(
                            icon = data.first,
                            label = data.second,
                            onClick = {},
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp)
                        )
                        Spacer(
                            Modifier.height(1.dp)
                                .fillMaxWidth()
                                .background(color = MaterialTheme.colorScheme.secondary)
                        )
                    }


                }
            }

        }

        item {
            Surface(
                tonalElevation = 16.dp,
                shadowElevation = 8.dp,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 20.dp),
                ) {
                    SettingItem(
                        icon = R.drawable.book,
                        label = "Về Katalis",
                        onClick = {},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp)
                    )
                    Spacer(
                        Modifier.height(1.dp)
                            .fillMaxWidth()
                            .background(color = MaterialTheme.colorScheme.secondary)
                    )
                    SettingItem(
                        icon = R.drawable.copyright,
                        label = "Bản quyền",
                        onClick = {},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp)
                    )
                    Spacer(
                        Modifier.height(1.dp)
                            .fillMaxWidth()
                            .background(color = MaterialTheme.colorScheme.secondary)
                    )
                }
            }
        }
    }
}

@Composable
fun SettingItem(
    icon : Int,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ){
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.size(24.dp)
        )
        Spacer(Modifier.width(10.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.weight(1f)
        )
        IconButton(
            onClick = onClick,
            modifier = Modifier.size(24.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.arrow_right),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview(){
    MyAppTheme {
        ProfileScreen(
            navController = NavController(LocalContext.current)
        )
    }
}