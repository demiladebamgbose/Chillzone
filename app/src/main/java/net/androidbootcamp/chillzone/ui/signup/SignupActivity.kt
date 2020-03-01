package net.androidbootcamp.chillzone.ui.signup

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextWatcher
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.adapters.TextViewBindingAdapter
import androidx.lifecycle.Observer
import com.google.android.gms.common.SignInButton
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.activity_signup.displayName
import kotlinx.android.synthetic.main.activity_signup.login
import kotlinx.android.synthetic.main.activity_signup.password
import kotlinx.android.synthetic.main.activity_signup.username
import kotlinx.android.synthetic.main.activity_signup.view.*
import net.androidbootcamp.chillzone.ChillApp
import net.androidbootcamp.chillzone.MainActivity
import net.androidbootcamp.chillzone.R
import net.androidbootcamp.chillzone.databinding.ActivitySignupBinding
import net.androidbootcamp.chillzone.firebase.auth.Result
import net.androidbootcamp.chillzone.helpers.afterTextChanged
import net.androidbootcamp.chillzone.ui.login.LoginViewModel
import net.androidbootcamp.chillzone.viewModels.VMFactory
import javax.inject.Inject

class SignupActivity : AppCompatActivity() {

    lateinit var loginViewModel:LoginViewModel
    @Inject lateinit var vmFactory: VMFactory

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
                username.text.toString(),
                password.text.toString(),
                displayName.text.toString()
            )
            }
        }
        password.apply { afterTextChanged {
            loginViewModel.signUpDataChanged(
                username.text.toString(),
                password.text.toString(),
                displayName.text.toString()
            )
            }
        }
        displayName.apply { afterTextChanged {
            loginViewModel.signUpDataChanged(
                username.text.toString(),
                password.text.toString(),
                displayName.text.toString()
            )
            }
        }

        login.setOnClickListener(){
            loadingS.visibility = View.VISIBLE
            loginViewModel.login(username.text.toString(), password.text.toString()).observe( this@SignupActivity, Observer  {

                loadingS.visibility = View.GONE

                if (it != null && it is Result.Success) {
                    updateUiWithUser(it.data.displayName)
                    setResult(Activity.RESULT_OK)

                    //Complete and destroy login activity once successful
                    intent = Intent(this@SignupActivity, MainActivity::class.java)
                    startActivity(intent)

                } else {
                    showLoginFailed(R.string.fui_error_quota_exceeded)
                }

            })
        }




    }

    fun signUp(email: String, password: String, displayName: String) {

    }


}
