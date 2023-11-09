package com.android.app.justbarit.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.app.justbarit.data.local.dao.BarDao
import com.android.app.justbarit.data.local.entity.BarEntity

@Database(entities = [BarEntity::class], version = 1)
abstract class JustBarItDatabase : RoomDatabase() {
    abstract fun countryDao(): BarDao
}