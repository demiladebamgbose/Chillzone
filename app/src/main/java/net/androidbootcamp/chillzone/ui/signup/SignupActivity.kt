package net.androidbootcamp.chillzone.ui.signup

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.activity_signup.view.*
import net.androidbootcamp.chillzone.ChillApp
import net.androidbootcamp.chillzone.MainActivity
import net.androidbootcamp.chillzone.R
import net.androidbootcamp.chillzone.databinding.ActivitySignupBinding
import net.androidbootcamp.chillzone.firebase.auth.Result
import net.androidbootcamp.chillzone.firebase.auth.model.User
import net.androidbootcamp.chillzone.helpers.afterTextChanged
import net.androidbootcamp.chillzone.ui.login.LoginViewModel
import net.androidbootcamp.chillzone.viewModels.VMFactory
import javax.inject.Inject


class SignupActivity : AppCompatActivity() {

    val RC_SIGN_IN = 123
    lateinit var loginViewModel:LoginViewModel
    @Inject lateinit var vmFactory: VMFactory
    @Inject lateinit var mGso: GoogleSignInOptions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as ChillApp).appComponent.inject(this)
        val binding: ActivitySignupBinding  =  DataBindingUtil.setContentView(this,
            R.layout.activity_signup)

        loginViewModel = vmFactory.create(LoginViewModel::class.java)
        binding.state = loginViewModel

        supportActionBar?.hide()
        sign_in_button.setColorScheme(SignInButton.COLOR_DARK)
        sign_in_button.setSize(SignInButton.SIZE_WIDE)

        loginViewModel.loginFormState.observe(this@SignupActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
            if (loginState.displayNameError !=null) {
                displayName.error = getString(loginState.displayNameError)
            }
        })

        username.apply { afterTextChanged {
            loginViewModel.signUpDataChanged(
                username?.text.toString(),
                password?.text.toString(),
                displayName?.text.toString()
            )
            }
        }
        password.apply { afterTextChanged {
            loginViewModel.signUpDataChanged(
                username?.text.toString(),
                password?.text.toString(),
                displayName?.text.toString()
            )
            }
        }
        displayName.apply { afterTextChanged {
            loginViewModel.signUpDataChanged(
                username?.text.toString(),
                password?.text.toString(),
                displayName?.text.toString()
            )
            }
        }

        login.setOnClickListener(){
            loadingS.visibility = View.VISIBLE
            loginViewModel.signUp(username.text.toString(), password.text.toString(),
                displayName.text.toString()).observe( this@SignupActivity, Observer  {

                handleAuthResult(it)

            })
        }
        
        sign_in_button.setOnClickListener() {
            loadingS.visibility = View.VISIBLE
            val mGoogleSignInClient = GoogleSignIn.getClient(this, mGso);
            val signInIntent: Intent = mGoogleSignInClient.getSignInIntent()
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }

    fun handleAuthResult (result: Result<User>) {
        loadingS.visibility = View.GONE

        if (result != null && result is Result.Success) {
            updateUiWithUser(result.data.displayName)
            setResult(Activity.RESULT_OK)

            //Complete and destroy login activity once successful
            intent = Intent(this@SignupActivity, MainActivity::class.java)
            startActivity(intent)

        } else {
            showLoginFailed(R.string.fui_error_quota_exceeded)
        }
    }

    fun updateUiWithUser(displayName: String?) {
        val welcome = getString(R.string.welcome)
        Toast.makeText(
            applicationContext,
            "$welcome $displayName",
            Toast.LENGTH_LONG
        ).show()
    }

    fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                if (account != null)
                    loginViewModel.signupWithGoogle(account).observe(this@SignupActivity, Observer {
                        handleAuthResult(it)
                    })
            } catch (e: ApiException) {
                loadingS.visibility = View.GONE
                // Google Sign In failed, update UI appropriately
                Log.w("SigninActivity", "Google sign in failed", e)
                // ...
            }
        }
    }


}
