package com.nxt.katalisreading.presentation.screen.auth


import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.nxt.katalisreading.R
import com.nxt.katalisreading.presentation.component.ButtonComponent
import com.nxt.katalisreading.presentation.component.Dialog
import com.nxt.katalisreading.presentation.component.EmailField
import com.nxt.katalisreading.presentation.component.Loading
import com.nxt.katalisreading.presentation.component.Logo
import com.nxt.katalisreading.presentation.component.Or
import com.nxt.katalisreading.presentation.component.PassWordField
import com.nxt.katalisreading.presentation.component.typeDialog
import com.nxt.katalisreading.presentation.navigation.Screen



@Composable
fun SignInScreen(
    navController: NavController,
    vm: AuthViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val state by vm.state.collectAsState()
    val context = LocalContext.current

    //Coroutine launcher for Google Sign-In
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                val idToken = account.idToken
                if (idToken != null) {
                    vm.signInWithGoogle(idToken,navController)
                }
            }catch (e: ApiException) {
                Toast.makeText(context, "Đăng nhập thất bại: ${e.message}", Toast.LENGTH_SHORT).show()
            }



    }
    if(state.showDialog){
        Dialog(
            type = if(state.isSuccess) typeDialog.SUCCESS else typeDialog.ERROR,
            mes = state.dialogMes?:"",
            onDismiss = {  vm.consumeError() }
        )
    }

    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp)
    ) {
        val (logo, title, label1, email, label2, password, forgotBtn, signInButton,or, GoogleButton, signUpRow) = createRefs()
        Logo(
            modifier = Modifier
                .constrainAs(logo) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
        )

        Text(
            text = "Đăng nhập",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.SemiBold,
                fontSize = 28.sp
            ),
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(logo.bottom, margin = 100.dp)
                    start.linkTo(parent.start)
                }
        )

        Text(
            text = "Email",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .constrainAs(label1) {
                    top.linkTo(title.bottom, margin = 28.dp)
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
                .constrainAs(password) {
                    top.linkTo(label2.bottom, margin = 10.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        //Forgot Password Button
        Text(
            text = "Quên mật khẩu?",
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.inter_regular, FontWeight.Normal)),
                textDecoration = TextDecoration.Underline,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal
            ),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .constrainAs(forgotBtn) {
                    top.linkTo(password.bottom, margin = 5.dp)
                    end.linkTo(parent.end)
                }
                .clickable(onClick = { /*TODO: Navigate to Forgot Password Screen*/ })
        )


        ButtonComponent(
            text = "Đăng nhập",
            onClick = { vm.signIn(navController) },
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(signInButton) {
                    top.linkTo(forgotBtn.bottom, margin = 20.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        Or(modifier = Modifier
            .fillMaxWidth()
            .constrainAs(or) {
                top.linkTo(signInButton.bottom, margin = 20.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        //Continue with Google Button
        Button(modifier = Modifier
            .fillMaxWidth()
            .constrainAs(GoogleButton) {
                top.linkTo(or.bottom, margin = 20.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            colors = ButtonColors(
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.primary,
                disabledContainerColor = MaterialTheme.colorScheme.background,
                disabledContentColor = MaterialTheme.colorScheme.primary,
            ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        shape = RoundedCornerShape(8.dp),
            onClick = {
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
                val client = GoogleSignIn.getClient(context, gso)
                launcher.launch(client.signInIntent) },
        ) {
            Image(
                painter = painterResource(id = R.drawable.google),
                contentDescription = null,
                modifier = Modifier.padding(end = 8.dp)
                    .size(24.dp),
                contentScale = ContentScale.Fit

            )
            Text(
                text = "Đăng nhập với Google",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

        }

        //Don't have an account? Sign up
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(signUpRow) {
                    bottom.linkTo(parent.bottom, margin = 20.dp)
                    start.linkTo(parent.start)
                },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Bạn chưa có tài khoản? ",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "Đăng ký",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary,
                    textDecoration = TextDecoration.Underline
                ),
                modifier = Modifier.padding(start = 4.dp)
                    .clickable(onClick = {
                        navController.navigate(Screen.SignUp.route){
                            popUpTo(Screen.Login.route) {
                                inclusive = true
                            }
                        }
                    }),
            )
        }

    }
    Loading(state.isLoading, modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.35f)))
}






