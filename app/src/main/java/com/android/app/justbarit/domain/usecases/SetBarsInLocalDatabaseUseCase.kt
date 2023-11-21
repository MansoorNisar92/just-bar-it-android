package com.android.app.justbarit.domain.usecases

import com.android.app.justbarit.data.remote.entity.Bar
import com.android.app.justbarit.data.remote.repository.BarRepository
import javax.inject.Inject

class SetBarsInLocalDatabaseUseCase @Inject constructor(private val repository: BarRepository) {

    suspend operator fun invoke(bars: List<Bar>) {
        return repository.saveBarsToDatabase(bars)
    }
}