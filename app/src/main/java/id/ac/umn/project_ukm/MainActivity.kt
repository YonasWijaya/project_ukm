package id.ac.umn.project_ukm

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import id.ac.umn.project_ukm.ui.theme.Project_ukmTheme

class MainActivity : ComponentActivity() {
    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract(),
    ) {res -> this.onSignInResult(res)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Project_ukmTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HalamanLogin()
                }
            }
        }
    }
    fun createSignInIntent(){
        val providers = arrayListOf(
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        val signInIntent = AuthUI.getInstance().createSignInIntentBuilder()
            .setAvailableProviders(providers).build()
        signInLauncher.launch(signInIntent)
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult){
        if (result.resultCode == RESULT_OK){
            goInside()
        }
    }

    private fun goInside(){
        val i = Intent(this, InsideActivity::class.java)
        startActivity(i)
        finish()
    }

    override fun onStart() {
        super.onStart()
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) goInside()
    }
}

@Preview(showBackground = true)
@Composable
fun HalamanLogin(){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            Text("Nama Aplikasi",
                textAlign = TextAlign.Center,
                fontSize = 60.sp)
        }
        Row {
            OutlinedButton(onClick = { MainActivity().createSignInIntent() },
                modifier = Modifier.size(width = 250.dp, height = 100.dp).padding(top = 20.dp)) {
                Image(painterResource(R.drawable.fui_ic_googleg_color_24dp),
                    "logo Google",
                    modifier = Modifier.padding(horizontal = 10.dp).size(40.dp))
                Text("MASUK", fontSize = 30.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}