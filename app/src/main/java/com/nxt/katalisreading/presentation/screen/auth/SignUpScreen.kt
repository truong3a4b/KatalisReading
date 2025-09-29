package com.nxt.katalisreading.presentation.screen.auth

import android.R.attr.checked
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.nxt.katalisreading.presentation.component.ButtonComponent
import com.nxt.katalisreading.presentation.component.EmailField
import com.nxt.katalisreading.presentation.component.Logo
import com.nxt.katalisreading.presentation.component.PassWordField
import com.nxt.katalisreading.presentation.navigation.Screen
import com.nxt.katalisreading.presentation.theme.MyAppTheme
import androidx.activity.compose.BackHandler
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.nxt.katalisreading.data.repository.AuthRepo
import com.nxt.katalisreading.presentation.component.Dialog
import com.nxt.katalisreading.presentation.component.Dialog
import com.nxt.katalisreading.presentation.component.Loading
import com.nxt.katalisreading.presentation.component.typeDialog


@Composable
fun SignUpScreen(
    navController: NavController,
    vm : AuthViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val state by vm.state.collectAsState()

    BackHandler {
        navController.navigate(Screen.Login.route) {
            popUpTo(Screen.SignUp.route) { inclusive = true }
        }
    }



    if(state.showDialog){
        Dialog(
            type = if(state.isSuccess) typeDialog.SUCCESS else typeDialog.ERROR,
            mes = state.dialogMes?:"",
            onDismiss = {
                if(state.isSuccess) navController.navigate(Screen.Login.route)
                vm.consumeError()
            }
        )
    }

    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp)
    ){
        val (logo, title, label1, email, label2, password,label3, comfirmPassword, policy,signUpBtn, signInBack ) = createRefs()
        Logo(
            modifier = Modifier.constrainAs(logo) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            }
        )

        //Title
        Text(
            text = "Tạo tài khoản",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.SemiBold,
                fontSize = 28.sp
            ),
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(logo.bottom, margin = 80.dp)
                    start.linkTo(parent.start)
                }
        )

        //Email
        Text(
            text = "Email",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .constrainAs(label1) {
                    top.linkTo(title.bottom, margin = 24.dp)
                    start.linkTo(parent.start)
                }
        )
        EmailField(email = state.email,
            onEmailChange = vm::onEmailChange,
            isError = state.emailError,
            errorMessage = state.emailMes,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(email) {
                    top.linkTo(label1.bottom, margin = 10.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        //Password
        Text(
            text = "Mật khẩu",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .constrainAs(label2) {
                    top.linkTo(email.bottom, margin = 5.dp)
                    start.linkTo(parent.start)
                }
        )
        PassWordField(
            password = state.password,
            onPasswordChange = vm::onPasswordChange,
            isError = state.passwordError,
            errorMes = state.passwordMes,
            placeholder = "Nhập mật khẩu",
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged(
                    onFocusChanged = {
                        if (!it.isFocused) {
                            vm.onFocusPasswordLost()
                        }
                    }
                )
                .constrainAs(password) {
                    top.linkTo(label2.bottom, margin = 10.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }

        )

        //Comfirm Password
        Text(
            text = "Nhập lại mật khẩu",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .constrainAs(label3) {
                    top.linkTo(password.bottom, margin = 5.dp)
                    start.linkTo(parent.start)
                }
        )
        PassWordField(
            password = state.confirm,
            onPasswordChange = vm::onConfirmPasswordChange,
            isError = state.confirmError,
            errorMes = state.confirmMes,
            placeholder = "Nhập lại mật khẩu",
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged(
                    onFocusChanged = {
                        if (!it.isFocused) {
                            vm.onFocusConfirmLost()
                        }
                    }
                )
                .constrainAs(comfirmPassword) {
                    top.linkTo(label3.bottom, margin = 10.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        //Term Agreement
        TermAgreement(
            checked = state.isCheckTerms,
            onCheckedChange = vm::onCheckTermsChange,
            modifier = Modifier
                .constrainAs(policy) {
                    top.linkTo(comfirmPassword.bottom, margin = 20.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }

        )

        //Sign Up Button
        ButtonComponent(
            text = "Đăng ký",
            onClick = {
                vm.signUp()
            },
            enable = state.isCheckTerms,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(signUpBtn) {
                    top.linkTo(policy.bottom, margin = 20.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
        )

        //Sign In Back Button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(signInBack) {
                    bottom.linkTo(parent.bottom, margin = 20.dp)
                    start.linkTo(parent.start)
                }
                .padding(top = 8.dp, bottom = 8.dp),

            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Bạn đã có tài khoản?",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "Đăng nhập",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary,
                    textDecoration = TextDecoration.Underline
                ),
                modifier = Modifier.padding(start = 4.dp)
                    .clickable(onClick = {
                        navController.navigate(Screen.Login.route){
                            popUpTo(Screen.SignUp.route) { inclusive = true }
                        }
                    }),
            )
        }
    }

    Loading(state.isLoading, modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.35f)))
}

@Composable
fun TermAgreement(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {

    val annotatedText = buildAnnotatedString {
        append("Tôi đồng ý với các ")

        pushStringAnnotation(tag = "TERMS", annotation = "terms")
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.primary,
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.Bold
            )
        ) {
            append("điều khoản dịch vụ")
        }

        append(" và ")

        pushStringAnnotation(tag = "POLICY", annotation = "policy")
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.primary,
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.SemiBold
            )
        ) {
            append("chính sách bảo mật")
        }

        append(" của Katalis.")
        pop()
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ){
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
        ClickableText(
            text = annotatedText,
            onClick = {/*TODO*/},
            style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Normal,
            ),
        )
    }

}