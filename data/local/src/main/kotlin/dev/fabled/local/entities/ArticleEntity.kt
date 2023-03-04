package dev.fabled.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "article_table")
data class ArticleEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "article_name")
    val articleName: String,
    @ColumnInfo(name = "article_image")
    val articleImage: String?,
    @ColumnInfo(name = "article_text")
    val articleText: String,
    @ColumnInfo(name = "article_url")
    val articleUrl: String,
    @ColumnInfo(name = "article_date")
    val articleDate: Long
)
