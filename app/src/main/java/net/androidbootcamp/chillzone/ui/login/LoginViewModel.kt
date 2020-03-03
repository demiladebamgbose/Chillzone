package net.androidbootcamp.chillzone.ui.login

import android.util.Log
import android.util.Patterns
import androidx.databinding.Observable
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import net.androidbootcamp.chillzone.repositories.UserRepository
import net.androidbootcamp.chillzone.firebase.auth.Result

import net.androidbootcamp.chillzone.R
import net.androidbootcamp.chillzone.firebase.auth.model.User
import java.util.*

class LoginViewModel(val userRepository: UserRepository) : ViewModel(), Observable {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun getLoggedInUser (): User? {
        return userRepository.user
    }

    fun login(email: String, password: String): LiveData<Result<User>> {
        // can be launched in a separate asynchronous job

        var userResult = Transformations.map(userRepository.login(email,password)) {
            if (it != null && it is Result.Success) {
                _loginResult.value =
                    LoginResult(success = LoggedInUserView(displayName = it.data.displayName))

            } else {
                _loginResult.value = LoginResult(error = R.string.login_failed)
            }

        it }
        return userResult

    }

    fun signUp(email: String, password: String, displayName: String): LiveData<Result<User>> {
        // can be launched in a separate asynchronous job
        val userResult = Transformations.map( userRepository.signUp(email, password, displayName)) {

        if (it != null && it is Result.Success) {
            _loginResult.value =
                LoginResult(success = LoggedInUserView(displayName = it.data.displayName))

        } else {
            _loginResult.value = LoginResult(error = R.string.login_failed)
        }

        it }
        return userResult

    }

    fun signupWithGoogle(account: GoogleSignInAccount) : LiveData<Result<User>> {
        val userResult = Transformations.map(userRepository.googleSignup(account)) {
            if (it != null && it is Result.Success) {
                _loginResult.value =
                    LoginResult(success = LoggedInUserView(displayName = it.data.displayName))

            } else {
                _loginResult.value = LoginResult(error = R.string.login_failed)
            }
        it }
        return userResult
    }

    fun loginDataChanged(email: String, password: String) {
        if (!isUserNameValid(email)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }
    fun signUpDataChanged(email: String, password: String, displayName: String) {
        loginDataChanged(email, password)

        if (!isUserNameValid(displayName)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
         return if (username.contains('@')) {
             Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
             username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
//        TODO //To change body of created functions use File | Settings | File Templates.
    }
}
