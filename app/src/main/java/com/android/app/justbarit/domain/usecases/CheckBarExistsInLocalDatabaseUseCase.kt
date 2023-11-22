package com.android.app.justbarit.domain.usecases

import com.android.app.justbarit.data.remote.repository.BarRepository
import javax.inject.Inject

class CheckBarExistsInLocalDatabaseUseCase @Inject constructor(private val repository: BarRepository) {
    suspend operator fun invoke(): Boolean {
        return repository.doBarsExistsInLocalDatabase()
    }
}