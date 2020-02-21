package net.androidbootcamp.chillzone.firebase.auth

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import net.androidbootcamp.chillzone.firebase.auth.model.User
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource(val auth: FirebaseAuth) {

    fun login(email: String, password: String): Result<User> {
        lateinit var  user: User
        lateinit var result : Result<User>

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(object:OnCompleteListener<AuthResult> {
                override fun onComplete(task: Task<AuthResult>) {
                    if (task.isSuccessful) {
                        user = User(email, password,auth.currentUser?.displayName )
                        result =
                            Result.Success(
                                user
                            )


                    } else {
                        result =
                            Result.Error(
                                IOException("Unable to log in user")
                            )
                    }
                }
            })
        return result
    }



    fun signUp( email: String, password: String, displayName:String) : Result<User> {
        lateinit var  user: User
        lateinit var result : Result<User>

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
                                result =
                                    Result.Success(
                                        user
                                    )
                            } else {
                                // TODO: remove created user
                                result =
                                    Result.Error(
                                        IOException("Unable to update display name")
                                    )
                            }
                        }
                    } else {
                        result =
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

