package net.androidbootcamp.chillzone.firebase.auth.model

import net.androidbootcamp.chillzone.room.user.UserEntity

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class User(
    val email: String?,
    val password : String?,
    val displayName : String?
) {
//    fun toUserEntity (): UserEntity {
//        return UserEntity(this.email, this.password, this.displayName)
//    }

}
