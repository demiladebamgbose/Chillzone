package net.androidbootcamp.chillzone.room

import androidx.room.Database
import androidx.room.DatabaseConfiguration
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import net.androidbootcamp.chillzone.room.user.UserDao
import net.androidbootcamp.chillzone.room.user.UserEntity

@Database(entities = arrayOf(UserEntity::class), version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun UserDao() : UserDao
}