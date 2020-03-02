package net.androidbootcamp.chillzone.room.user

import android.view.Display
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import net.androidbootcamp.chillzone.firebase.auth.model.User
import org.jetbrains.annotations.Nullable

@Entity(tableName = UserEntity.TABLE_NAME)
data class UserEntity (@PrimaryKey val email: String,
                       @Nullable @ColumnInfo(name = "password") val password: String?,
                       @Nullable @ColumnInfo(name = "displayName") val displayName: String?

                       ) {
    companion object{
        const val TABLE_NAME = "user"
    }

    fun toUser (): User {
        return User(this.email, this.password, this.displayName)
    }
}