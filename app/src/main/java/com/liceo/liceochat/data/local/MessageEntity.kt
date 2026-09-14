package com.liceo.liceochat.data.local

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey val id: String,
    val sender: String,
    val text: String,
    val createdAt: Long
)

@Dao
interface MessageDao {
    @Query("SELECT * FROM messages ORDER BY createdAt DESC")
    suspend fun getAll(): List<MessageEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<MessageEntity>)
}
