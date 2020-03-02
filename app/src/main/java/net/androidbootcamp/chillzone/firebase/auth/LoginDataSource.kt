package net.androidbootcamp.chillzone.firebase.auth

import androidx.lifecycle.MutableLiveData
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import net.androidbootcamp.chillzone.firebase.auth.model.User
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource(val auth: FirebaseAuth) {

    fun login(email: String, password: String): MutableLiveData<Result<User>> {
        lateinit var  user: User
        var result : MutableLiveData<Result<User>> = MutableLiveData()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener( object: OnCompleteListener<AuthResult> {
                override fun onComplete(task: Task<AuthResult>) {
                    if (task.isSuccessful) {
                        task.result
                        user = User(email, password,auth.currentUser?.displayName )
                        result.value = Result.Success(
                                user
                            )


                    } else {
                        result.value = Result.Error(
                                IOException("Unable to log in user")
                            )
                    }
                }
            } )
        return result
    }

    fun signupWithGoogle(account: GoogleSignInAccount) : MutableLiveData<Result<User>> {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        var result : MutableLiveData<Result<User>> = MutableLiveData()

        auth.signInWithCredential(credential).addOnCompleteListener() {
            if (it.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                val fireUser = auth.currentUser

                val user = User(fireUser?.email, "",fireUser?.displayName)
                result.value = Result.Success(
                    user
                )
            } else {
                // If sign in fails, display a message to the user.
                result.value = Result.Error(
                    IOException("Unable to log in user")
                )
            }
        }
        return result
    }



    fun signUp( email: String, password: String, displayName:String) : MutableLiveData<Result<User>> {
        lateinit var  user: User
        var result : MutableLiveData<Result<User>> = MutableLiveData()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(object:OnCompleteListener<AuthResult> {
                override fun onComplete(task: Task<AuthResult>) {
                    if (task.isSuccessful) {
                         var firebaseUser = auth.currentUser

                         var profileUpdates: UserProfileChangeRequest =  UserProfileChangeRequest.Builder()
                            .setDisplayName(displayName).build()

                        firebaseUser?.updateProfile(profileUpdates)?.addOnCompleteListener{
                            if (task.isSuccessful) {
                                user = User(email, password, displayName)
                                result.value =
                                    Result.Success(
                                        user
                                    )
                            } else {
                                // TODO: remove created user
                                result.value =
                                    Result.Error(
                                        IOException("Unable to update display name")
                                    )
                            }
                        }
                    } else {
                        result.value =
                            Result.Error(
                                IOException("Unable to sign up user")
                            )
                    }
                }
            })
        return result
    }

    fun logout() {
        auth.signOut()
    }
}

