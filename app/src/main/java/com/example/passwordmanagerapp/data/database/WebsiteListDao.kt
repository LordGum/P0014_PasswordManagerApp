package com.example.passwordmanagerapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WebsiteListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWebsite(website: WebsiteDbModel)

    @Query("DELETE FROM websites WHERE id = :id")
    suspend fun deleteWebsite(id: Int)

    @Query("SELECT * FROM websites WHERE id = :id")
    suspend fun getWebsiteInfo(id: Int): WebsiteDbModel

    @Query("SELECT * FROM websites ORDER BY id")
    fun getWebsitesList(): Flow<List<WebsiteDbModel>>
}