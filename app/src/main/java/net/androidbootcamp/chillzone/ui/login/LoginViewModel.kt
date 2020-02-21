package net.androidbootcamp.chillzone.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import net.androidbootcamp.chillzone.repositories.UserRepository
import net.androidbootcamp.chillzone.firebase.auth.Result

import net.androidbootcamp.chillzone.R
import net.androidbootcamp.chillzone.firebase.auth.model.User

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun getLoggedInUser (): User? {
        return userRepository.user
    }

    fun login(email: String, password: String) {
        // can be launched in a separate asynchronous job
        val user = userRepository.login(email, password)

        if (user.value != null) {
            _loginResult.value =
                LoginResult(success = LoggedInUserView(displayName = user.value?.displayName))
        } else {
            _loginResult.value = LoginResult(error = R.string.login_failed)
        }
    }

    fun signUp(email: String, password: String, displayName: String) {
        // can be launched in a separate asynchronous job
        val user = userRepository.signUp(email, password, displayName)

        if (user.value != null) {
            _loginResult.value =
                LoginResult(success = LoggedInUserView(displayName = user.value?.displayName))
        } else {
            _loginResult.value = LoginResult(error = R.string.login_failed)
        }
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
}
