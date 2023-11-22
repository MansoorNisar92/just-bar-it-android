package com.android.app.justbarit.data.remote.repository

import com.android.app.justbarit.data.local.dao.BarDao
import com.android.app.justbarit.data.local.entity.toBarModelList
import com.android.app.justbarit.data.remote.entity.Bar
import com.android.app.justbarit.data.remote.entity.toBarEntityList
import javax.inject.Inject

class BarRepository @Inject constructor(private val barDao: BarDao
) {

    suspend fun getBarsFromDatabase(): List<Bar> {
        return barDao.getBars().toBarModelList()
    }

    suspend fun saveBarsToDatabase(bars: List<Bar>) {
        barDao.insertAll(bars.toBarEntityList())
    }

    suspend fun doBarsExistsInLocalDatabase(): Boolean {
        return barDao.doBarsExists()
    }

    suspend fun flushBarsFromDatabase() {
        barDao.deleteAllBars()
    }

}