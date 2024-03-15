package com.example.passwordmanagerapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "websites")
data class WebsiteDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val iconUrl: String,
    val address: String,
    var name: String,
    var cipheredLogin: String,
    var cipheredPassword: String,
    var comment: String
)