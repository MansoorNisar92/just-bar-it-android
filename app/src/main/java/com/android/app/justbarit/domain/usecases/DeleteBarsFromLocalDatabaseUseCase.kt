package com.android.app.justbarit.domain.usecases

import com.android.app.justbarit.data.remote.repository.BarRepository
import javax.inject.Inject

class DeleteBarsFromLocalDatabaseUseCase @Inject constructor(private val repository: BarRepository) {
    suspend operator fun invoke() {
        repository.flushBarsFromDatabase()
    }
}