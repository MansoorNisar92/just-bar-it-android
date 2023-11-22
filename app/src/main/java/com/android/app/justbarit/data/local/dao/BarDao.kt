package com.android.app.justbarit.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.app.justbarit.data.local.entity.BarEntity

@Dao
interface BarDao {
    @Query("SELECT * FROM bar")
    suspend fun getBars(): List<BarEntity>

    @Query("SELECT EXISTS(SELECT 1 FROM bar limit 1) AS exist_status")
    suspend fun doBarsExists(): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(bars: List<BarEntity>)

    @Query("DELETE FROM bar")
    suspend fun deleteAllBars()
}