package net.androidbootcamp.chillzone.repositories

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import net.androidbootcamp.chillzone.firebase.auth.LoginDataSource
import net.androidbootcamp.chillzone.firebase.auth.Result
import net.androidbootcamp.chillzone.firebase.auth.model.User
import net.androidbootcamp.chillzone.room.AppDatabase

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class UserRepository(val dataSource: LoginDataSource, val appDatabase: AppDatabase) {

    // in-memory cache of the loggedInUser object
    var user: User? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }

    fun logout() {
        user = null
        dataSource.logout()
    }

    fun login(email: String, password: String): MutableLiveData<Result<User>> {
        // handle login

//        lateinit var result: MutableLiveData<User>

        val loginresult = dataSource.login(email, password)
        // var uEntity =  appDatabase.UserDao().getUser(email)

//        if (uEntity.value != null) {
//             result.value. = uEntity.value?.toUser();
//            return result
//        }

//        Loginresult.observe(LifecycleOwn }, )


        return loginresult
    }

    fun signUp(email: String, password: String, displayName: String ) : MutableLiveData<User> {
        lateinit var result: MutableLiveData<User>
        appDatabase.UserDao().deleteAllUsers()

        val signUpResult = dataSource.signUp(email, password, displayName)

        if (signUpResult is Result.Success) {
            result.value = signUpResult.data
            setLoggedInUser(signUpResult.data)
        }

        return result

    }

    private fun setLoggedInUser(user: User) {
        this.user = user
        appDatabase.UserDao().saveUser(user.toUserEntity())
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }
}
