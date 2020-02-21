package net.androidbootcamp.chillzone.room.user

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveUser(userEntity: UserEntity)

    @Query("SELECT * FROM ${UserEntity.TABLE_NAME} WHERE email = :email ")
    fun getUser(email: String): LiveData<UserEntity>

    @Query("Delete FROM ${UserEntity.TABLE_NAME}")
    fun  deleteAllUsers()
}