package net.androidbootcamp.chillzone.ui.login

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import kotlinx.android.synthetic.main.activity_login.*
import net.androidbootcamp.chillzone.ChillApp
import net.androidbootcamp.chillzone.MainActivity

import net.androidbootcamp.chillzone.R
import net.androidbootcamp.chillzone.databinding.ActivityLoginBinding
import net.androidbootcamp.chillzone.firebase.auth.model.User
import net.androidbootcamp.chillzone.firebase.auth.Result
import net.androidbootcamp.chillzone.viewModels.VMFactory
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    @Inject lateinit var vmFactory: VMFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as ChillApp).appComponent.inject(this)

//        requestWindowFeature(Window.FEATURE_NO_TITLE)
//        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        supportActionBar?.hide()




        val binding: ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.lifecycleOwner = this

        loginViewModel = ViewModelProviders.of(this, vmFactory)
            .get(LoginViewModel::class.java)

        binding.state = loginViewModel


        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })

//        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
//            val loginResult = it ?: return@Observer
//
//            loading.visibility = View.GONE
//            if (loginResult.error != null) {
//                showLoginFailed(loginResult.error)
//            }
//            if (loginResult.success != null) {
//                updateUiWithUser(loginResult.success)
//            }
//            setResult(Activity.RESULT_OK)
//
//            //Complete and destroy login activity once successful
//            intent = Intent(this@LoginActivity, MainActivity::class.java)
//            startActivity(intent)
//        })

        username.afterTextChanged {
            loginViewModel.loginDataChanged(
                username.text.toString(),
                password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                            username.text.toString(),
                            password.text.toString()
                        )
                }
                false
            }

            login.setOnClickListener {
                loading.visibility = View.VISIBLE
                loginViewModel.login(username.text.toString(), password.text.toString()).observe( this@LoginActivity, Observer  {

                    loading.visibility = View.GONE

                    if (it != null && it is Result.Success) {
                        updateUiWithUser(it.data.displayName)
                        setResult(Activity.RESULT_OK)

                        //Complete and destroy login activity once successful
                        intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)

                    } else {
                        showLoginFailed(R.string.fui_error_quota_exceeded)
                    }

                })
            }
        }
    }

    private fun updateUiWithUser(displayName: String?) {
        val welcome = getString(R.string.welcome)
        Toast.makeText(
            applicationContext,
            "$welcome $displayName",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}
