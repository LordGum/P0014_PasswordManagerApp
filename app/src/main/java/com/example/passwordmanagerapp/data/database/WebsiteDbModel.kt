package com.example.passwordmanagerapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.passwordmanagerapp.domain.entities.WebsiteAccount

@Entity(tableName = "websites_list")
data class WebsiteDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val iconUrl: String,
    val address: String,
    var name: String,
    val accountList: ArrayList<WebsiteAccount>
)