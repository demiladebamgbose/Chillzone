package net.androidbootcamp.chillzone.room.user

import android.view.Display
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = UserEntity.TABLE_NAME)
data class UserEntity (@PrimaryKey val email: String,
                       @ColumnInfo(name = "displayName") val displayName: String,
                       @ColumnInfo(name = "password") val password: String
) {
    companion object{
        const val TABLE_NAME = "user"
    }
}