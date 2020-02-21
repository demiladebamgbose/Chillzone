package net.androidbootcamp.chillzone.room.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveUser(userEntity: UserEntity)

    @Query("SELECT * FROM ${UserEntity.TABLE_NAME} WHERE email = :email ")
    fun getUser(email: String): UserEntity
}