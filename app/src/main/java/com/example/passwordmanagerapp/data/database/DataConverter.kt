package com.example.passwordmanagerapp.data.database

import androidx.room.TypeConverter
import com.example.passwordmanagerapp.domain.entities.WebsiteAccount
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class DataConverter {
    @TypeConverter
    fun mapListToString(value: List<WebsiteAccount>): String {
        val gson = Gson()
        val type = object : TypeToken<List<WebsiteAccount>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun mapStringToList(value: String): List<WebsiteAccount> {
        val gson = Gson()
        val type = object : TypeToken<List<WebsiteAccount>>() {}.type
        return gson.fromJson(value, type)
    }
}