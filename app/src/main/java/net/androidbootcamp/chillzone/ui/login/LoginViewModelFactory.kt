package net.androidbootcamp.chillzone.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.androidbootcamp.chillzone.firebase.auth.LoginDataSource
import net.androidbootcamp.chillzone.repositories.UserRepository

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class LoginViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(
                userRepository = UserRepository(
                    dataSource = LoginDataSource()
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
