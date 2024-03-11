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

    @Query("DELETE FROM websites_list WHERE id=:websiteId")
    suspend fun deleteWebsite(websiteId: Int)

    @Query("SELECT * FROM websites_list WHERE id=:websiteId")
    suspend fun getWebsiteInfo(websiteId: Int): WebsiteDbModel

    @Query("SELECT * FROM websites_list ORDER BY id")
    fun getWebsitesList(): Flow<List<WebsiteDbModel>>
}