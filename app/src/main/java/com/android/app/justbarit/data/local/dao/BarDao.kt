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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(countries: List<BarEntity>)
}