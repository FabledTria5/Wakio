package dev.fabled.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "notifications_table")
data class NotificationEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "notification_type_tag")
    val notificationTypeTag: String,
    @ColumnInfo(name = "notification_text")
    val notificationText: String,
    @ColumnInfo(name = "createdAt")
    val createdAt: LocalDateTime
)